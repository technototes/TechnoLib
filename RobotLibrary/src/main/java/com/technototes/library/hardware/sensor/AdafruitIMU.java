package com.technototes.library.hardware.sensor;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMUNew;
import java.util.function.DoubleSupplier;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class AdafruitIMU extends Sensor<AdafruitBNO055IMUNew> implements IGyro, DoubleSupplier {

    public enum Orientation {
        Yaw,
        Pitch,
        Roll,
        ZYaw,
        ZPitch,
        ZRoll,
    }

    private final Orientation imuDirection;
    private double resetRadians;
    private AngleUnit units;

    public AdafruitIMU(String deviceName, Orientation o) {
        super(deviceName);
        imuDirection = o;
        units = AngleUnit.DEGREES;
        zero();
    }

    public AngleUnit getUnits() {
        return units;
    }

    public void setUnits(AngleUnit u) {
        units = u;
    }

    public double getHeading(AngleUnit u) {
        return u.fromUnit(AngleUnit.RADIANS, getRawValue(AngleUnit.RADIANS) - resetRadians);
    }

    public void setHeading(double newHeading, AngleUnit u) {
        zero();
        resetRadians -= AngleUnit.RADIANS.fromUnit(u, newHeading);
    }

    public double getVelocity(AngleUnit u) {
        AngularVelocity av = this.getRawDevice().getRobotAngularVelocity(u);
        switch (imuDirection) {
            case Yaw:
                return u.fromUnit(AngleUnit.RADIANS, av.zRotationRate);
            case ZYaw:
                return u.fromUnit(AngleUnit.RADIANS, -av.zRotationRate);
            case Pitch:
                return u.fromUnit(AngleUnit.RADIANS, av.xRotationRate);
            case ZPitch:
                return u.fromUnit(AngleUnit.RADIANS, -av.xRotationRate);
            case Roll:
                return u.fromUnit(AngleUnit.RADIANS, av.yRotationRate);
            case ZRoll:
                return u.fromUnit(AngleUnit.RADIANS, -av.yRotationRate);
        }
        return 0.0;
    }

    private double getRawValue(AngleUnit u) {
        YawPitchRollAngles ypr = this.getRawDevice().getRobotYawPitchRollAngles();
        switch (imuDirection) {
            case Yaw:
                return ypr.getYaw(u);
            case ZYaw:
                return -ypr.getYaw(u);
            case Pitch:
                return ypr.getPitch(u);
            case ZPitch:
                return -ypr.getPitch(u);
            case Roll:
                return ypr.getRoll(u);
            case ZRoll:
                return -ypr.getRoll(u);
        }
        return 0.0;
    }

    @Override
    public double getAsDouble() {
        return getHeading();
    }

    @Override
    public String LogLine() {
        return String.valueOf(getHeading());
    }
}
