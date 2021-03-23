package com.technototes.library.command;

/** Command group to run commands in sequence
 * @author Alex Stedman
 */
public class SequentialCommandGroup extends CommandGroup {
    private int currentCommandIndex = 0;

    /** Make sequential command group
     *
     */
    public SequentialCommandGroup(){
        super();
    }

    /** Make sequential command group
     *
     * @param commands The commands to run
     */
    public SequentialCommandGroup(Command... commands) {
        super(commands);
    }

    /** Run the commands in sequence
     *
     */
    @Override
    public void runCommands() {
        commands[currentCommandIndex].run();
    }

    /** Returns if all the commands are finished
     *
     * @return Is the command group finished
     */
    @Override
    public boolean isFinished() {
        if (commands[currentCommandIndex].commandState == CommandState.RESET) {
            currentCommandIndex++;
        }
        return currentCommandIndex > commands.length-1;
    }

}
