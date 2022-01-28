package com.technototes.library.control;

import com.technototes.library.general.Periodic;

import java.util.function.DoubleSupplier;

/** The class to extend custom gamepad axis from
 * @author Alex Stedman
 */
public class AxisBase extends ButtonBase implements DoubleSupplier, Periodic {
    /** The default trigger threshold
     *
     */
    public static final double DEFAULT_TRIGGER_THRESHOLD = 0.05;
    private double triggerThreshold;
    protected DoubleSupplier doubleSupplier;

    /** Make a GamepadAxis with the supplier
     *
     * @param d The supplier to make the axis around
     */
    public AxisBase(DoubleSupplier d){
        this(d, DEFAULT_TRIGGER_THRESHOLD);
    }
    /** Make a GamepadAxis with the supplier and the threshold for the stick to behave as a button
     *
     * @param d The supplier to make the axis around
     * @param t The threshold
     */
    public AxisBase(DoubleSupplier d, double t){
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
        if(isDisabled()) return 0;
        return getInverted() ? -doubleSupplier.getAsDouble() : doubleSupplier.getAsDouble();
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
    public AxisBase setTriggerThreshold(double threshold){
        triggerThreshold = threshold;
        return this;
    }
}
