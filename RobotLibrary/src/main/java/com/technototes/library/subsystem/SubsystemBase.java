package com.technototes.library.subsystem;

import com.technototes.library.hardware.HardwareDevice;

/** Root class for subsystems
 * @author Alex Stedman
 * @param <T> The {@link HardwareDevice} for this subsystem
 */
public abstract class SubsystemBase<T extends HardwareDevice<?>> implements Subsystem<T>{
    protected T device;

    /** Create a subsystem
     *
     * @param device The main device for the subsystem
     */
    public SubsystemBase(T device) {
        this.device = device;
    }

    /** Get the devices for this subsystem
     *
     * @return The devices
     */
    @Override
    public T getDevice() {
        return device;
    }

    @Override
    public void periodic(){

    }
}
