package com.technototes.library.command;

import java.util.function.DoubleSupplier;

/**
 * @author Alex Stedman
 * class for commands with time to run
 */
public class WaitCommand implements Command {
    public double getSeconds() {
        return supplier.getAsDouble();
    }

    private DoubleSupplier supplier;
    public WaitCommand(double sec){
        supplier = ()->sec;
    }

    public WaitCommand(DoubleSupplier sup){
        supplier = sup;
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return supplier.getAsDouble() <= getRuntime().seconds();
    }
}
