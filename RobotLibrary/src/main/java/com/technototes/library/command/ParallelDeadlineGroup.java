package com.technototes.library.command;

/**
 * Command group to run commands in parallel until one particular command completes
 *
 * @author Alex Stedman
 */
public class ParallelDeadlineGroup extends CommandGroup {

    private Command deadline;

    /**
     * Make parallel deadline group
     *
     * @param command  the deadline condition (Once this is complete, the rest are cancelled)
     * @param commands The other commands for the group
     */
    public ParallelDeadlineGroup(Command command, Command... commands) {
        super(true, commands);
        addCommands(command);
        deadline = command;
    }

    /**
     * Add another command to the group to be run while waiting for the 'deadline' command to finish
     *
     * @param c The command to add to the command group
     */
    @Override
    public void schedule(Command c) {
        CommandScheduler.getInstance().scheduleWithOther(this, c);
    }

    /**
     * @return True if the 'deadline' command has finished
     */
    @Override
    public boolean isFinished() {
        return deadline.justFinished();
    }
}
