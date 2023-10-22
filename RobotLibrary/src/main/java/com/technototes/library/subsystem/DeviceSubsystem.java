package com.technototes.library.subsystem;

import com.technototes.library.hardware.HardwareDevice;

/**
 * Don't do this, don't use this. Subsystems should be used for encapsulation, not for exposing
 * the rest of the system to a hardware device
 * @author Alex Stedman
 * @param <T> The {@link HardwareDevice} for this subsystem
 */
@Deprecated
public abstract class DeviceSubsystem<T extends HardwareDevice<?>> implements Subsystem {

    protected T device;

    /** Create a subsystem
     *
     * @param device The main device for the subsystem
     */
    public DeviceSubsystem(T device) {
        this.device = device;
    }

    /** Get the devices for this subsystem
     *
     * @return The devices
     */
    public T getDevice() {
        return device;
    }

    @Override
    public void periodic() {}
}
