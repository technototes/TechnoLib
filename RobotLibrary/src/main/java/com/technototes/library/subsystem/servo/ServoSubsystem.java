package com.technototes.library.subsystem.servo;

import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.hardware.servo.ServoGroup;
import com.technototes.library.subsystem.DeviceSubsystem;

/**
 * a bad example class for servo subsystems
 *
 * This is an anti-pattern: Don't expose a servo as a subsystem. Expose capabilities of the
 * subsystem, for use by commands
 * @author Alex Stedman
 */
@Deprecated
public class ServoSubsystem extends DeviceSubsystem<Servo> {

    /** Create servo subsystem
     *
     * @param servo The servo
     */
    public ServoSubsystem(Servo servo) {
        super(servo);
    }

    public ServoSubsystem(Servo... servo) {
        super(new ServoGroup(servo));
    }

    /** Set servo subsystem position
     *
     * @param position The position
     */
    public void setPosition(double position) {
        getDevice().setPosition(position);
    }

    /** Get subsystem servo position
     *
     * @return The position
     */
    public double getPosition() {
        return getDevice().getPosition();
    }
}
