package com.technototes.path.command;

import com.technototes.library.command.Command;
import com.technototes.path.subsystem.PathingMecanumDrivebaseSubsystem;

public class ResetGyroCommand implements Command {

    public PathingMecanumDrivebaseSubsystem subsystem;

    public ResetGyroCommand(PathingMecanumDrivebaseSubsystem s) {
        subsystem = s;
    }

    @Override
    public void execute() {
        subsystem.zeroExternalHeading();
    }
}
