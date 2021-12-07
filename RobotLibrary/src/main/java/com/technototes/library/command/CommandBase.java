package com.technototes.library.command;

/**
 * Command base class for people who like parity with wpilib
 */
public abstract class CommandBase implements Command {
    @Override
    public void execute() {

    }

    //also for parity
    @Override
    public boolean isFinished() {
        return false;
    }
}
