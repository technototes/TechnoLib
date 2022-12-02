package com.technototes.library.command;

/**
 * Command base class for people who like parity with wpilib
 * <p>
 * Deprecated because I don't care about wpilib in the least
 */
@Deprecated
public abstract class CommandBase implements Command {

    /**
     * Execute the command
     */
    @Override
    public void execute() {}

    /**
     * Is this command finished
     *
     * @return
     */
    @Override
    public boolean isFinished() {
        return false;
    }
}
