package com.technototes.library.command;

public class CommandForTesting implements Command {

    public int initialized = 0;
    public int executed = 0;
    public int ended = 0;
    public int canceled = 0;

    @Override
    public void initialize() {
        initialized++;
    }

    @Override
    public void execute() {
        executed++;
    }

    @Override
    public void end(boolean cancel) {
        ended++;
        if (cancel) {
            canceled++;
        }
    }

    public boolean check(int i, int x, int e, int c) {
        return initialized == i && executed == x && ended == e && canceled == c;
    }
}
