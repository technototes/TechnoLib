package com.technototes.library.subsystem.motor;

import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.subsystem.DeviceSubsystem;

/** Class for motor subsystems
 * @author Alex Stedman
 * @param <T> The motor type
 */
public class MotorSubsystem<T extends Motor<?>> extends DeviceSubsystem<T> {
    /** Create motor subsystem
     *
     * @param motor The motor
     */
    public MotorSubsystem(T motor) {
        super(motor);
    }

    /** Get the speed of the motors in the subsystem
     *
     * @return The speed
     */
    public double getSpeed() {
        return getDevice().getSpeed();
    }

    /** Set the speed of the primary motors in subsystem
     *
     * @param speed The speed
     */
    public void setSpeed(double speed) {
        getDevice().setSpeed(speed);
    }
    public void stop(){
        setSpeed(0);
    }
}
