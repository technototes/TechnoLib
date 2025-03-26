package com.technototes.library.command;

import java.util.function.Consumer;

public class TimedCommand extends WaitCommand {
    protected double timeToRun;
    protected Consumer<Double> function;

    public TimedCommand(double time, Consumer<Double> func){
        super(time);
        function = func;
    }

    @Override
    public void execute() {
        function.accept(getRuntime().time());
    }
}
