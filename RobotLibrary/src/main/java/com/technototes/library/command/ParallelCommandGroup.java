package com.technototes.library.command;

/** Command group to run commands in parallel until all finish
 * @author Alex Stedman
 */
public class ParallelCommandGroup extends CommandGroup {
    /** Make empty group
     *
     */
    public ParallelCommandGroup(){
        super();
    }

    /** Make parallel command group
     *
     * @param commands The commands for the group
     */
    public ParallelCommandGroup(Command... commands) {
        super(commands);
    }

    /** Runs the commands in parallel
     *
     */
    @Override
    public void runCommands() {
        for(Command c : commands){
            c.run();
        }

    }

    /** Is this finished
     *
     * @return If all of the commands are finished
     */
    @Override
    public boolean isFinished() {
        for (Command c : commands) {
            if (c.commandState != CommandState.RESET) {
                return false;
            }
        }
        return true;
    }
}
