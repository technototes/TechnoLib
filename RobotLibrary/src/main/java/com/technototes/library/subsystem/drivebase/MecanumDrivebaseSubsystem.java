package com.technototes.library.subsystem.drivebase;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.motor.Motor;

import java.util.function.DoubleSupplier;

/** Class for mecanum/xdrive drivebases
 * @author Alex Stedman
 * @param <T> The motor type for the subsystem
 */
public class MecanumDrivebaseSubsystem<T extends DcMotorSimple> extends DrivebaseSubsystem<T>{
    /** Drive motors
     *
     */
    public Motor<T> flMotor, frMotor, rlMotor, rrMotor;

    /** Create mecanum drivebase
     *
     * @param flMotor The front left motor for the drivebase
     * @param frMotor The front right motor for the drivebase
     * @param rlMotor The rear left motor for the drivebase
     * @param rrMotor The rear right motor for the drivebase
     */
    public MecanumDrivebaseSubsystem(Motor<T> flMotor, Motor<T> frMotor, Motor<T> rlMotor, Motor<T> rrMotor) {
        super(flMotor, frMotor, rlMotor, rrMotor);
        this.flMotor = flMotor;
        this.frMotor = frMotor;
        this.rlMotor = rlMotor;
        this.rrMotor = rrMotor;
    }


    /** Create mecanum drivebase
     *
     * @param gyro The gyro supplier
     * @param flMotor The front left motor for the drivebase
     * @param frMotor The front right motor for the drivebase
     * @param rlMotor The rear left motor for the drivebase
     * @param rrMotor The rear right motor for the drivebase
     */
    public MecanumDrivebaseSubsystem(DoubleSupplier gyro, Motor<T> flMotor, Motor<T> frMotor, Motor<T> rlMotor, Motor<T> rrMotor) {
        super(gyro, flMotor, frMotor, rlMotor, rrMotor);
        this.flMotor = flMotor;
        this.frMotor = frMotor;
        this.rlMotor = rlMotor;
        this.rrMotor = rrMotor;
    }

    public void joystickDrive(double x, double y, double rotation){
        joystickDriveWithGyro(x, y, rotation, 0);
    }

    public void joystickDriveWithGyro(double x, double y, double rotation, double gyroAngle) {
        double speed = Range.clip(Math.abs(Math.hypot(x, y)), 0, 1);
        double headingRad = Math.toRadians(gyroAngle);
        double angle = -Math.atan2(y, x) + headingRad - Math.PI/4;
        drive(speed, angle, rotation);
    }
    public void drive(double speed, double angle, double rotation) {
        double x = Math.cos(angle) * speed;
        double y = Math.sin(angle) * speed;

        double powerCompY = -(x + y);
        double powerCompX = x - y;

        speed = Range.clip(speed + Math.abs(rotation), 0, 1);

        double flPower = powerCompY - powerCompX - 2*rotation;
        double frPower = -powerCompY - powerCompX - 2*rotation;
        double rlPower = powerCompY + powerCompX - 2*rotation;
        double rrPower = -powerCompY + powerCompX - 2*rotation;

        double scale = getScale(flPower, frPower, rlPower, rrPower);
        scale = scale == 0 ? 0 : speed/scale;
        scale = Math.cbrt(scale);
        drive(flPower*scale, frPower*scale,rlPower*scale, rrPower*scale);
    }

    public void stop() {
        drive(0, 0, 0, 0);
    }

    public void drive(double flSpeed, double frSpeed, double rlSpeed, double rrSpeed) {
        flMotor.setSpeed(flSpeed*getSpeed());
        frMotor.setSpeed(frSpeed*getSpeed());
        rlMotor.setSpeed(rlSpeed*getSpeed());
        rrMotor.setSpeed(rrSpeed*getSpeed());
    }

}
