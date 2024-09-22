package com.technototes.library.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.HardwareDevice;
import java.util.function.Supplier;

/**
 * This is for "continuous rotation" servos. They work, unfortunately, like motors
 * without encoders: You can set a power (from 0 to 1, typically, with .5 as "stop") and the
 * servo will spin in the direct, at the provided speed...
 *
 * Not Yet Implemented!
 * TODO: Implement this
 */
public class CRServo extends HardwareDevice<com.qualcomm.robotcore.hardware.CRServo> implements Supplier<Double> {

    private double min = -1, max = 1;
    protected double power;
    protected DcMotorSimple.Direction dir;

    private String dirStr() {
        return dir == DcMotorSimple.Direction.FORWARD ? "Fwd" : "Rev";
    }

    /**
     * Create a motor
     *
     * @param device The hardware device
     */
    public CRServo(com.qualcomm.robotcore.hardware.CRServo device, String nm) {
        super(device, nm);
        power = 0;
        dir = DcMotorSimple.Direction.FORWARD;
    }

    /**
     * Create a motor
     *
     * @param deviceName The device name
     */
    public CRServo(String deviceName) {
        super(deviceName);
        power = 0;
        dir = DcMotorSimple.Direction.FORWARD;
    }

    @Override
    public String LogLine() {
        if (min <= -1.0 && max >= 1.0) {
            return logData(String.format("%1.2f%s", power, dirStr()));
        } else {
            return logData(String.format("%1.2f%s[%1.2f-%1.2f]", power, dirStr(), min, max));
        }
    }

    /**
     * Sets the min &amp; max values for the motor power (still clipped to -1/1)
     *
     * @param mi The minimum value
     * @param ma The maximum value
     * @return The Motor (for chaining)
     */
    public CRServo setLimits(double mi, double ma) {
        mi = Range.clip(mi, -1, 1);
        ma = Range.clip(ma, -1, 1);
        min = Math.min(mi, ma);
        max = Math.max(mi, ma);
        setPower(power);
        return this;
    }

    /**
     * Returns the DcMotorSimple.Direction the motor is traveling
     */
    public DcMotorSimple.Direction getDirection() {
        return dir;
    }

    /**
     * Set the motor to go *backward*
     */
    public CRServo setBackward() {
        return setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * Set the motor to go *forward*
     */
    public CRServo setForward() {
        return setDirection(DcMotorSimple.Direction.FORWARD);
    }

    /**
     * Set the motor to go in a particular direction
     */
    public CRServo setDirection(DcMotorSimple.Direction dir) {
        if (this.dir != dir) {
            this.dir = dir;
            com.qualcomm.robotcore.hardware.CRServo device = getRawDevice();
            if (device != null) {
                device.setDirection(dir);
            }
        }
        return this;
    }

    /**
     * Gets the power value for the motor
     *
     * @return the power value (as a double)
     */
    public double getPower() {
        com.qualcomm.robotcore.hardware.CRServo device = getRawDevice();
        if (device != null) {
            power = device.getPower();
        }
        return power;
    }

    /**
     * Set the (range-clipped) power of the motor
     *
     * @param pow The power value (-1 -> 1)
     */
    public void setPower(double pow) {
        power = Range.clip(pow, min, max);
        com.qualcomm.robotcore.hardware.CRServo device = getRawDevice();
        if (device != null) {
            device.setPower(power);
        }
    }

    /**
     * Gets the *speed* of the motor when it's used as a DoubleSupplier
     *
     * @return The speed of the motor
     */
    public Double get() {
        return getPower();
    }
}
