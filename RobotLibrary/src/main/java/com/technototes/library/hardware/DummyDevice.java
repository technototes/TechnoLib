package com.technototes.library.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

/**
 * This isn't worth actually doing. You have to redo your configuration constantly if you create
 * a device that isn't part of the core runtime :(
 *
 * @param <T> The type of the device
 */
public class DummyDevice<T> implements HardwareDevice {

    private final T internal;

    public DummyDevice(T inter) {
        internal = inter;
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.Unknown;
    }

    @Override
    public String getDeviceName() {
        return internal.toString();
    }

    @Override
    public String getConnectionInfo() {
        return "dummy device";
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {}

    @Override
    public void close() {}

    public T get() {
        return internal;
    }
}
