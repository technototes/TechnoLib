package com.technototes.library.command;

import androidx.annotation.Nullable;
import java.util.function.BooleanSupplier;

/**
 * Simple class for commands that require a certain condition to be true to run
 * <p>
 * This encapsulates *two* different capabilities.
 * 1. A ConditionalCommand with only a condition, but no commands, is a a "wait" command.
 * It will only finish once the condition is true.
 * 2. A ConditionalCommand with a condition and a true (and optionally false) command
 * is a generic "if/else" command: If the condition is true, execute the 'true command',
 * if not true, execute the 'false command' if it is supplied.
 * <p>
 * TODO: This makes the class a little clunky. It wouldn't be terrible to break the functionality
 * TODO: out into multiple classes instead
 *
 * @author Alex Stedman
 */
public class ConditionalCommand implements Command {

    private BooleanSupplier supplier;

    @Nullable
    private Command trueCommand, falseCommand;

    /**
     * This makes a "wait" command
     *
     * @param condition The BooleanSupplier that will be waited upon until true
     */
    public ConditionalCommand(BooleanSupplier condition) {
        supplier = condition;
        trueCommand = null;
        falseCommand = null;
    }

    /**
     * Make a conditional command
     *
     * @param condition The condition
     * @param command   The command to run when the condition is true.
     */
    public ConditionalCommand(BooleanSupplier condition, Command command) {
        supplier = condition;
        trueCommand = command;
        CommandScheduler.getInstance().scheduleWithOther(this, trueCommand, condition);
        falseCommand = null;
    }

    /**
     * Make a conditional command
     *
     * @param condition The condition
     * @param trueC     The command to run when the condition is true
     * @param falseC    The command to run when the condition is false
     */
    public ConditionalCommand(BooleanSupplier condition, Command trueC, Command falseC) {
        supplier = condition;
        trueCommand = trueC;
        falseCommand = falseC;
        CommandScheduler.getInstance().scheduleWithOther(this, trueCommand, condition);
        CommandScheduler
            .getInstance()
            .scheduleWithOther(this, falseCommand, () -> !condition.getAsBoolean());
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        if (trueCommand == null) return supplier.getAsBoolean();
        if (falseCommand == null) return trueCommand.justFinished();
        return trueCommand.justFinished() || falseCommand.justFinished();
    }
}
