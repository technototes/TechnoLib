package com.technototes.library.command;

import java.util.HashMap;
import java.util.Map;

/** Root class for all command groups
 * @author Alex Stedman
 */
public abstract class CommandGroup extends Command {
    protected Map<Command, Boolean> commandMap;


    /** Create a command group with commands
     *
     * @param commands Commands for group
     */
    public CommandGroup(Command... commands) {
        commandMap = new HashMap<>();
        for(Command c : commands){
            addCommand(c);
        }
    }

    /** Add a command to the group
     *
     * @param command The command
     * @return this
     */
    public CommandGroup addCommand(Command command){
        commandMap.put(command, false);
        schedule(command);
        return this;
    }

    public abstract void schedule(Command c);


    @Override
    /** Return if its is finished
     *
     */
    public abstract boolean isFinished();

    @Override
    public void end(boolean cancel) {
        if(cancel){
            for (Map.Entry<Command, Boolean> entry : commandMap.entrySet()) {
                if (!entry.getValue()) entry.getKey().end(true);
            }
        }
        commandMap.replaceAll((command, bool) -> false);
    }
}
