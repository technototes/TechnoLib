package com.technototes.library.command;

/**
 * A grouping command which runs a list of commands in sequence
 *
 * @author Alex Stedman
 */
public class SequentialCommandGroup extends CommandGroup {

    protected Command lastCommand;

    /**
     * Make sequential command group. By default if a command is cancelled, the next commend in
     * the sequence is run.
     *
     * @param commands The commands to run
     */
    public SequentialCommandGroup(Command... commands) {
        super(true, commands);
    }

    /**
     * This allows you to append another command to the Sequential Command Group
     *
     * @param c The command to add to the end of the list
     */
    @Override
    public void schedule(Command c) {
        if (lastCommand == null) {
            CommandScheduler.getInstance().scheduleWithOther(this, c);
        } else {
            CommandScheduler.getInstance().scheduleAfterOther(lastCommand, c);
        }
        lastCommand = c;
    }

    /**
     * Returns if all the commands are finished
     *
     * @return Is the command group finished
     */
    @Override
    public boolean isFinished() {
        return lastCommand.justFinished() || (anyCancelled && !countCancel);
    }
}
