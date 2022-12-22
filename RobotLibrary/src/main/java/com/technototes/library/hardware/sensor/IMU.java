package com.technototes.library.hardware.sensor;

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

    private com.qualcomm.robotcore.hardware.IMU.Parameters parameters;
    // This is the preferred units (Degrees/radians)
    private AngleUnit preferred = AngleUnit.DEGREES;
    // This is an offset from 0 so we can zero the IMU without needing to be facing forward
    private double angleOffset;
    private AxesOrder axesOrder;
    private AxesSigns axesSigns;

    /**
     * Make an imu
     *
     * @param device The imu device
     */
    public IMU(com.qualcomm.robotcore.hardware.IMU device) {
        super(device);
        angleOffset = 0.0;

        // TODO: Validate this on hardware!
        axesOrder = AxesOrder.ZXY;
        axesSigns = AxesSigns.PPP;

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
        angleOffset = 0.0;
        // TODO: Validate this on hardware!
        axesOrder = AxesOrder.ZXY;
        axesSigns = AxesSigns.PPP;

        degrees();
        initialize();
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
    public IMU initialize() {
        // TODO: fix this?
        // getDevice().initialize(parameters);
        return this;
    }

    // Gets the Yaw (orientation along the z-axis) with no adjustment
    private double getRawYaw(AngleUnit unit) {
        // TODO: Deal with the AxesSign thing properly. Cuz it does nothing here, AFAICT
        return getDevice().getRobotYawPitchRollAngles().getYaw(unit);
    }

    /**
     * Get gyro heading
     *
     * @return The gyro heading (in preferred units, from -180/pi to +180/pi
     */
    @Override
    public double gyroHeading() {
        return preferred.normalize(getRawYaw(preferred));
    }

    /**
     * Get the gyro heading in the provided units
     *
     * @param unit the unit desired
     * @return The heading in 'unit' units from -180/pi to +180/pi
     */
    public double gyroHeading(AngleUnit unit) {
        return unit.normalize(getRawYaw(unit) - unit.fromUnit(preferred, angleOffset));
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
        angleOffset = getRawYaw(preferred) - newHeading;
    }

    /**
     * Remaps the axes for the IMU in the order and sign provided
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
     * If you have code that was using that layout, this is what you need to call.
     * I expect to delete this code eventually...
     *
     * @param legacyOrder The *legacy* axis order desired
     * @param legacySigns The *legacy* signs desired
     * @return this (for chaining)
     */
    public IMU remapLegacyAxes(AxesOrder legacyOrder, AxesSigns legacySigns) {
        // The BNO055 has the X and Y axes rotated 90 degrees :/
        switch (legacyOrder) {
            case XZX:
                axesOrder = AxesOrder.YZY;
                // I think signs stay the same on this one...
                break;
            case XYX:
            case YXY:
            case YZY:
            case ZYZ:
            case ZXZ:
            case XZY:
            case XYZ:
            case YXZ:
            case YZX:
            case ZYX:
            case ZXY:
        }
        return this;
    }

    /**
     * Gets the angular velocity (in default units)
     *
     * @return The Angular Velocity
     */
    public AngularVelocity getAngularVelocity() {
        return device.getRobotAngularVelocity(preferred);
    }

    /**
     * Gets the Angular orientation of the IMU
     *
     * @return the Orientation of the IMU
     */
    public Orientation getAngularOrientation() {
        Orientation res = getDevice().getRobotOrientation(AxesReference.INTRINSIC, axesOrder, preferred);
        if ((axesSigns.bVal & AxesSigns.NPP.bVal) == AxesSigns.NPP.bVal) {
            res.firstAngle = -res.firstAngle;
        }
        if ((axesSigns.bVal & AxesSigns.PNP.bVal) == AxesSigns.PNP.bVal) {
            res.secondAngle = -res.secondAngle;
        }
        if ((axesSigns.bVal & AxesSigns.PPN.bVal) == AxesSigns.PPN.bVal) {
            res.thirdAngle = -res.thirdAngle;
        }
        return res;
    }
}
