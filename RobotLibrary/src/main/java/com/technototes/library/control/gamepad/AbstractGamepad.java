package com.technototes.library.control.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.technototes.library.control.Periodic;

import java.lang.reflect.InvocationTargetException;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

/** A class to extend gamepads from, it does the internal processing for you.
 * @author Alex Stedman
 * @param <T> The class for the button components on the gamepad
 * @param <U> The class for the axis components on the gamepad
 */
public abstract class AbstractGamepad<T extends GamepadButton, U extends GamepadAxis> implements Periodic {
    //normal gamepad
    private Gamepad gamepad;
    //buttons
    /** The button objects
     *
     */
    public T a, b, x, y, start, back, leftBumper, rightBumper,
            cross, circle, square, triangle, share, options,
            dpadUp, dpadDown, dpadLeft, dpadRight, leftStickButton, rightStickButton;
    //axis
    /** The axis objects
     *
     */
    public U leftTrigger, rightTrigger, leftStickX, leftStickY, rightStickX, rightStickY;
    //sticks
    /** The stick objects
     *
     */
    public GamepadStick<U, T> leftStick, rightStick;
    //dpad
    /** The dpad object
     *
     */
    public GamepadDpad<T> dpad;
    //periodics to run
    private Periodic[] periodics;

    private Class<T> buttonClass;
    private Class<U> axisClass;

    /** Creates a gamepad with these parameters
     *
     * @param g The gamepad to base around
     * @param bClass The class object passed for the button components (Should be the same as the one used for the class parameter T)
     * @param aClass The class object passed for the axis components (Should be the same as the one used for the class parameter U)
     */
    public AbstractGamepad(Gamepad g, Class<T> bClass, Class<U> aClass){
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
        periodics = new Periodic[]{a, b, x, y, start, back, leftBumper, rightBumper,
                leftTrigger, rightTrigger, leftStick, rightStick, dpad};
    }

    //to actually instantiate the objects
    private void setComponents(Gamepad g) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        //buttons
        //a=new T();

        a = buttonInstance(()->g.a);
        b = buttonInstance(()->g.b);
        x = buttonInstance(()->g.x);
        y = buttonInstance(()->g.y);
        cross = a;
        circle = b;
        square = x;
        triangle = y;

        start = buttonInstance(()->g.start);
        back = buttonInstance(()->g.back);
        share = back;
        options = start;

        //bumpers
        leftBumper = buttonInstance(()->g.left_bumper);
        rightBumper = buttonInstance(()->g.right_bumper);

        //dpad
        dpadUp = buttonInstance(() -> g.dpad_up);
        dpadDown = buttonInstance(() -> g.dpad_down);
        dpadLeft = buttonInstance(() -> g.dpad_left);
        dpadRight = buttonInstance(() -> g.dpad_right);

        //left stick
        leftStickX = axisInstance(() -> g.left_stick_x);
        leftStickY = axisInstance(() -> g.left_stick_y);
        leftStickButton = buttonInstance(() -> g.left_stick_button);

        //right stick
        rightStickX = axisInstance(() -> g.right_stick_x);
        rightStickY = axisInstance(() -> g.right_stick_y);
        rightStickButton = buttonInstance(() -> g.right_stick_button);

        //triggers
        leftTrigger = axisInstance(() -> g.left_trigger);
        rightTrigger = axisInstance(() -> g.right_trigger);

    }

    //enums

    /** Button enum for all buttons on gamepad
     *
     */
    public enum Button{
        A, B, X, Y, CROSS, CIRCLE, SQUARE, TRIANGLE, SHARE, OPTIONS, START, BACK, LEFT_BUMPER, RIGHT_BUMPER, LEFT_STICK_BUTTON, RIGHT_STICK_BUTTON
    }
    /** Axis enum for all axis on gamepad
     *
     */
    public enum Axis{
        LEFT_STICK_X, LEFT_STICK_Y, RIGHT_STICK_X, RIGHT_STICK_Y, LEFT_TRIGGER, RIGHT_TRIGGER
    }

    /** Returns a button
     *
     * @param bu The enum to choose which gamepad button to return
     * @return The gamepad button that you choose with the enum
     */
    public T getButton(Button bu){
        switch (bu){
            case A:
                return a;
            case B:
                return b;
            case X:
                return x;
            case Y:
                return y;
            case CROSS:
                return cross;
            case CIRCLE:
                return circle;
            case SQUARE:
                return square;
            case TRIANGLE:
                return triangle;
            case SHARE:
                return share;
            case OPTIONS:
                return options;
            case BACK:
                return back;
            case START:
                return start;
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

    /** Returns an axis
     *
     * @param as The enum for the axis that is wanted to be returned
     * @return The chosen axis
     */
    public U getAxis(Axis as){
        switch (as){
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

    /** Returns a button as boolean
     *
     * @param bu The enum to specify which button to get as boolean
     * @return The chosen button as boolean
     */
    public boolean getButtonAsBoolean(Button bu){
        return getButton(bu).getAsBoolean();
    }
    /** Returns an axis as double
     *
     * @param as The enum to specify which axis to get as double
     * @return The chosen axis as double
     */
    public double getAxisAsDouble(Axis as){
        return getAxis(as).getAsDouble();
    }
    /** Returns an axis as boolean
     *
     * @param as The enum to specify which axis to get as boolean
     * @return The chosen axis as boolean
     */
    public boolean getAxisAsBoolean(Axis as){
        return getAxis(as).getAsBoolean();
    }

    /** Returns the left stick
     *
     * @return The left stick on the gamepad
     */
    public GamepadStick<U, T> getLeftStick(){
        return leftStick;
    }
    /** Returns the right stick
     *
     * @return The right stick on the gamepad
     */
    public GamepadStick<U, T> getRightStick(){
        return rightStick;
    }
    /** Returns the Dpad object
     *
     * @return The dpad
     */
    public GamepadDpad<T> getDpad(){
        return dpad;
    }
    @Override
    public void periodic() {
        for(Periodic p : periodics){
            p.periodic();
        }
    }

    /** Returns the encapsulated gamepad
     *
     * @return The gamepad
     */
    public Gamepad getGamepad(){
        return gamepad;
    }

    public T buttonInstance(BooleanSupplier b) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return buttonClass.getConstructor(BooleanSupplier.class).newInstance(b);
    }
    public U axisInstance(DoubleSupplier d) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return axisClass.getConstructor(DoubleSupplier.class).newInstance(d);
    }
}
