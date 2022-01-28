package com.technototes.library.control;

import com.technototes.library.general.Enablable;
import com.technototes.library.general.Invertable;
import com.technototes.library.general.Periodic;

import java.util.function.BooleanSupplier;

/** The class to extend custom gamepad buttons from
 * @author Alex Stedman
 */
public class ButtonBase implements BooleanSupplier, Periodic, Invertable<ButtonBase>, Enablable<ButtonBase> {

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
    public ButtonBase(BooleanSupplier b){
        booleanSupplier = b;
    }



    @Override
    public void periodic(){
        periodic(getAsBoolean());
    }

    private void periodic(boolean currentState){
        if(isDisabled()) {
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
    public boolean isJustPressed(){
        return pressed && recentAction;
    }
    /** Returns if the button is just released
     *
     * @return The above condition
     */
    public boolean isJustReleased(){
        return !pressed && recentAction;
    }
    /** Returns if the button is pressed
     *
     * @return The above condition
     */
    public boolean isPressed(){
        return pressed;
    }
    /** Returns if the button is released
     *
     * @return The above condition
     */
    public boolean isReleased(){
        return !pressed;
    }
    /** Returns if the button is just toggled
     *
     * @return The above condition
     */
    public boolean isJustToggled(){
        return toggle && recentAction && pressed;
    }
    /** Returns if the button is just untoggled
     *
     * @return The above condition
     */
    public boolean isJustInverseToggled(){
        return !toggle && recentAction && pressed;
    }
    /** Returns if the button is toggled
     *
     * @return The above condition
     */
    public boolean isToggled(){
        return toggle;
    }
    /** Returns if the button is untoggled
     *
     * @return The above condition
     */
    public boolean isInverseToggled(){
        return !toggle;
    }

    /** Same as isPressed()
     * @return The above condition
     */
    @Override
    public boolean getAsBoolean() {
        return booleanSupplier.getAsBoolean()^inverted&&isEnabled();
    }

    @Override
    public ButtonBase setInverted(boolean invert) {
        inverted = invert;
        return this;
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }


    @Override
    public ButtonBase setEnabled(boolean enable) {
        enabled = enable;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
