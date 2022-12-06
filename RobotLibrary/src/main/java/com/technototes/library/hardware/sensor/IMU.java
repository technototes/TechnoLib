package com.technototes.library.hardware.sensor;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Class for the BNO055 (Inertial Movement Units) that implements the IGyro interface
 */
@SuppressWarnings("unused")
public class IMU extends Sensor<BNO055IMUImpl> implements IGyro {

    private double angleOffset = 0;

    /**
     * The direction of the axes signs when remapping the axes
     */
    public enum AxesSigns {
        /**
         * Positive, Positive, Positive
         */
        PPP(0b000),
        /**
         * Positive, Positive, Negative
         */
        PPN(0b001),
        /**
         * Positive, Negative, Positive
         */
        PNP(0b010),
        /**
         * Positive, Negative, Negative
         */
        PNN(0b011),
        /**
         * Negative, Positive, Positive
         */
        NPP(0b100),
        /**
         * Negative, Positive, Negative
         */
        NPN(0b101),
        /**
         * Negative, Negative, Positive
         */
        NNP(0b110),
        /**
         * Negative, Negative, Negative
         */
        NNN(0b111);

        /**
         * The value of the enumeration
         */
        public final int bVal;

        /**
         * Sets the enum to the integer
         *
         * @param bVal The value (should be 0-7)
         */
        AxesSigns(int bVal) {
            this.bVal = bVal;
        }
    }

    private BNO055IMU.Parameters parameters;

    /**
     * Make an imu
     *
     * @param device The imu device
     */
    public IMU(BNO055IMUImpl device) {
        super(device);
        parameters = new BNO055IMU.Parameters();
        degrees();
        initialize();
    }

    /**
     * Make an imu
     *
     * @param deviceName The device name in hardware map
     */
    public IMU(String deviceName) {
        super(deviceName);
        parameters = new BNO055IMU.Parameters();
        degrees();
        initialize();
    }

    /**
     * Set angle format to degrees
     *
     * @return this
     */
    public IMU degrees() {
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        return this;
    }

    /**
     * Set angle format to radians
     *
     * @return this (for chaining)
     */
    public IMU radians() {
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        return this;
    }

    /**
     * Initialize the IMU
     *
     * @return the IMU (for chaining)
     */
    public IMU initialize() {
        getDevice().initialize(parameters);
        return this;
    }

    /**
     * Get gyro heading
     *
     * @return The gyro heading
     */
    @Override
    public double gyroHeading() {
        return getAngularOrientation().firstAngle;
    }

    /**
     * Get the gyro heading in the provided units
     *
     * @param unit the unit desired
     * @return The heading in 'unit' units
     */
    public double gyroHeading(AngleUnit unit) {
        return unit.fromUnit(device.getAngularOrientation().angleUnit, gyroHeading() - angleOffset);
    }

    /**
     * Gets the gyro heading in degrees
     *
     * @return the heading
     */
    @Override
    public double gyroHeadingInDegrees() {
        return gyroHeading(AngleUnit.DEGREES);
    }

    /**
     * Gets the gyro heading in radians
     *
     * @return the heading
     */
    @Override
    public double gyroHeadingInRadians() {
        return gyroHeading(AngleUnit.RADIANS);
    }

    /**
     * Sets the current heading to be 'new heading'
     *
     * @param newHeading The new heading (in default units)
     */
    @Override
    public void setHeading(double newHeading) {
        angleOffset = gyroHeading() - newHeading;
    }

    /**
     * Remaps the axes for the IMU in the order and sign provided
     *
     * @param order The axis order desired
     * @param signs The signs desired
     * @return this (for chaining)
     */
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
            getDevice()
                .write8(BNO055IMU.Register.OPR_MODE, BNO055IMU.SensorMode.CONFIG.bVal & 0x0F);

            Thread.sleep(100);

            // Write the AXIS_MAP_CONFIG register
            getDevice().write8(BNO055IMU.Register.AXIS_MAP_CONFIG, axisMapConfig & 0x3F);

            // Write the AXIS_MAP_SIGN register
            getDevice().write8(BNO055IMU.Register.AXIS_MAP_SIGN, axisMapSign & 0x07);

            // Switch back to the previous mode
            getDevice()
                .write8(BNO055IMU.Register.OPR_MODE, getDevice().getParameters().mode.bVal & 0x0F);

            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        parameters = getDevice().getParameters();
        return this;
    }

    /**
     * Gets the angular velocity (in default units)
     *
     * @return The Angular Velocity
     */
    public AngularVelocity getAngularVelocity() {
        return device.getAngularVelocity();
    }

    /**
     * Gets the Angular orientation of the IMU
     *
     * @return the Orientation of the IMU
     */
    public Orientation getAngularOrientation() {
        return device.getAngularOrientation();
    }
}
