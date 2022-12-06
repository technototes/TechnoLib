package com.technototes.library.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Root class for all command groups (Sequential, Parallel, etc...)
 * WARNING: You probably will be better served by the specific CommandGroup subclasses, rather than
 * using this one directly.
 *
 * @author Alex Stedman
 */
public abstract class CommandGroup implements Command {

    /**
     * This is a map from the command to whether it has been run
     */
    protected Map<Command, Boolean> commandMap;
    /**
     * Should a cancelled command be considered 'finished'
     */
    protected boolean countCancel;
    /**
     * Have *any* of the command list been cancelled
     */
    protected boolean anyCancelled;

    /**
     * Create a command group with commands
     *
     * @param countCancel true if a cancelled command is considered 'finished'
     * @param commands    Commands for group
     */
    public CommandGroup(boolean countCancel, Command... commands) {
        commandMap = new HashMap<>();
        addCommands(commands);
        this.countCancel = countCancel;
    }

    /**
     * Add a command to the group
     *
     * @param commands The command
     * @return this
     */
    public CommandGroup addCommands(Command... commands) {
        for (Command c : commands) {
            schedule(c);
            commandMap.put(c, false);
        }
        return this;
    }

    /**
     * Specify that this CommandGroup should count a cancellation as 'completed'
     *
     * @return this CommandGroup
     */
    public CommandGroup countCancel() {
        countCancel = true;
        return this;
    }

    /**
     * Specify that this CommandGroup should NOT count cancellation as 'completed'
     *
     * @return this CommandGroup
     */
    public CommandGroup ignoreCancel() {
        countCancel = false;
        return this;
    }

    /**
     * This should schedule the command as part of this command group, I think.
     * TODO: Is this correct?
     *
     * @param c The command to add to the command group
     */
    public abstract void schedule(Command c);

    /**
     * Mark all commands in the group as not yet run
     */
    @Override
    public void initialize() {
        commandMap.replaceAll((command, bool) -> false);
        anyCancelled = false;
    }

    @Override
    public void execute() {
        // makes true if command just finished
        commandMap.replaceAll((command, bool) ->
            (countCancel ? command.justFinished() : command.justFinishedNoCancel()) || bool
        );
        anyCancelled = commandMap.keySet().stream().anyMatch(Command::isCancelled) || anyCancelled;
    }

    /**
     * MUST IMPLEMENT IN SUBCLASSES:
     *
     * @return True if this CommandGroup is finished
     */
    @Override
    public abstract boolean isFinished();

    /**
     * This stops the command group from executing
     *
     * @param cancel True if the command was cancelled, False if it ended naturally
     */
    @Override
    public void end(boolean cancel) {
        commandMap.keySet().forEach(Command::cancel);
    }
}
