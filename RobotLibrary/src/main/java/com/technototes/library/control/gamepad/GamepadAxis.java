package com.technototes.library.control.gamepad;

import com.technototes.library.control.Periodic;

import java.util.function.DoubleSupplier;

/** The class to extend custom gamepad axis from
 * @author Alex Stedman
 */
public class GamepadAxis extends GamepadButton implements DoubleSupplier, Periodic {
    /** The default trigger threshold
     *
     */
    public static final double DEFAULT_TRIGGER_THRESHOLD = 0.1;
    private double triggerThreshold;
    protected DoubleSupplier doubleSupplier;

    /** Make a GamepadAxis with the supplier
     *
     * @param d The supplier to make the axis around
     */
    public GamepadAxis(DoubleSupplier d){
        this(d, DEFAULT_TRIGGER_THRESHOLD);
    }
    /** Make a GamepadAxis with the supplier and the threshold for the stick to behave as a button
     *
     * @param d The supplier to make the axis around
     * @param t The threshold
     */
    public GamepadAxis(DoubleSupplier d, double t){
        super(() -> Math.abs(d.getAsDouble())>=t);
        doubleSupplier = d;
        triggerThreshold = t;
    }


    /** Returns the double from the axis
     *
     * @return The double
     */
    @Override
    public double getAsDouble() {
        return doubleSupplier.getAsDouble();
    }

    /** Gets the trigger threshold
     *
     * @return The threshold
     */
    public double getTriggerThreshold() {
        return triggerThreshold;
    }

    /** Set threshold
     * @param threshold the new threshold
     */
    public GamepadAxis setTriggerThreshold(double threshold){
        triggerThreshold = threshold;
        return this;
    }
}
