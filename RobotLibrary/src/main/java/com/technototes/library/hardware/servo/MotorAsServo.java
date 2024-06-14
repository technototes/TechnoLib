package com.technototes.library.hardware.servo;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.hardware.HardwareDevice;
import com.technototes.library.hardware.Sensored;
import java.io.InvalidClassException;

/**
 * This is to use a motor as a servo using the built-in capabilities (instead of doing it manually)
 *
 * One rather important feature would be to hand control back & forth between a normal encoded
 * motor. The alternative would be to extend EncodedMotor to just "do" this stuff automatically.
 * Honestly, that probably makes more sense, but requires some thought about state transitions.
 *
 * @param <T>
 * Not Yet Implemented!
 * TODO: Implement this
 *
 */
public class MotorAsServo<T extends DcMotor> extends HardwareDevice<T> implements Sensored {

    public MotorAsServo(T device, String deviceName) throws InvalidClassException {
        super(device, deviceName);
        throw new InvalidClassException("com.technototes.library.hardware.servo.MotorAsServo", "Not Yet Implemented");
    }

    protected MotorAsServo(String deviceName) throws InvalidClassException {
        super(deviceName);
        throw new InvalidClassException("com.technototes.library.hardware.servo.MotorAsServo", "Not Yet Implemented");
    }

    /**
     * @return
     */
    @Override
    public String LogLine() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public double getSensorValue() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public double getAsDouble() {
        return getSensorValue();
    }
}
