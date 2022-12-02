package com.technototes.library.command;

/**
 * Command group to run commands in parallel until *one* is finished
 *
 * @author Alex Stedman
 */
public class ParallelRaceGroup extends CommandGroup {

    /**
     * Make parallel race group
     *
     * @param commands The commands for the group
     */
    public ParallelRaceGroup(Command... commands) {
        super(true, commands);
    }

    /**
     * Add one more command to the list of commands that will be run at the same time
     *
     * @param c The command to add to the group
     */
    @Override
    public void schedule(Command c) {
        CommandScheduler.getInstance().scheduleWithOther(this, c);
    }

    /**
     * Is this finished?
     *
     * @return True if *any* of the commands are finished, false if *none* have finished.
     */
    @Override
    public boolean isFinished() {
        // if there is a single finished command
        return commandMap.containsValue(true);
    }
}
