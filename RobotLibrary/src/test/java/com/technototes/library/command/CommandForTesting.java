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

    public boolean check(int i, int x, int e, int c) {
        int iCheck = (initialized == i) ? 0 : 0xFF000000;
        int xCheck = (executed == x) ? 0 : 0xFF0000;
        int eCheck = (ended == e) ? 0 : 0xFF00;
        int cCheck = (canceled == c) ? 0 : 0xFF;
        lastRes = iCheck | xCheck | eCheck | cCheck;
        return lastRes == 0;
    }

    public String lastResult() {
        return String.format("%08x", lastRes);
    }
}
