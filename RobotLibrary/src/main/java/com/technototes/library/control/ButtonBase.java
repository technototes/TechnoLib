package com.technototes.library.control;

import com.technototes.library.general.CanBeEnabled;
import com.technototes.library.general.Invertible;
import com.technototes.library.general.Periodic;
import java.util.function.BooleanSupplier;

/** The class to extend custom gamepad buttons from
 * @author Alex Stedman
 */
public class ButtonBase implements BooleanSupplier, Periodic, Invertible<ButtonBase>, CanBeEnabled<ButtonBase> {

    protected BooleanSupplier booleanSupplier;

    private boolean pressed = false;
    private boolean toggle = false;
    private boolean recentAction = false;
    private boolean pastState = false;
    private boolean inverted = false;
    private boolean enabled = true;

    /** Create button with boolean supplier
     *
     * @param b The supplier
     */
    public ButtonBase(BooleanSupplier b) {
        booleanSupplier = b;
    }

    @Override
    public void periodic() {
        periodic(getAsBoolean());
    }

    private void periodic(boolean currentState) {
        if (isDisabled()) {
            recentAction = false;
            pastState = false;
            pressed = false;
            toggle = false;
            return;
        }
        recentAction = pastState != currentState;
        pastState = currentState;
        pressed = currentState;
        toggle = (recentAction && pastState) != toggle;
    }

    /** Returns if the button is just pressed
     *
     * @return The above condition
     */
    public boolean isJustPressed() {
        return pressed && recentAction;
    }

    /** Returns if the button is just released
     *
     * @return The above condition
     */
    public boolean isJustReleased() {
        return !pressed && recentAction;
    }

    /** Returns if the button is pressed
     *
     * @return The above condition
     */
    public boolean isPressed() {
        return pressed;
    }

    /** Returns if the button is released
     *
     * @return The above condition
     */
    public boolean isReleased() {
        return !pressed;
    }

    /** Returns if the button is just toggled
     *
     * @return The above condition
     */
    public boolean isJustToggled() {
        return toggle && recentAction && pressed;
    }

    /** Returns if the button is just untoggled
     *
     * @return The above condition
     */
    public boolean isJustInverseToggled() {
        return !toggle && recentAction && pressed;
    }

    /** Returns if the button is toggled
     *
     * @return The above condition
     */
    public boolean isToggled() {
        return toggle;
    }

    /** Returns if the button is untoggled
     *
     * @return The above condition
     */
    public boolean isInverseToggled() {
        return !toggle;
    }

    /** Same as isPressed()
     * @return The above condition
     */
    @Override
    public boolean getAsBoolean() {
        // Alex had to get some of that sweet, sweet exclusive or operator...
        // For the non-bit-twiddly among us, this is (bs.get() != inverted) && isEnabled()
        // Or, verbally: flip the booleanSupplier if it's inverted, and it's only true if
        // it's also enabled...
        return booleanSupplier.getAsBoolean() ^ inverted && isEnabled();
    }

    /**
     * Inverts the button, such that "isPressed" and "isReleased" are opposite, along with everything
     * that entails
     *
     * @param invert Inversion value (true inverts, false leaves the values as is)
     * @return the ButtonBase object
     */
    @Override
    public ButtonBase setInverted(boolean invert) {
        inverted = invert;
        return this;
    }

    /**
     * @return True if the button is inverted (pressed registers as released, etc...)
     */
    @Override
    public boolean getInverted() {
        return inverted;
    }

    /**
     * Enable or disable the button
     * @param enable True for enabled, false for disabled
     * @return
     */
    @Override
    public ButtonBase setEnabled(boolean enable) {
        enabled = enable;
        return this;
    }

    /**
     * @return True if the button is enabled
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
