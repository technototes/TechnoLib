package com.technototes.library.command;

/**
 * Command group to run commands in parallel until all of them finish
 *
 * @author Alex Stedman
 */
public class ParallelCommandGroup extends CommandGroup {

    /**
     * Make parallel command group
     *
     * @param commands The commands for the group
     */
    public ParallelCommandGroup(Command... commands) {
        super(true, commands);
    }

    @Override
    public void schedule(Command c) {
        CommandScheduler.scheduleWithOther(this, c);
    }

    /**
     * @return True if *all* of the commands are finished
     */
    @Override
    public boolean isFinished() {
        // if there is no unfinished commands its done
        return !commandMap.containsValue(false);
    }
}
