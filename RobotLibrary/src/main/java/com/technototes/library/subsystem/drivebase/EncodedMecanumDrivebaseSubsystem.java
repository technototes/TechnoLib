package com.technototes.library.subsystem.drivebase;

import com.technototes.library.hardware.motor.EncodedMotor;

import java.util.function.DoubleSupplier;
@Deprecated
public class EncodedMecanumDrivebaseSubsystem extends MecanumDrivebaseSubsystem<EncodedMotor<?>> {
    public EncodedMecanumDrivebaseSubsystem(EncodedMotor<?> flMotor, EncodedMotor<?> frMotor, EncodedMotor<?> rlMotor, EncodedMotor<?> rrMotor) {
        super(flMotor, frMotor, rlMotor, rrMotor);
    }

    public EncodedMecanumDrivebaseSubsystem(DoubleSupplier gyro, EncodedMotor<?> flMotor, EncodedMotor<?> frMotor, EncodedMotor<?> rlMotor, EncodedMotor<?> rrMotor) {
        super(gyro, flMotor, frMotor, rlMotor, rrMotor);
    }

}
