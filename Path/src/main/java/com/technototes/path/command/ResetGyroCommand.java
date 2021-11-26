package com.technototes.path.command;

import com.technototes.library.command.Command;
import com.technototes.path.subsystem.MecanumDrivebaseSubsystem;


public class ResetGyroCommand implements Command {
    public MecanumDrivebaseSubsystem subsystem;
    public ResetGyroCommand(MecanumDrivebaseSubsystem s){
        subsystem = s;
    }
    @Override
    public void execute() {
        subsystem.zeroExternalHeading();
    }
}
