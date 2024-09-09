package com.technototes.library.hardware.servo;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.general.Invertible;
import com.technototes.library.hardware.HardwareDevice;
import com.technototes.library.hardware.Sensored;

/**
 * Class for servos
 * There's some useful functionality in here, but it can be added more easily by making something
 * that direction extends the robotcore Servo instead. The HardwareDevice base class just adds
 * a layer of indirection without much value...
 *
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class Servo
    extends HardwareDevice<com.qualcomm.robotcore.hardware.Servo>
    implements Sensored, Invertible<Servo> {

    private boolean inverted = false;
    private double pos = 0.0;

    /**
     * Create servo object
     *
     * @param device The servo
     */
    public Servo(com.qualcomm.robotcore.hardware.Servo device, String nm) {
        super(device, nm);
    }

    /**
     * Create servo object
     *
     * @param deviceName The device name in hardware map
     */
    public Servo(String deviceName) {
        super(deviceName);
    }

    @Override
    public String LogLine() {
        // Could also include the min/max stuff, and "inverted"
        return logData(String.format("%1.3", pos));
    }

    /**
     * Set position for the servo and return this
     *
     * @param position The servo position
     * @return this
     */
    public Servo startAt(double position) {
        setPosition(position);
        return this;
    }

    public Servo scalePWM(double min, double max) {
        com.qualcomm.robotcore.hardware.Servo dev = getRawDevice();
        if (dev instanceof ServoImplEx) {
            ((ServoImplEx) dev).setPwmRange(new PwmControl.PwmRange(min, max));
        }
        return this;
    }

    public Servo expandedRange() {
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

    /**
     * Set servo position
     *
     * @param position The position to set the servo to
     */
    public void setPosition(double position) {
        this.pos = Range.clip(!inverted ? position : 1 - position, 0, 1);
        getRawDevice().setPosition(this.pos);
    }

    public void incrementPosition(double incAmount) {
        setPosition(getPosition() + incAmount);
    }

    @Override
    public double getSensorValue() {
        return inverted ? 1 - this.pos : this.pos;
    }

    /**
     * Get servo position
     *
     * @return The servo position
     */
    public double getPosition() {
        return getSensorValue();
    }

    /**
     * Set servo range
     *
     * @param min The minimum of the range
     * @param max The maximum of the range
     * @return this
     */
    public Servo onRange(double min, double max) {
        getRawDevice().scaleRange(min, max);
        return this;
    }
}
