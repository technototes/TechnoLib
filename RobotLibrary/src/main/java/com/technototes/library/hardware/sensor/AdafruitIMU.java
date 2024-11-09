package com.technototes.library.hardware.sensor;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMUNew;
import java.util.function.DoubleSupplier;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class AdafruitIMU extends Sensor<AdafruitBNO055IMUNew> implements IGyro, DoubleSupplier {

    public enum Orientation {
        Yaw,
        Pitch,
        Roll,
        NYaw,
        NPitch,
        NRoll,
    }

    private final Orientation imuDirection;
    private double resetDegrees;
    private AngleUnit unit;

    public AdafruitIMU(String deviceName, Orientation o) {
        super(deviceName);
        imuDirection = o;
        unit = AngleUnit.DEGREES;
        zero();
    }

    @Override
    public double gyroHeading() {
        return unit.fromUnit(AngleUnit.DEGREES, getDegValue() - resetDegrees);
    }

    public AdafruitIMU setDegrees() {
        unit = AngleUnit.DEGREES;
        return this;
    }

    public AdafruitIMU setRadians() {
        unit = AngleUnit.RADIANS;
        return this;
    }

    public AdafruitIMU setUnits(AngleUnit u) {
        unit = u;
        return this;
    }

    @Override
    public double gyroHeadingInDegrees() {
        return getDegValue() - resetDegrees;
    }

    @Override
    public double gyroHeadingInRadians() {
        return Math.toRadians(gyroHeadingInDegrees());
    }

    @Override
    public void setHeading(double newHeading) {
        zero();
        // TODO: Haven't thought about this yet. Might be -=
        resetDegrees += AngleUnit.DEGREES.fromUnit(unit, newHeading);
    }

    @Override
    public void zero() {
        resetDegrees = getDegValue();
    }

    @Override
    public String LogLine() {
        return String.valueOf(gyroHeadingInDegrees());
    }

    private double getDegValue() {
        YawPitchRollAngles ypr = this.getRawDevice().getRobotYawPitchRollAngles();
        switch (imuDirection) {
            case Yaw:
                return ypr.getYaw(AngleUnit.DEGREES);
            case NYaw:
                return -ypr.getYaw(AngleUnit.DEGREES);
            case Pitch:
                return ypr.getPitch(AngleUnit.DEGREES);
            case NPitch:
                return -ypr.getPitch(AngleUnit.DEGREES);
            case Roll:
                return ypr.getRoll(AngleUnit.DEGREES);
            case NRoll:
                return -ypr.getRoll(AngleUnit.DEGREES);
        }
        return 0.0;
    }

    @Override
    public double getAsDouble() {
        return unit.fromUnit(AngleUnit.DEGREES, gyroHeadingInDegrees());
    }
}
