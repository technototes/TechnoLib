package com.technototes.library.command;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/** Root class for all command groups
 * @author Alex Stedman
 */
public abstract class CommandGroup extends Command {
    protected Command[] commands;

    /** Basic constructor
     *
     */
    public CommandGroup(){

    }
    /** Create a command group with commands
     *
     * @param command Commands for group
     */
    public CommandGroup(Command... command) {
        commands = command;
    }

    /** Add a command to the group
     *
     * @param command The command
     * @return this
     */
    public CommandGroup addCommand(Command command) {
        Command[] n = new Command[commands.length+1];
        for(int i = 0; i < commands.length; i++){
            n[i]=commands[i];
        }
        n[n.length-1] = command;
        commands = n;
        return this;
    }

    @Override
    public void run() {
        switch (commandState) {
            case RESET:
                commandRuntime.reset();
                commandState = CommandState.INITIALIZED;
            case INITIALIZED:
                runCommands();
                commandState = isFinished() ? CommandState.EXECUTED : CommandState.INITIALIZED;
                if(commandState != CommandState.EXECUTED){
                    return;
                }
            case EXECUTED:
                commandState = CommandState.RESET;
                return;
        }
    }


    /** Run the commands
     *
     */
    public abstract void runCommands();

    @Override
    /** Return if its is finished
     *
     */
    public abstract boolean isFinished();

}
