package com.technototes.library.subsystem.drivebase;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.motor.MotorGroup;
import com.technototes.library.subsystem.DeviceSubsystem;

import java.util.function.DoubleSupplier;

/** Class for DriveBase subsystems
 * @author Alex Stedman The motors for the drivebase
 * @param <T> The type of motors for the drivebase
 */
public abstract class DrivebaseSubsystem<T extends DcMotorSimple> extends DeviceSubsystem<MotorGroup<T>> {

    protected DoubleSupplier gyroSupplier = () -> 0;


    @Deprecated
    public enum SampleDriveSpeed {
        SNAIL(0.2), NORMAL(0.5), TURBO(1);
        public double spe;
        SampleDriveSpeed(double s){
            spe = s;
        }
        public double getSpeed(){
            return spe;
        }
    }


    /** The default drive speeds
     *
     */
    @Deprecated
    public SampleDriveSpeed driveSpeed = SampleDriveSpeed.NORMAL;

    /** Create a drivebase subsystem
     *
     * @param motors The drive motors
     */
    public DrivebaseSubsystem(Motor<T>... motors) {
        super(new MotorGroup<T>(motors));
    }
    /** Create a drivebase subsystem
     * @param gyro The gyro supplier
     * @param motors The drive motors
     */
    public DrivebaseSubsystem(DoubleSupplier gyro, Motor<T>... motors) {
        super(new MotorGroup<T>(motors));
        gyroSupplier = gyro;
    }

    public double getScale(double... powers){
        double max = 0;
        for(double d : powers){
            max = Math.max(Math.abs(d), max);
        }
        return max;
    }
    /** Get the Gyro angle
     *
     * @return Gyro angle from supplier
     */
    public double getGyro(){
        return gyroSupplier.getAsDouble();
    }


    public double getSpeed(){
        return 1;
    }

}
