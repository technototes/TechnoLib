package com.technototes.library.command;

import java.util.function.DoubleSupplier;

/**
 * A command to do nothing but wait for a span of time.
 * In an ideal world, you wouldn't need this. But the world is far from ideal...
 *
 * @author Alex Stedman
 */
public class WaitCommand implements Command {

    /**
     * @return the number of seconds the command will wait
     */
    public double getSeconds() {
        return supplier.getAsDouble();
    }

    private DoubleSupplier supplier;

    /**
     * Create a wait command for a fixed number of seconds
     *
     * @param sec The number of seconds (can be non-whole numbers!)
     */
    public WaitCommand(double sec) {
        supplier = () -> sec;
    }

    /**
     * Create a wait command for a number of seconds that can be calculated when the commannd is
     * triggered
     *
     * @param sup The DoublerSupplier function which calculates the number of seconds to wait
     */
    public WaitCommand(DoubleSupplier sup) {
        supplier = sup;
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return supplier.getAsDouble() <= getRuntime().seconds();
    }
}
