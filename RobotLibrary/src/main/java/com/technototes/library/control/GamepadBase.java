package com.technototes.library.control;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.technototes.library.general.CanBeEnabled;
import com.technototes.library.general.Periodic;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

/**
 * A class to extend gamepads from, it does the internal processing for you.
 *
 * @param <T> The class for the button components on the gamepad
 * @param <U> The class for the axis components on the gamepad
 * @author Alex Stedman
 */
public class GamepadBase<T extends ButtonBase, U extends AxisBase>
    implements Periodic, CanBeEnabled<GamepadBase<T, U>> {

    private boolean enabled = true;
    // normal gamepad
    private Gamepad gamepad;
    /**
     * The button objects for the XBox game controller
     */
    public T xbox_a, xbox_b, xbox_x, xbox_y, xbox_start, xbox_back;
    /**
     * The button objects for the PS4 game controller
     */
    public T ps_cross, ps_circle, ps_square, ps_triangle, ps_share, ps_options;
    /**
     * The button objects for both the XBox and PS4 controllers
     */
    public T leftBumper, rightBumper, dpadUp, dpadDown, dpadLeft, dpadRight, leftStickButton, rightStickButton;
    /**
     * The axis objects
     */
    public U leftTrigger, rightTrigger, leftStickX, leftStickY, rightStickX, rightStickY;
    /**
     * The stick objects
     */
    public GamepadStick<U, T> leftStick, rightStick;
    /**
     * The dpad object
     */
    public GamepadDpad<T> dpad;

    // periodics to run
    private Periodic[] periodics;
    private CanBeEnabled<?>[] enablables;

    private Class<T> buttonClass;
    private Class<U> axisClass;

    /**
     * Creates a gamepad with these parameters
     *
     * @param g      The gamepad to base around
     * @param bClass The class object passed for the button components (Should be the same as the one used for the class parameter T)
     * @param aClass The class object passed for the axis components (Should be the same as the one used for the class parameter U)
     */
    public GamepadBase(Gamepad g, Class<T> bClass, Class<U> aClass) {
        gamepad = g;
        buttonClass = bClass;
        axisClass = aClass;
        try {
            setComponents(g);
        } catch (Exception e) {
            e.printStackTrace();
        }

        leftStick = new GamepadStick<>(leftStickX, leftStickY, leftStickButton);
        rightStick = new GamepadStick<>(rightStickX, rightStickY, rightStickButton);
        dpad = new GamepadDpad<>(dpadUp, dpadDown, dpadLeft, dpadRight);
        periodics = new Periodic[] {
            xbox_a,
            xbox_b,
            xbox_x,
            xbox_y,
            xbox_start,
            xbox_back,
            leftBumper,
            rightBumper,
            leftTrigger,
            rightTrigger,
            leftStick,
            rightStick,
            dpad,
        };
        enablables = new CanBeEnabled[] {
            xbox_a,
            xbox_b,
            xbox_x,
            xbox_y,
            xbox_start,
            xbox_back,
            leftBumper,
            rightBumper,
            leftTrigger,
            rightTrigger,
            leftStick,
            rightStick,
            dpad,
        };
    }

    // to actually instantiate the objects
    private void setComponents(Gamepad g)
        throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // buttons
        // a=new T();
        xbox_a = buttonInstance(() -> g.a);
        xbox_b = buttonInstance(() -> g.b);
        xbox_x = buttonInstance(() -> g.x);
        xbox_y = buttonInstance(() -> g.y);
        ps_cross = xbox_a;
        ps_circle = xbox_b;
        ps_square = xbox_x;
        ps_triangle = xbox_y;

        xbox_start = buttonInstance(() -> g.start);
        xbox_back = buttonInstance(() -> g.back);
        ps_share = xbox_back;
        ps_options = xbox_start;

        // bumpers
        leftBumper = buttonInstance(() -> g.left_bumper);
        rightBumper = buttonInstance(() -> g.right_bumper);

        // dpad
        dpadUp = buttonInstance(() -> g.dpad_up);
        dpadDown = buttonInstance(() -> g.dpad_down);
        dpadLeft = buttonInstance(() -> g.dpad_left);
        dpadRight = buttonInstance(() -> g.dpad_right);

        // left stick
        leftStickX = axisInstance(() -> g.left_stick_x);
        leftStickY = axisInstance(() -> g.left_stick_y);
        leftStickButton = buttonInstance(() -> g.left_stick_button);

        // right stick
        rightStickX = axisInstance(() -> g.right_stick_x);
        rightStickY = axisInstance(() -> g.right_stick_y);
        rightStickButton = buttonInstance(() -> g.right_stick_button);

        // triggers
        leftTrigger = axisInstance(() -> g.left_trigger);
        rightTrigger = axisInstance(() -> g.right_trigger);
    }

    // enums

    /**
     * Button enum for all buttons on gamepad
     */
    public enum Button {
        /**
         * XBox A button
         */
        XBOX_A,
        /**
         * XBox B button
         */
        XBOX_B,
        /**
         * XBox X button
         */
        XBOX_X,
        /**
         * XBox Y button
         */
        XBOX_Y,
        /**
         * PS4 Cross (X) button
         */
        PS_CROSS,
        /**
         * PS4 Circle (O) button
         */
        PS_CIRCLE,
        /**
         * PS4 Square button
         */
        PS_SQUARE,
        /**
         * PS4 Triangle button
         */
        PS_TRIANGLE,
        /**
         * PS4 Share button
         */
        PS_SHARE,
        /**
         * PS4 Options button
         */
        PS_OPTIONS,
        /**
         * XBox Start button
         */
        XBOX_START,
        /**
         * XBox Back button
         */
        XBOX_BACK,
        /**
         * Left bumper button
         */
        LEFT_BUMPER,
        /**
         * Right bumper button
         */
        RIGHT_BUMPER,
        /**
         * Button when clicking the left stick
         */
        LEFT_STICK_BUTTON,
        /**
         * Button which clicking the right stick
         */
        RIGHT_STICK_BUTTON,
    }

    /**
     * Axis enum for all axis on gamepad
     */
    public enum Axis {
        /**
         * Left stick's horizontal axis
         */
        LEFT_STICK_X,
        /**
         * Left stick's vertical axis
         */
        LEFT_STICK_Y,
        /**
         * Right stick's horizontal axis
         */
        RIGHT_STICK_X,
        /**
         * Right stick's vertical axis
         */
        RIGHT_STICK_Y,
        /**
         * Left Trigger's axis
         */
        LEFT_TRIGGER,
        /**
         * Right Trigger's axis
         */
        RIGHT_TRIGGER,
    }

    /**
     * Returns a button
     *
     * @param bu The enum to choose which gamepad button to return
     * @return The gamepad button that you choose with the enum
     */
    public T getButton(Button bu) {
        switch (bu) {
            case XBOX_A:
                return xbox_a;
            case XBOX_B:
                return xbox_b;
            case XBOX_X:
                return xbox_x;
            case XBOX_Y:
                return xbox_y;
            case PS_CROSS:
                return ps_cross;
            case PS_CIRCLE:
                return ps_circle;
            case PS_SQUARE:
                return ps_square;
            case PS_TRIANGLE:
                return ps_triangle;
            case PS_SHARE:
                return ps_share;
            case PS_OPTIONS:
                return ps_options;
            case XBOX_BACK:
                return xbox_back;
            case XBOX_START:
                return xbox_start;
            case LEFT_BUMPER:
                return leftBumper;
            case RIGHT_BUMPER:
                return rightBumper;
            case LEFT_STICK_BUTTON:
                return leftStickButton;
            case RIGHT_STICK_BUTTON:
                return rightStickButton;
            default:
                return null;
        }
    }

    /**
     * Returns an axis
     *
     * @param as The enum for the axis that is wanted to be returned
     * @return The chosen axis
     */
    public U getAxis(Axis as) {
        switch (as) {
            case LEFT_STICK_X:
                return leftStickX;
            case LEFT_STICK_Y:
                return leftStickY;
            case RIGHT_STICK_X:
                return rightStickX;
            case RIGHT_STICK_Y:
                return rightStickY;
            case LEFT_TRIGGER:
                return leftTrigger;
            case RIGHT_TRIGGER:
                return rightTrigger;
            default:
                return null;
        }
    }

    /**
     * Returns a button as boolean (same as isPressed)
     *
     * @param bu The enum to specify which button to get as boolean
     * @return The chosen button as boolean
     */
    public boolean getButtonAsBoolean(Button bu) {
        return getButton(bu).getAsBoolean();
    }

    /**
     * Returns an axis as double
     *
     * @param as The enum to specify which axis to get as double
     * @return The chosen axis as double
     */
    public double getAxisAsDouble(Axis as) {
        return getAxis(as).getAsDouble();
    }

    /**
     * Returns an axis as boolean
     *
     * @param as The enum to specify which axis to get as boolean
     * @return The chosen axis as boolean
     */
    public boolean getAxisAsBoolean(Axis as) {
        return getAxis(as).getAsBoolean();
    }

    /**
     * Returns the left stick
     *
     * @return The left stick on the gamepad
     */
    public GamepadStick<U, T> getLeftStick() {
        return leftStick;
    }

    /**
     * Returns the right stick
     *
     * @return The right stick on the gamepad
     */
    public GamepadStick<U, T> getRightStick() {
        return rightStick;
    }

    /**
     * Returns the Dpad object
     *
     * @return The dpad
     */
    public GamepadDpad<T> getDpad() {
        return dpad;
    }

    /**
     * Run the periodic functions for the controller (if the controller is enabled)
     */
    @Override
    public void periodic() {
        if (isDisabled()) {
            return;
        }
        for (Periodic p : periodics) {
            p.periodic();
        }
    }

    /**
     * Returns the encapsulated gamepad
     *
     * @return The gamepad
     */
    public Gamepad getGamepad() {
        return gamepad;
    }

    /**
     * Great a ButtonBase type from the given BooleanSupplier
     *
     * @param b the function to wrap in a ButtonBase
     * @return the T (extends ButtonBase) which the ButtonBase will wrap
     * @throws InstantiationException    Couldn't create the target type
     * @throws IllegalAccessException    Couldn't access the constructor
     * @throws NoSuchMethodException     Couldn't find a constructor with a BooleanSupplier parameter
     * @throws InvocationTargetException Couldn't invoke the constructor for other reasons
     */
    public T buttonInstance(BooleanSupplier b)
        throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return buttonClass.getConstructor(BooleanSupplier.class).newInstance(b);
    }

    /**
     * Returns the U (extended from AxisBase) type wrapped around a simple DoubleSupplier
     *
     * @param d The function to call
     * @return A U (of AxisBase type) that's wrapped around the DoubleSupplier d provided
     * @throws InstantiationException    Couldn't create the target type
     * @throws IllegalAccessException    Couldn't access the constructor
     * @throws NoSuchMethodException     Couldn't find a constructor with a BooleanSupplier parameter
     * @throws InvocationTargetException Couldn't invoke the constructor for other reasons
     */
    public U axisInstance(DoubleSupplier d)
        throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return axisClass.getConstructor(DoubleSupplier.class).newInstance(d);
    }

    // rumble stuff

    /**
     * Run a bunch of "RumbleBlips"
     *
     * @param blips The number of blips
     */
    public void rumbleBlips(int blips) {
        gamepad.rumbleBlips(blips);
    }

    /**
     * Run a single "RumbleBlip"
     */
    public void rumbleBlip() {
        rumbleBlips(1);
    }

    /**
     * Rumble the gamepad for 'seconds'
     *
     * @param seconds The number of (fractional) seconds to rumble for
     */
    public void rumble(double seconds) {
        gamepad.rumble((int) (seconds * 1000));
    }

    /**
     * Is the gamepad rumbling
     *
     * @return true if the gamepad is rumbling
     */
    public boolean isRumbling() {
        return gamepad.isRumbling();
    }

    /**
     * Stop rumbling on the gamepad
     */
    public void stopRumble() {
        gamepad.stopRumble();
    }

    /**
     * Rumble for about 1/8th of a second
     */
    public void rumble() {
        rumble(120);
    }

    /**
     * Enable/disable the gamepad (and all potentially bound commands!)
     *
     * @param enable True for enabled, false for disabled
     * @return the Gamepad (for chaining)
     */
    @Override
    public GamepadBase<T, U> setEnabled(boolean enable) {
        enabled = enable;
        for (CanBeEnabled<?> enablable : enablables) enablable.setEnabled(enable);
        return this;
    }

    /**
     * Is the gamepad (and all bound commands from it!) enabled?
     *
     * @return true if it's enabled, false if it's disabled
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
