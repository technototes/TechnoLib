package com.technototes.library.control;

import com.technototes.library.structure.Invertable;
import com.technototes.library.structure.Periodic;

import java.util.function.BooleanSupplier;

/** The class to extend custom gamepad buttons from
 * @author Alex Stedman
 */
public class GamepadButton implements BooleanSupplier, Periodic, Invertable<GamepadButton> {
    protected BooleanSupplier booleanSupplier;

    private boolean pressed = false;
    private boolean toggle = false;
    private boolean recentAction = false;
    private boolean pastState = false;
    private boolean inverted = false;

    /** Create button with boolean supplier
     *
     * @param b The supplier
     */
    public GamepadButton(BooleanSupplier b){
        booleanSupplier = b;
    }



    @Override
    public void periodic(){
        periodic(getAsBoolean());
    }

    private void periodic(boolean currentState){
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
        return booleanSupplier.getAsBoolean()^inverted;
    }

    @Override
    public GamepadButton setInverted(boolean invert) {
        inverted = invert;
        return this;
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }
}
