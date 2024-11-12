package com.technototes.library.hardware.sensor;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;
import com.qualcomm.hardware.adafruit.AdafruitBNO055IMUNew;
import com.qualcomm.hardware.bosch.BNO055IMU;
import java.util.function.DoubleSupplier;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class AdafruitIMU extends Sensor<AdafruitBNO055IMU> implements IGyro, DoubleSupplier {

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
        this.getRawDevice().initialize(new BNO055IMU.Parameters());
        resetRadians = getHeadingInRadians();
    }

    public AdafruitIMU(AdafruitBNO055IMU device, String deviceName, Orientation o) {
        super(device, deviceName);
        imuDirection = o;
        units = AngleUnit.DEGREES;
        this.getRawDevice().initialize(new BNO055IMU.Parameters());
        resetRadians = getHeadingInRadians();
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
        resetRadians = getHeadingInRadians() - AngleUnit.RADIANS.fromUnit(u, newHeading);
    }

    public double getVelocity(AngleUnit u) {
        AngularVelocity av = this.getRawDevice().getAngularVelocity(u);
        switch (imuDirection) {
            case Yaw:
                return av.yRotationRate;
            case ZYaw:
                return -av.yRotationRate;
            case Pitch:
                return av.xRotationRate;
            case ZPitch:
                return -av.xRotationRate;
            case Roll:
                return av.zRotationRate;
            case ZRoll:
                return -av.zRotationRate;
        }
        return 0.0;
    }

    private double getRawValue(AngleUnit u) {
        org.firstinspires.ftc.robotcore.external.navigation.Orientation ypr =
            this.getRawDevice().getAngularOrientation();
        switch (imuDirection) {
            case Yaw:
                return u.fromUnit(AngleUnit.RADIANS, ypr.secondAngle);
            case ZYaw:
                return u.fromUnit(AngleUnit.RADIANS, -ypr.secondAngle);
            case Pitch:
                return u.fromUnit(AngleUnit.RADIANS, ypr.firstAngle);
            case ZPitch:
                return u.fromUnit(AngleUnit.RADIANS, -ypr.firstAngle);
            case Roll:
                return u.fromUnit(AngleUnit.RADIANS, ypr.thirdAngle);
            case ZRoll:
                return u.fromUnit(AngleUnit.RADIANS, -ypr.thirdAngle);
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
