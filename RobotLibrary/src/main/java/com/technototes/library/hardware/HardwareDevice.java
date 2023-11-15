package com.technototes.library.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Class for hardware devices
 * This just adds an extra layer of indirection that doesn't feel like it's worth the hassle.
 *
 * @param <T> The class for the default device (ones found in ftcsdk)
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public abstract class HardwareDevice<T extends com.qualcomm.robotcore.hardware.HardwareDevice> {

    /**
     * Hardware map object for stuff
     */
    public static HardwareMap hardwareMap = null;

    private T device;

    /**
     * The name of the hardware used for logging & hardware creation
     */
    protected String name;

    /**
     * Make a hardware device
     *
     * @param device The default device
     */
    public HardwareDevice(T device, String deviceName) {
        this.device = device;
        name = deviceName;
    }

    /**
     * Make a hardware device with the string to get from hardwaremap
     *
     * @param deviceName The device name
     */
    @SuppressWarnings("unchecked cast")
    protected HardwareDevice(String deviceName) {
        try {
            device =
                hardwareMap.get((Class<T>) com.qualcomm.robotcore.hardware.HardwareDevice.class/*T.class*/, deviceName);
        } catch (Exception e) {
            device = null;
        }
    }

    /**
     * Get encapsulated device
     *
     * @return The device
     */
    public T getDevice() {
        // TODO: Assert that we've got a device, yeah?
        return device;
    }

    /**
     * Get the logging expression
     */
    protected String logData(String info) {
        return String.format("%s: %s", name, info);
    }

    /**
     * This is used for logging stuff by name and/or device type
     * @return
     */
    public abstract String LogLine();
}
