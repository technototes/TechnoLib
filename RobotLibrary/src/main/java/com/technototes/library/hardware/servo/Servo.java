package com.technototes.library.hardware.servo;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.*;
import com.technototes.library.general.Invertable;

/** Class for servos
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class Servo extends HardwareDevice<com.qualcomm.robotcore.hardware.Servo> implements Sensored, Invertable<Servo> {

    private boolean inverted = false;
    /** Create servo object
     *
     * @param device The servo
     */
    public Servo(com.qualcomm.robotcore.hardware.Servo device) {
        super(device);
    }

    /** Create servo object
     *
     * @param deviceName The device name in hardware map
     */
    public Servo(String deviceName) {
        super(deviceName);
    }

    /** Set position for the servo and return this
     *
     * @param position The servo position
     * @return this
     */
    public Servo startAt(double position) {
        setPosition(position);
        return this;
    }

    public Servo scalePWM(double min, double max){
        if(getDevice() instanceof ServoImplEx) ((ServoImplEx) getDevice()).setPwmRange(new PwmControl.PwmRange(min, max));
        return this;
    }

    public Servo expandedRange(){
        return scalePWM(500, 2500);
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }

    @Override
    public Servo setInverted(boolean invert) {
        inverted = invert;
        return this;
    }

    /** Set servo position
     *
     * @param position The position to set the servo to
     */
    public void setPosition(double position) {
        device.setPosition(Range.clip(!inverted ? position : 1-position, 0, 1));
    }
    public void incrementPosition(double incAmount){
        setPosition(getPosition()+incAmount);
    }

    @Override
    public double getSensorValue() {
        return inverted ? 1-device.getPosition() : device.getPosition();
    }

    /** Get servo position
     *
     * @return The servo position
     */
    public double getPosition(){
        return getSensorValue();
    }

    /** Set servo range
     *
     * @param min The minimum of the range
     * @param max The maximum of the range
     * @return this
     */
    public Servo onRange(double min, double max) {
        getDevice().scaleRange(min, max);
        return this;
    }




}
