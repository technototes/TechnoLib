package com.technototes.library.command;

/**
 * @author Alex Stedman
 * class for commands with time to run
 */
public class WaitCommand extends Command {
    public double getSeconds() {
        return seconds;
    }

    private double seconds;
    public WaitCommand(double sec){
        seconds = sec;
    }

    @Override
    public boolean isFinished() {
        return seconds <= commandRuntime.seconds();
    }
}
