package com.technototes.library.command;

/** Command group to run commands in sequence
 * @author Alex Stedman
 */
public class SequentialCommandGroup extends CommandGroup {
    protected Command lastCommand;

    /** Make sequential command group
     *
     * @param commands The commands to run
     */
    public SequentialCommandGroup(Command... commands) {
        super(commands);
    }


    @Override
    public void schedule(Command c) {
        if(lastCommand == null){
             CommandScheduler.getInstance().scheduleWithOther(this, c);
        }else {
            CommandScheduler.getInstance().scheduleAfterOther(lastCommand, c);
        }
        lastCommand = c;
    }

    /** Returns if all the commands are finished
     *
     * @return Is the command group finished
     */
    @Override
    public boolean isFinished() {
        return !commandMap.containsValue(false);
    }


}
