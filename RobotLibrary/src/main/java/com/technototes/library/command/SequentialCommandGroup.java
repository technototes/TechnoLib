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
        lastCommand = null;
    }


    @Override
    public void schedule(Command c) {
        if(lastCommand == null){
            this.with(c);
        } else{
            lastCommand.then(c);
        }
        lastCommand = c;
    }

    /** Returns if all the commands are finished
     *
     * @return Is the command group finished
     */
    @Override
    public boolean isFinished() {
        return lastCommand.justFinished();
    }
}
