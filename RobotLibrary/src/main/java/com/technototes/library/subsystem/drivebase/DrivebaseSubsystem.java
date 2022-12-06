package com.technototes.library.subsystem.drivebase;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.motor.MotorGroup;
import com.technototes.library.subsystem.DeviceSubsystem;
import java.util.function.DoubleSupplier;

/**
 * Class for DriveBase subsystems
 *
 * @param <T> The type of motors for the drivebase
 * @author Alex Stedman The motors for the drivebase
 */
public abstract class DrivebaseSubsystem<T extends DcMotorSimple>
    extends DeviceSubsystem<MotorGroup<T>> {

    /**
     * Override this to get the gyroscope heading.
     * Or pass it in to the constructor. Which ever works best for your drivebase implementation.
     */
    protected DoubleSupplier gyroSupplier = () -> 0;

    /**
     * Create a drivebase subsystem
     *
     * @param motors The drive motors
     */
    public DrivebaseSubsystem(Motor<T>... motors) {
        super(new MotorGroup<T>(motors));
    }

    /**
     * Create a drivebase subsystem
     *
     * @param gyro   The gyro supplier
     * @param motors The drive motors
     */
    public DrivebaseSubsystem(DoubleSupplier gyro, Motor<T>... motors) {
        super(new MotorGroup<T>(motors));
        gyroSupplier = gyro;
    }

    /**
     * This will give you a *positive* value to scale the value to such that
     * the largest value of the list will be |1| (negative or positive).
     * <p>
     * This is helpful for an X-Drive drivebase, as the algorithm for calucating
     * motor power will often give a collection of values, all of which are smaller than 1.
     *
     * @param powers The list of values you're setting
     * @return The number to divide the values by to set the largest to 1/-1
     */
    public double getScale(double... powers) {
        double max = 0;
        for (double d : powers) {
            max = Math.max(Math.abs(d), max);
        }
        return max;
    }

    /**
     * Get the Gyro angle
     *
     * @return Gyro angle from supplier
     */
    public double getGyro() {
        return gyroSupplier.getAsDouble();
    }

    /**
     * Override this one, I guess? Not sure how useful it is.
     *
     * @return the speed?
     */
    public double getSpeed() {
        return 1;
    }
}
