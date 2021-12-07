package com.technototes.library.control;

import com.technototes.library.command.Command;

import java.util.function.DoubleSupplier;
import java.util.function.Function;

/** Class for command axis for the gamepad
 * @author Alex Stedman
 */
public class CommandAxis extends GamepadAxis implements CommandInput<CommandAxis> {
    /** Make a command axis
     *
     * @param supplier The axis supplier
     */
    public CommandAxis(DoubleSupplier supplier){
        super(supplier);
    }

    /** Make a command axis
     *
     * @param supplier The axis supplier
     * @param threshold The threshold to trigger to make the axis behave as a button
     */
    public CommandAxis(DoubleSupplier supplier, double threshold){
        super(supplier, threshold);
    }

    @Override
    public CommandAxis getInstance() {
        return this;
    }

    @Override
    public CommandAxis setTriggerThreshold(double threshold) {
        super.setTriggerThreshold(threshold);
        return this;
    }

    public CommandAxis schedulePressed(Function<DoubleSupplier, Command> f){
        return whilePressed(f.apply(this));
    }
    public CommandAxis schedule(Function<Double, Command> f){
        return schedule(f.apply(this.getAsDouble()));
    }

    @Override
    public CommandAxis setInverted(boolean invert) {
        return (CommandAxis) super.setInverted(invert);
    }

}
