package com.technototes.library.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Class for hardware devices
 * This just adds an extra layer of indirection that doesn't feel like it's worth the hassle.
 *
 * @param <T> The class for the default device (ones found in ftcsdk)
 * @author Alex Stedman
 */
@Deprecated
@SuppressWarnings("unused")
public abstract class HardwareDevice<T extends com.qualcomm.robotcore.hardware.HardwareDevice> {

    /**
     * Hardware map object for stuff
     */
    public static HardwareMap hardwareMap = null;

    protected T device;

    /**
     * Make a hardware device
     *
     * @param device The default device
     */
    public HardwareDevice(T device) {
        this.device = device;
    }

    /**
     * Make a hardware device with the string to get from hardwaremap
     *
     * @param deviceName The device name
     */
    @SuppressWarnings("unchecked cast")
    protected HardwareDevice(String deviceName) {
        this(
            hardwareMap.get(
                (Class<T>) com.qualcomm.robotcore.hardware.HardwareDevice.class/*T.class*/,
                deviceName
            )
        );
    }

    /**
     * Get encapsulated device
     *
     * @return The device
     */
    public T getDevice() {
        return device;
    }
}
