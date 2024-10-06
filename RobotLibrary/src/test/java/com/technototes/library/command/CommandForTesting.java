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

    /*
    RESET,
    STARTED,
    INITIALIZING,
    EXECUTING,
    FINISHED,
    INTERRUPTED,
    CANCELLED,
    */
    private int lastRes = 0;

    public int check(int i, int x, int e, int c) {
        int iCheck = (initialized == i) ? 0 : 1000;
        int xCheck = (executed == x) ? 0 : 100;
        int eCheck = (ended == e) ? 0 : 10;
        int cCheck = (canceled == c) ? 0 : 1;
        lastRes = iCheck + xCheck + eCheck + cCheck;
        return lastRes;
    }

    public String lastResult() {
        return String.format("%d %d %d %d", initialized, executed, ended, canceled);
    }
}
