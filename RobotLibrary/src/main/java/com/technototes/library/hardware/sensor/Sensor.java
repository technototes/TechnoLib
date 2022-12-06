package com.technototes.library.hardware.sensor;

import com.technototes.library.hardware.HardwareDevice;

/**
 * Root class for sensors
 *
 * @param <T> The Sensor hardware device
 * @author Alex Stedman
 */
@Deprecated
@SuppressWarnings("unused")
public abstract class Sensor<T extends com.qualcomm.robotcore.hardware.HardwareDevice>
    extends HardwareDevice<T> {

    /**
     * Create a sensor
     *
     * @param device The device
     */
    public Sensor(T device) {
        super(device);
    }

    /**
     * Create sensor
     *
     * @param deviceName The device name in hardware map
     */
    public Sensor(String deviceName) {
        super(deviceName);
    }
}
