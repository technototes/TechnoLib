package com.technototes.library.subsystem.drivebase;

import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.motor.Motor;

/** Class for drivebase subsystems
 * @author Alex Stedman
 * @param <T> The type of motor for the drivebase
 */
public class TankDrivebaseSubsystem<T extends Motor> extends DrivebaseSubsystem<T> {
    /** Drive motors
     *
     */
    public T leftSide, rightSide;

    /** Create tank drivebase
     *
     * @param leftMotor The motor/motorgroup for the left side of the drivebase
     * @param rightMotor The motor/motorgroup for the right side of the drivebase
     */
    public TankDrivebaseSubsystem(T leftMotor, T rightMotor) {
        super(leftMotor, rightMotor);
        leftSide = leftMotor;
        rightSide = rightMotor;
    }

    public void arcadeDrive(double y, double x){
        double lp = y+x;
        double rp = -y+x;
        double scale = getScale(lp, rp);
        double speed = Range.clip(Math.abs(y)+Math.abs(x), 0, 1);
        scale = scale == 0 ? 0 : speed/scale;
        drive(lp*scale, rp*scale);
    }

    public void stop() {
        drive(0, 0);
    }

    public void drive(double l, double r) {
        leftSide.setSpeed(l*getSpeed());
        rightSide.setSpeed(r*getSpeed());
    }
}
