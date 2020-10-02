package com.technototes.library.command.simple;

import com.technototes.library.command.Command;
import com.technototes.library.control.gamepad.CommandGamepad;
import com.technototes.library.hardware.sensor.IMU;
import com.technototes.library.subsystem.drivebase.MecanumDrivebaseSubsystem;
import com.technototes.library.subsystem.drivebase.TankDrivebaseSubsystem;

import java.util.function.DoubleSupplier;

public class MecanumDriveCommand extends Command {
    public MecanumDrivebaseSubsystem subsystem;
    public DoubleSupplier xv, yv, tv;
    public DoubleSupplier gyro;
    public MecanumDriveCommand(MecanumDrivebaseSubsystem s, DoubleSupplier x, DoubleSupplier y, DoubleSupplier t){
        addRequirements(s);
        subsystem = s;
        xv = x;
        yv = y;
        tv = t;
    }
    public MecanumDriveCommand(MecanumDrivebaseSubsystem s, CommandGamepad.Stick s1, CommandGamepad.Stick s2){
        new MecanumDriveCommand(s, s1.x, s1.y, s2.x);
    }
    public MecanumDriveCommand setFieldCentric(DoubleSupplier d){
        gyro = d;
        return this;
    }
    public MecanumDriveCommand setFieldCentric(IMU m){
        return setFieldCentric(() -> m.gyroHeading());
    }
    public MecanumDriveCommand setFieldCentric(IMU m, double offset){
        return setFieldCentric(() -> m.gyroHeading()+offset);
    }
    @Override
    public void execute() {
        subsystem.joystickDrive(xv.getAsDouble(), yv.getAsDouble(), tv.getAsDouble(), gyro.getAsDouble());
    }
}
