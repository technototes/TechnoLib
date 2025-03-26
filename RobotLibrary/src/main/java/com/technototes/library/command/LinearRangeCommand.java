package com.technototes.library.command;

import java.util.function.Consumer;

public class LinearRangeCommand extends TimedCommand {
    double startVal, endVal;

    public LinearRangeCommand(double time, double start, double end, Consumer<Double> func) {
        super(time, func);
        startVal = start;
        endVal = end;
    }

    @Override
    public void execute() {
        // Being explicitly pedantic about pre-algebra, here...
        double M = (endVal - startVal) / startVal;
        double X = getRuntime().time();
        double B = startVal;

        double Y = M * X + B;

        function.accept(Y);
    }
}
