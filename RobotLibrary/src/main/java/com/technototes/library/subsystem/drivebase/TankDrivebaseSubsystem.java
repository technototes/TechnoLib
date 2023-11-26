package com.technototes.library.subsystem.drivebase;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.motor.Motor;

/**
 * Class for drivebase subsystems
 *
 * @param <T> The type of motor for the drivebase
 * @author Alex Stedman
 */
public class TankDrivebaseSubsystem<T extends DcMotorSimple> extends DrivebaseSubsystem<T> {

    /**
     * Drive motors
     */
    protected Motor<T> leftSide() {
        return motors[0];
    }

    protected Motor<T> rightSide() {
        return motors[1];
    }

    /**
     * Create tank drivebase
     *
     * @param leftMotor  The motor/motorgroup for the left side of the drivebase
     * @param rightMotor The motor/motorgroup for the right side of the drivebase
     */
    public TankDrivebaseSubsystem(Motor<T> leftMotor, Motor<T> rightMotor) {
        super(leftMotor, rightMotor);
    }

    public void arcadeDrive(double y, double x) {
        double lp = y + x;
        double rp = -y + x;
        double scale = getScale(lp, rp);
        double speed = Range.clip(Math.abs(y) + Math.abs(x), 0, 1);
        scale = scale == 0 ? 0 : speed / scale;
        drive(lp * scale, rp * scale);
    }

    public void stop() {
        drive(0, 0);
    }

    public void drive(double l, double r) {
        motors[0].setPower(l * getSpeed());
        motors[1].setPower(r * getSpeed());
    }
}
