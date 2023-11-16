package com.technototes.library.hardware;

public class FailedDevice implements com.qualcomm.robotcore.hardware.HardwareDevice {

    protected String name;

    protected FailedDevice(String deviceName) {
        name = deviceName;
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.Other;
    }

    @Override
    public String getDeviceName() {
        return name;
    }

    @Override
    public String getConnectionInfo() {
        return "NC";
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {}

    @Override
    public void close() {}
}
