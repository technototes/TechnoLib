package com.technototes.library.subsystem.motor;

import com.technototes.library.hardware.motor.EncodedMotor;

/**
 * A bad example class for encoded motor subsystems
 *
 * This is an anti-pattern.
 * Subsystems are levels of abstraction. This doesn't provide a real level of abstraction.
 * @author Alex Stedman
 */
@Deprecated
public class EncodedMotorSubsystem extends MotorSubsystem<EncodedMotor<?>> {

    /** Max speed
     *
     */
    public double maxSpeed = 0.5;

    /** Create encoded motor subsystem
     *
     * @param motor The motor
     */
    public EncodedMotorSubsystem(EncodedMotor<?> motor) {
        super(motor);
    }

    /** Set the max speed for the subsystem
     *
     * @param speed New max speed
     * @return this
     */
    public EncodedMotorSubsystem setMaxSpeed(double speed) {
        maxSpeed = speed;
        return this;
    }

    /** Set position for subsystem with existing max speed
     *
     * @param ticks Motor ticks for position
     * @return If this is at specified position
     */
    public boolean setPosition(double ticks) {
        return setPosition(ticks, maxSpeed);
    }

    /** Set position for subsystem
     *
     * @param ticks Motor ticks for position
     * @param speed The max speed to run to the position
     * @return If this is at specified position
     */
    public boolean setPosition(double ticks, double speed) {
        return getDevice().setPosition(ticks, speed);
    }
}
