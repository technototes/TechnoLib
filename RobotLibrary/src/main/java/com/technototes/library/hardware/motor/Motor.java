package com.technototes.library.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.general.Invertable;
import com.technototes.library.hardware.HardwareDevice;
import java.util.function.Supplier;

/**
 * Class for motors
 *
 * @param <T> The qualcomm hardware device interface
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class Motor<T extends DcMotorSimple>
    extends HardwareDevice<T>
    implements Invertable<Motor<T>>, Supplier<Double> {

    private boolean invert = false;
    private double min = -1, max = 1;

    /**
     * Create a motor
     *
     * @param device The hardware device
     */
    public Motor(T device) {
        super(device);
    }

    /**
     * Create a motor
     *
     * @param deviceName The device name
     */
    public Motor(String deviceName) {
        super(deviceName);
    }

    /**
     * Sets the min &amp; max values for the motor power (still clipped to -1/1)
     *
     * @param mi The minimum value
     * @param ma The maximum value
     * @return The Motor (for chaining)
     */
    public Motor<T> setLimits(double mi, double ma) {
        min = Range.clip(mi, -1, 1);
        max = Range.clip(ma, -1, 1);
        return this;
    }

    /**
     * Returns whether the motor is inverted. WARNING: THIS RETURNS TRUE FOR A "FORWARD" SETTING!
     *
     * @return True for inverted (forward) false for not inverted (reverse)
     */
    @Override
    public boolean getInverted() {
        return invert;
    }

    /**
     * Set the Inverted state for the motor. WARNING: THIS IS BACKWARD TO WHAT YOU MIGHT THINK!
     * True - Motor goes *forward*. False - motor goes *reverse*.
     *
     * @param inv true for forward, false for reverse (probably not what you were expecting)
     * @return The motor (for chaining)
     */
    @Override
    public Motor<T> setInverted(boolean inv) {
        getDevice()
            .setDirection(inv ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        invert = inv;
        return this;
    }

    /**
     * Invert the motor (toggle inversion)
     *
     * @return The motor (for chaining)
     */
    @Override
    public Motor<T> invert() {
        return setInverted(!getInverted());
    }

    /**
     * Gets the power value for the motor
     *
     * @return the power value (as a double)
     */
    public double getSpeed() {
        return device.getPower();
    }

    /**
     * Set speed of motor
     *
     * @param speed The speed of the motor
     */
    public void setSpeed(double speed) {
        device.setPower(Range.clip(speed, min, max));
    }

    /**
     * Configure the motor to *brake* when the power is set to zero.
     *
     * @return The Motor device (for chaining)
     */
    public Motor<T> brake() {
        if (getDevice() instanceof DcMotor) ((DcMotor) getDevice()).setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE
            );
        return this;
    }

    /**
     * Configure the motor to *float* when the power is set to zero.
     *
     * @return The Motor device (for chaining)
     */
    public Motor<T> coast() {
        if (getDevice() instanceof DcMotor) ((DcMotor) getDevice()).setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.FLOAT
            );
        return this;
    }

    /**
     * Gets the *speed* of the motor when it's used as a DoubleSupplier
     *
     * @return The speed of the motor
     */
    @Override
    public Double get() {
        return getSpeed();
    }
}
