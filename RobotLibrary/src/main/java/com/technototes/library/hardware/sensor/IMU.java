package com.technototes.library.hardware.sensor;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/** Class for imus
 *
 */
@SuppressWarnings("unused")
public class IMU extends Sensor<BNO055IMUImpl> implements IGyro {


    private double angleOffset = 0;

    public enum AxesSigns {
        PPP(0b000),
        PPN(0b001),
        PNP(0b010),
        PNN(0b011),
        NPP(0b100),
        NPN(0b101),
        NNP(0b110),
        NNN(0b111);

        public final int bVal;

        AxesSigns(int bVal) {
            this.bVal = bVal;
        }
    }


    private BNO055IMU.Parameters parameters;

    /** Make an imu
     *
     * @param device The imu device
     */
    public IMU(BNO055IMUImpl device) {
        super(device);
        parameters = new BNO055IMU.Parameters();
        degrees();
        initialize();
    }

    /** Make an imu
     *
     * @param deviceName The device name in hardware map
     */
    public IMU(String deviceName) {
        super(deviceName);
        parameters = new BNO055IMU.Parameters();
        degrees();
        initialize();
    }

    /** Set angle format to degrees
     *
     * @return this
     */
    public IMU degrees(){
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        return this;
    }
    /** Set angle format to radians
     *
     * @return this
     */
    public IMU radians(){
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        return this;
    }

    public IMU initialize(){
        getDevice().initialize(parameters);
        return this;
    }

    /** Get gyro heading
     *
     * @return The gyro heading
     */
    @Override
    public double gyroHeading() {
        return getAngularOrientation().firstAngle;
    }
    public double gyroHeading(AngleUnit unit) {
        return unit.fromUnit(device.getAngularOrientation().angleUnit, gyroHeading()-angleOffset);
    }

    @Override
    public double gyroHeadingInDegrees() {
        return gyroHeading(AngleUnit.DEGREES);
    }

    @Override
    public double gyroHeadingInRadians() {
        return gyroHeading(AngleUnit.RADIANS);
    }

    @Override
    public void setHeading(double newHeading) {
        angleOffset = gyroHeading()-newHeading;
    }

    public IMU remapAxes(AxesOrder order, AxesSigns signs) {
        try {
            // the indices correspond with the 2-bit encodings specified in the datasheet
            int[] indices = order.indices();
            int axisMapConfig = 0;
            axisMapConfig |= (indices[0] << 4);
            axisMapConfig |= (indices[1] << 2);
            axisMapConfig |= (indices[2]);

            // the BNO055 driver flips the first orientation vector so we also flip here
            int axisMapSign = signs.bVal ^ (0b100 >> indices[0]);

            // Enter CONFIG mode
            getDevice().write8(BNO055IMU.Register.OPR_MODE, BNO055IMU.SensorMode.CONFIG.bVal & 0x0F);

            Thread.sleep(100);

            // Write the AXIS_MAP_CONFIG register
            getDevice().write8(BNO055IMU.Register.AXIS_MAP_CONFIG, axisMapConfig & 0x3F);

            // Write the AXIS_MAP_SIGN register
            getDevice().write8(BNO055IMU.Register.AXIS_MAP_SIGN, axisMapSign & 0x07);

            // Switch back to the previous mode
            getDevice().write8(BNO055IMU.Register.OPR_MODE, getDevice().getParameters().mode.bVal & 0x0F);

            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        parameters = getDevice().getParameters();
        return this;
    }

    public AngularVelocity getAngularVelocity(){
        return device.getAngularVelocity();
    }

    public Orientation getAngularOrientation(){
        return device.getAngularOrientation();
    }

}
