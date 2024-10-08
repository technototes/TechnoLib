package com.technototes.library.subsystem.drivebase;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.motor.Motor;
import java.util.function.DoubleSupplier;

/**
 * Class for mecanum/xdrive drivebases
 *
 * @param <T> The motor type for the subsystem
 * @author Alex Stedman
 */
public class SimpleMecanumDrivebaseSubsystem<T extends DcMotorSimple> extends DrivebaseSubsystem<T> {

    /**
     * Drive motors
     */
    protected Motor<T> flMotor() {
        return motors[0];
    }

    protected Motor<T> frMotor() {
        return motors[1];
    }

    protected Motor<T> rlMotor() {
        return motors[2];
    }

    protected Motor<T> rrMotor() {
        return motors[3];
    }

    /**
     * Create mecanum drivebase
     *
     * @param flMotor The front left motor for the drivebase
     * @param frMotor The front right motor for the drivebase
     * @param rlMotor The rear left motor for the drivebase
     * @param rrMotor The rear right motor for the drivebase
     */
    public SimpleMecanumDrivebaseSubsystem(Motor<T> flMotor, Motor<T> frMotor, Motor<T> rlMotor, Motor<T> rrMotor) {
        super(flMotor, frMotor, rlMotor, rrMotor);
    }

    /**
     * Create mecanum drivebase
     *
     * @param gyro    The gyro supplier
     * @param flMotor The front left motor for the drivebase
     * @param frMotor The front right motor for the drivebase
     * @param rlMotor The rear left motor for the drivebase
     * @param rrMotor The rear right motor for the drivebase
     */
    public SimpleMecanumDrivebaseSubsystem(
        DoubleSupplier gyro,
        Motor<T> flMotor,
        Motor<T> frMotor,
        Motor<T> rlMotor,
        Motor<T> rrMotor
    ) {
        super(gyro, flMotor, frMotor, rlMotor, rrMotor);
    }

    public void joystickDrive(double x, double y, double rotation) {
        joystickDriveWithGyro(x, y, rotation, 0);
    }

    public void joystickDriveWithGyro(double x, double y, double rotation, double gyroAngle) {
        double speed = Range.clip(Math.abs(Math.hypot(x, y)), 0, 1);
        double headingRad = Math.toRadians(gyroAngle);
        double angle = -Math.atan2(y, x) + headingRad - Math.PI / 4;
        drive(speed, angle, rotation);
    }

    public void drive(double speed, double angle, double rotation) {
        double x = Math.cos(angle) * speed;
        double y = Math.sin(angle) * speed;

        double powerCompY = -(x + y);
        double powerCompX = x - y;

        speed = Range.clip(speed + Math.abs(rotation), 0, 1);

        double flPower = powerCompY - powerCompX - 2 * rotation;
        double frPower = -powerCompY - powerCompX - 2 * rotation;
        double rlPower = powerCompY + powerCompX - 2 * rotation;
        double rrPower = -powerCompY + powerCompX - 2 * rotation;

        double scale = getScale(flPower, frPower, rlPower, rrPower);
        scale = scale == 0 ? 0 : speed / scale;
        scale = Math.cbrt(scale);
        drive(flPower * scale, frPower * scale, rlPower * scale, rrPower * scale);
    }

    public void stop() {
        drive(0, 0, 0, 0);
    }

    public void drive(double flSpeed, double frSpeed, double rlSpeed, double rrSpeed) {
        motors[0].setPower(flSpeed * getSpeed());
        motors[1].setPower(frSpeed * getSpeed());
        motors[2].setPower(rlSpeed * getSpeed());
        motors[3].setPower(rrSpeed * getSpeed());
    }
}
