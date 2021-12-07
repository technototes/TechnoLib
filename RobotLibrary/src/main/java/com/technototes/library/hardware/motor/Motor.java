package com.technototes.library.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.HardwareDevice;
import com.technototes.library.structure.Invertable;

import java.util.function.Supplier;

/** Class for motors
 * @author Alex Stedman
 * @param <T> The qualcomm hardware device interface
 */
@SuppressWarnings("unused")
public class Motor<T extends DcMotorSimple> extends HardwareDevice<T> implements Invertable<Motor<T>>, Supplier<Double> {
    private boolean invert = false;
    private double min = -1, max = 1;
    /** Create a motor
     *
     * @param device The hardware device
     */
    public Motor(T device) {
        super(device);
    }

    /** Create a motor
     *
     * @param deviceName The device name
     */
    public Motor(String deviceName) {
        super(deviceName);
    }

    public Motor<T> setOutputLimits(double mi, double ma){
        min = Range.clip(mi, -1, 1);
        max = Range.clip(ma, -1, 1);
        return this;
    }

    @Override
    public boolean getInverted() {
        return invert;
    }

    @Override
    public Motor<T> setInverted(boolean inv) {
        getDevice().setDirection(inv ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        invert = inv;
        return this;
    }

    @Override
    public Motor<T> invert() {
        return setInverted(!getInverted());
    }

    public double getSpeed() {
        return device.getPower();
    }

    /** Set speed of motor  
     *
     * @param speed The speed of the motor
     */
    public void setSpeed(double speed) {
        device.setPower(Range.clip(speed, min, max));
    }


    @Override
    public Double get() {
        return getSpeed();
    }
}
