package com.technototes.path.command;

import com.acmerobotics.roadrunner.drive.DriveSignal;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.technototes.library.command.Command;
import com.technototes.path.subsystem.MecanumDrivebaseSubsystem;


import java.util.function.DoubleSupplier;

public class MecanumDriveCommand implements Command {
    public MecanumDrivebaseSubsystem subsystem;
    public DoubleSupplier x, y, r;
    public MecanumDriveCommand(MecanumDrivebaseSubsystem sub, DoubleSupplier xSup, DoubleSupplier ySup, DoubleSupplier rSup) {
        addRequirements(sub);
        subsystem = sub;
        x = xSup;
        y = ySup;
        r = rSup;
    }

    @Override
    public void execute() {
             Vector2d input = new Vector2d(
                -y.getAsDouble()*subsystem.speed,
                -x.getAsDouble()*subsystem.speed
        ).rotated(-subsystem.getExternalHeading());

        subsystem.setWeightedDrivePower(
                new Pose2d(
                        input.getX(),
                        input.getY(),
                        -Math.pow(r.getAsDouble()*subsystem.speed, 3)
                )
        );
    }


    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean cancel) {
        if(cancel) subsystem.setDriveSignal(new DriveSignal());
    }
}
