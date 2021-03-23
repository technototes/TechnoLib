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
        if(getCurrentCommand() != null) getCurrentCommand().run();
    }

    /** Returns if all the commands are finished
     *
     * @return Is the command group finished
     */
    @Override
    public boolean isFinished() {
        if (getCurrentCommand() != null) {
            if (getCurrentCommand().commandState == CommandState.RESET) {
                currentCommandIndex++;
            }
            return currentCommandIndex > commands.length - 1;
        }
        return true;
    }

    /** Get the command being currently run
     *
     * @return The current command
     */
    public Command getCurrentCommand() {
        try {
            return commands[currentCommandIndex];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("oman idk this issue but this works for now");
            return null;
        }
    }
}
