package com.technototes.library.hardware.sensor;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Class for the IMU (Inertial Movement Units) that implements the IGyro interface
 */
@SuppressWarnings("unused")
public class IMU extends Sensor<com.qualcomm.robotcore.hardware.IMU> implements IGyro {

    private Orientation orientation;
    public AngularVelocity angularVelocity;

    // TODO: Make this report zero-ing info properly
    @Override
    public String LogLine() {
        return logData(
            String.format("%f1.3,%f1.3,%f1.3 ", orientation.firstAngle, orientation.secondAngle, orientation.thirdAngle)
        );
    }

    /**
     * The direction of the axes signs when remapping the axes
     *
     * Probably don't use this stuff. Just use the IMU direction from the 8.1 and later SDK
     */
    @Deprecated
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
        public int bVal;

        /**
         * Sets the enum to the integer
         *
         * @param bVal The value (should be 0-7)
         */
        AxesSigns(int bVal) {
            this.bVal = bVal;
        }
    }

    // This is the preferred units (Degrees/radians)
    private AngleUnit preferred = AngleUnit.DEGREES;
    // This is an offset from 0 so we can zero the IMU without needing to be facing forward
    private double angleOffset;
    private AxesOrder axesOrder;
    private AxesSigns axesSigns;

    /**
     * Make an imu
     *
     * Use the Logo/Usb Facing direction API's as part of the FTC 8.1+ SDK
     */
    protected IMU(com.qualcomm.robotcore.hardware.IMU device, com.qualcomm.robotcore.hardware.IMU.Parameters params) {
        super(device, "imu");
        angleOffset = 0.0;

        axesSigns = AxesSigns.PPP;
        axesOrder = AxesOrder.ZXY;

        degrees();
        initialize(params);
    }

    /**
     * Make an imu
     *
     * Use the Logo/Usb Facing direction API's as part of the FTC 8.1+ SDK
     */
    protected IMU(String deviceName, com.qualcomm.robotcore.hardware.IMU.Parameters params) {
        super(deviceName);
        angleOffset = 0.0;

        axesSigns = AxesSigns.PPP;
        axesOrder = AxesOrder.ZXY;

        degrees();
        initialize(params);
    }

    /**
     * Make an imu
     *
     * @param device The imu device
     */
    public IMU(
        com.qualcomm.robotcore.hardware.IMU device,
        RevHubOrientationOnRobot.LogoFacingDirection logo,
        RevHubOrientationOnRobot.UsbFacingDirection usb
    ) {
        this(device, new com.qualcomm.robotcore.hardware.IMU.Parameters(new RevHubOrientationOnRobot(logo, usb)));
    }

    /**
     * Make an imu
     *
     * @param deviceName The device name in hardware map
     */
    public IMU(
        String deviceName,
        RevHubOrientationOnRobot.LogoFacingDirection logo,
        RevHubOrientationOnRobot.UsbFacingDirection usb
    ) {
        this(deviceName, new com.qualcomm.robotcore.hardware.IMU.Parameters(new RevHubOrientationOnRobot(logo, usb)));
    }

    /**
     * Set angle format to degrees
     *
     * @return this
     */
    public IMU degrees() {
        // Convert the angle offset!
        angleOffset = AngleUnit.DEGREES.fromUnit(preferred, angleOffset);
        preferred = AngleUnit.DEGREES;
        return this;
    }

    /**
     * Set angle format to radians
     *
     * @return this (for chaining)
     */
    public IMU radians() {
        // Convert the angle offset!
        angleOffset = AngleUnit.RADIANS.fromUnit(preferred, angleOffset);
        preferred = AngleUnit.RADIANS;
        return this;
    }

    /**
     * Initialize the IMU
     *
     * @return the IMU (for chaining)
     */
    public IMU initialize(com.qualcomm.robotcore.hardware.IMU.Parameters imuParams) {
        getRawDevice().initialize(imuParams);
        return this;
    }

    /**
     * Get gyro heading
     *
     * @return The gyro heading (in preferred units, from -180/pi to +180/pi
     */
    @Override
    public double gyroHeading() {
        return getAngularOrientation().firstAngle;
    }

    /**
     * Get the gyro heading in the provided units
     *
     * @param unit the unit desired
     * @return The heading in 'unit' units from -180/pi to +180/pi
     */
    public double gyroHeading(AngleUnit unit) {
        return unit.fromUnit(preferred, gyroHeading() - angleOffset);
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
     * Remaps the axes for the IMU in the order and sign provided.
     * This is using the 8.1.1+ IMU axis order:
     * - Z up from the Control Hub
     * - X positive toward the sensor connector side (I2C, etc...)
     * - Y positive toward the USB connector end
     *
     * @param order The axis order desired
     * @param signs The signs desired
     * @return this (for chaining)
     */
    public IMU remapAxesAndSigns(AxesOrder order, AxesSigns signs) {
        axesSigns = signs;
        axesOrder = order;
        return this;
    }

    /**
     * Remaps the axes for the BNO055 IMU in the order and sign provided
     * The SDK 8.1.1 added a new IMU class, which (delightfully) rotated
     * the X and Y axes around the Z axis by 90 degrees clock-wise (viewed from above)
     * If you have code that was using that layout, this is what you probably need to call.
     * I expect to delete this code eventually. Also: it's really not tested at all...
     *
     * @param legacyOrder The *legacy* axis order desired
     * @param legacySigns The *legacy* signs desired
     * @return this (for chaining)
     */
    @Deprecated
    public IMU remapLegacyAxes(AxesOrder legacyOrder, AxesSigns legacySigns) {
        // The BNO055 has the X and Y axes rotated 90 degrees :/
        // These are *very* untested
        switch (legacyOrder) {
            case XZX:
                axesOrder = AxesOrder.YZY;
                break;
            case XYX:
                axesOrder = AxesOrder.YXY;
                axesSigns.bVal = legacySigns.bVal ^ AxesSigns.PNP.bVal;
                break;
            case YXY:
                axesOrder = AxesOrder.XYX;
                axesSigns.bVal = legacySigns.bVal ^ AxesSigns.NPN.bVal;
                break;
            case YZY:
                axesOrder = AxesOrder.XZX;
                axesSigns.bVal = legacySigns.bVal ^ AxesSigns.NPN.bVal;
                break;
            case ZYZ:
                axesOrder = AxesOrder.ZXZ;
                axesSigns.bVal = legacySigns.bVal ^ AxesSigns.PNP.bVal;
                break;
            case ZXZ:
                axesOrder = AxesOrder.ZYZ;
                break;
            case XZY:
                axesOrder = AxesOrder.YZX;
                axesSigns.bVal = legacySigns.bVal ^ AxesSigns.PPN.bVal;
                break;
            case XYZ:
                axesOrder = AxesOrder.YXZ;
                axesSigns.bVal = legacySigns.bVal ^ AxesSigns.PNP.bVal;
                break;
            case YXZ:
                axesOrder = AxesOrder.XYZ;
                axesSigns.bVal = legacySigns.bVal ^ AxesSigns.NPP.bVal;
                break;
            case YZX:
                axesOrder = AxesOrder.XZY;
                axesSigns.bVal = legacySigns.bVal ^ AxesSigns.NPP.bVal;
                break;
            case ZYX:
                axesOrder = AxesOrder.ZXY;
                axesSigns.bVal = legacySigns.bVal ^ AxesSigns.PNP.bVal;
                break;
            case ZXY:
                axesOrder = AxesOrder.ZYX;
                axesSigns.bVal = legacySigns.bVal ^ AxesSigns.PPN.bVal;
                break;
        }
        return this;
    }

    /**
     * Gets the angular velocity (in default units)
     *
     * @return The Angular Velocity
     */
    public AngularVelocity getAngularVelocity(AngleUnit units) {
        angularVelocity = getRawDevice().getRobotAngularVelocity(units);
        return angularVelocity;
    }

    public AngularVelocity getAngularVelocity() {
        return getAngularVelocity(preferred);
    }

    /**
     * Gets the Angular orientation of the IMU
     *
     * @return the Orientation of the IMU
     */
    public Orientation getAngularOrientation(AngleUnit units) {
        orientation = getRawDevice().getRobotOrientation(AxesReference.INTRINSIC, axesOrder, units);
        if ((axesSigns.bVal & AxesSigns.NPP.bVal) == AxesSigns.NPP.bVal) {
            orientation.firstAngle = -orientation.firstAngle;
        }
        if ((axesSigns.bVal & AxesSigns.PNP.bVal) == AxesSigns.PNP.bVal) {
            orientation.secondAngle = -orientation.secondAngle;
        }
        if ((axesSigns.bVal & AxesSigns.PPN.bVal) == AxesSigns.PPN.bVal) {
            orientation.thirdAngle = -orientation.thirdAngle;
        }
        return orientation;
    }

    public Orientation getAngularOrientation() {
        return getAngularOrientation(preferred);
    }
}
