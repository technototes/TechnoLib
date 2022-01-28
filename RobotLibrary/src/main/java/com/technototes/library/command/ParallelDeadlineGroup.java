package com.technototes.library.command;

/** Command group to run commands in parallel until one finished
 * @author Alex Stedman
 */
public class ParallelDeadlineGroup extends CommandGroup {

    private Command deadline;

    /** Make parallel deadline group
     *
     * @param command the deadline condition
     * @param commands The commands for the group
     */
    public ParallelDeadlineGroup(Command command, Command... commands) {
        super(true, commands);
        addCommands(command);
        deadline = command;
    }

    @Override
    public void schedule(Command c) {
        CommandScheduler.getInstance().scheduleWithOther(this, c);
    }


    @Override
    public boolean isFinished() {
        return deadline.justFinished();
    }

}
