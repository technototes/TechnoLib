package com.technototes.library.command;

import java.util.HashMap;
import java.util.Map;

/** Root class for all command groups
 * @author Alex Stedman
 */
public abstract class CommandGroup implements Command {
    protected Map<Command, Boolean> commandMap;
    protected boolean countCancel, anyCancelled;

    /** Create a command group with commands
     *
     * @param commands Commands for group
     */
    public CommandGroup(boolean countCancel, Command... commands) {
        commandMap = new HashMap<>();
        addCommands(commands);
        this.countCancel = countCancel;
    }

    /** Add a command to the group
     *
     * @param commands The command
     * @return this
     */
    public CommandGroup addCommands(Command... commands){
        for(Command c : commands){
            schedule(c);
            commandMap.put(c, false);
        }
        return this;
    }

    public CommandGroup countCancel(){
        countCancel = true;
        return this;
    }
    public CommandGroup ignoreCancel(){
        countCancel = false;
        return this;
    }


    public abstract void schedule(Command c);

    @Override
    public void initialize() {
        commandMap.replaceAll((command, bool) -> false);
        anyCancelled = false;
    }

    @Override
    public void execute() {
        //makes true if command just finished
        commandMap.replaceAll((command, bool) -> (countCancel ? command.justFinished() : command.justFinishedNoCancel()) || bool);
         anyCancelled = commandMap.keySet().stream().anyMatch(Command::isCancelled) || anyCancelled;
    }

    @Override
    /** Return if commandgroup is finished
     *
     */
    public abstract boolean isFinished();

    @Override
    public void end(boolean cancel) {
        commandMap.keySet().forEach(Command::cancel);
    }
}
