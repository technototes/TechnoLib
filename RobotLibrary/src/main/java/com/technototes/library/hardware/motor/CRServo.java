package com.technototes.library.hardware.motor;

import com.technototes.library.hardware.HardwareDevice;
import java.io.InvalidClassException;
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

    public CRServo(com.qualcomm.robotcore.hardware.CRServo device, String deviceName) throws InvalidClassException {
        super(device, deviceName);
        throw new InvalidClassException("com.technototes.library.hardware.motor.CRServo", "Not Yet Implemented");
    }

    protected CRServo(String deviceName) throws InvalidClassException {
        super(deviceName);
        throw new InvalidClassException("com.technototes.library.hardware.motor.CRServo", "Not Yet Implemented");
    }

    @Override
    public String LogLine() {
        return null;
    }

    @Override
    public Double get() {
        return null;
    }
}
