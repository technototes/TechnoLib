package com.technototes.library.command;

/**
 * Each time this command(group) is scheduled, the next one
 * in the series will execute. When the last one is executed,
 * it starts over at the first one.
 *
 * The most obvious application is for "toggle" command:
 * something like this:
 * clawToggle = new CycleCommandGroup(claw::open, claw::close);
 */
public class CycleCommandGroup implements Command {
    protected Command[] commands;
    protected int currentState = 0;

    public CycleCommandGroup(Command... commands) {
        assert commands.length > 0;
        this.commands = commands;
    }

    public void execute() {
        commands[currentState].run();
        currentState = (currentState + 1) % commands.length;
    }
}
