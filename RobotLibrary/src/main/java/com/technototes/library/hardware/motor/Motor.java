package com.technototes.library.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.HardwareDevice;
import java.util.function.Supplier;

/**
 * Class for motors
 *
 * @param <T> The qualcomm hardware device interface
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class Motor<T extends DcMotorSimple> extends HardwareDevice<T> implements Supplier<Double> {

    private double min = -1, max = 1;
    protected double power;
    protected DcMotorSimple.Direction dir;
    protected DcMotor.ZeroPowerBehavior zeroBehavior;

    private String dirStr() {
        return dir == DcMotorSimple.Direction.FORWARD ? "Fwd" : "Rev";
    }

    private String zBehavior() {
        boolean simple = (getDevice() instanceof DcMotor);
        if (simple) {
            return "";
        }
        if (zeroBehavior == DcMotor.ZeroPowerBehavior.BRAKE) {
            return "Brk";
        } else {
            return "Flt";
        }
    }

    /**
     * Create a motor
     *
     * @param device The hardware device
     */
    public Motor(T device, String nm) {
        super(device, nm);
        power = 0;
        dir = DcMotorSimple.Direction.FORWARD;
        zeroBehavior = DcMotor.ZeroPowerBehavior.FLOAT;
    }

    /**
     * Create a motor
     *
     * @param deviceName The device name
     */
    public Motor(String deviceName) {
        super(deviceName);
    }

    @Override
    public String LogLine() {
        if (min <= -1.0 && max >= 1.0) {
            return logData(String.format("%1.2f%s%s", power, dirStr(), zBehavior()));
        } else {
            return logData(String.format("%1.2f%s%s[%1.2f-%1.2f]", power, dirStr(), zBehavior(), min, max));
        }
    }

    /**
     * Sets the min &amp; max values for the motor power (still clipped to -1/1)
     *
     * @param mi The minimum value
     * @param ma The maximum value
     * @return The Motor (for chaining)
     */
    public Motor<T> setLimits(double mi, double ma) {
        mi = Range.clip(mi, -1, 1);
        ma = Range.clip(ma, -1, 1);
        min = Math.min(mi, ma);
        max = Math.max(mi, ma);
        return this;
    }

    /**
     * Returns the DcMotorSimple.Direction the motor is traveling
     */
    public DcMotorSimple.Direction getDirection() {
        dir = getDevice().getDirection();
        return dir;
    }

    /**
     * Set the motor to go *backward*
     */
    public Motor<T> setBackward() {
        getDevice().setDirection(DcMotorSimple.Direction.REVERSE);
        dir = DcMotorSimple.Direction.REVERSE;
        return this;
    }

    /**
     * Set the motor to go *forward*
     */
    public Motor<T> setForward() {
        getDevice().setDirection(DcMotorSimple.Direction.FORWARD);
        dir = DcMotorSimple.Direction.FORWARD;
        return this;
    }

    /**
     * Set the motor to go in a particular direction
     */
    public Motor<T> setDirection(DcMotorSimple.Direction dir) {
        this.dir = dir;
        getDevice().setDirection(dir);
        return this;
    }

    /**
     * Gets the power value for the motor
     *
     * @return the power value (as a double)
     * @deprecated use getPower() instead
     */
    @Deprecated
    public double getSpeed() {
        return getPower();
    }

    /**
     * Gets the power value for the motor
     *
     * @return the power value (as a double)
     */
    public double getPower() {
        return device.getPower();
    }

    /**
     * Set the (range-clipped) speed of motor
     *
     * @param speed The speed of the motor
     */
    @Deprecated
    public void setSpeed(double speed) {
        setPower(speed);
    }

    /**
     * Set the (range-clipped) power of the motor
     *
     * @param pow The power value (-1 -> 1)
     */
    public void setPower(double pow) {
        device.setPower(Range.clip(pow, min, max));
    }

    /**
     * Configure the motor to *brake* when the power is set to zero.
     *
     * @return The Motor device (for chaining)
     */
    public Motor<T> brake() {
        T device = getDevice();
        if (device instanceof DcMotor) {
            ((DcMotor) device).setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            zeroBehavior = DcMotor.ZeroPowerBehavior.BRAKE;
        }
        return this;
    }

    /**
     * Configure the motor to *float* when the power is set to zero.
     *
     * @return The Motor device (for chaining)
     */
    public Motor<T> coast() {
        T device = getDevice();
        if (device instanceof DcMotor) {
            ((DcMotor) device).setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            zeroBehavior = DcMotor.ZeroPowerBehavior.FLOAT;
        }
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
