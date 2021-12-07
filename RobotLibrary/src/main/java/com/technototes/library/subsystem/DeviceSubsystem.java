package com.technototes.library.subsystem;

import com.technototes.library.hardware.HardwareDevice;

/** class for subsystems
 * @author Alex Stedman
 * @param <T> The {@link HardwareDevice} for this subsystem
 */
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
    public void periodic(){

    }
}
