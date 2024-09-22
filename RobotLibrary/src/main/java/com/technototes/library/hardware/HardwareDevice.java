package com.technototes.library.hardware;

import android.util.Log;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    protected static Map<String, HardwareDevice<?>> names = new HashMap<>();

    public static void initMap(HardwareMap h) {
        hardwareMap = h;
    }

    public static Set<HardwareDevice<?>> devices = null;

    private T device;

    /**
     * The name of the hardware used for logging & hardware creation
     */
    protected String name;

    public String getName() {
        return name;
    }

    /**
     * Make a hardware device
     *
     * @param device The default device
     */
    public HardwareDevice(T device, String deviceName) {
        this.device = device;
        name = deviceName;
        names.put(deviceName, this);
    }

    /**
     * Make a hardware device with the string to get from hardwaremap
     *
     * @param deviceName The device name
     */
    @SuppressWarnings("unchecked cast")
    protected HardwareDevice(String deviceName) {
        device = hardwareMap.tryGet(
            (Class<T>) com.qualcomm.robotcore.hardware.HardwareDevice.class/*T.class*/,
            deviceName
        );
        name = deviceName;
        names.put(name, this);
        /* if (device == null) {
            Log.e("DEVICE FAILURE", deviceName);
        } */
    }

    /**
     * Get encapsulated device
     *
     * @return The device
     */
    protected T getRawDevice() {
        // TODO: Assert that we've got a device, yeah?
        return device;
    }

    protected boolean realHardware() {
        return device != null;
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
