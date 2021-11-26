package com.technototes.library.subsystem.servo;

import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.hardware.servo.ServoGroup;
import com.technototes.library.subsystem.SubsystemBase;

/** Class for servo subsystems
 * @author Alex Stedman
 */
public class ServoSubsystem extends SubsystemBase<Servo> {
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
    public double getPosition(){
        return getDevice().getPosition();
    }

}
