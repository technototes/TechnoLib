package com.technototes.library.hardware.sensor;

import androidx.annotation.ColorInt;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RevColorSensorV3
    extends Sensor<com.qualcomm.hardware.rev.RevColorSensorV3>
    implements IColorSensor, IDistanceSensor {

    private DistanceUnit distanceUnit;
    private double dist;

    public int val;

    /**
     * Make a color Sensor
     *
     * @param device The hardware device
     */
    public RevColorSensorV3(com.qualcomm.hardware.rev.RevColorSensorV3 device, String nm) {
        super(device, nm);
    }

    /**
     * Make a color sensor
     *
     * @param deviceName The device name in hardware map
     */
    public RevColorSensorV3(String deviceName) {
        super(deviceName);
    }

    public void enableLED(boolean on) {
        com.qualcomm.hardware.rev.RevColorSensorV3 device = getRawDevice();
        if (device != null) {
            getRawDevice().enableLed(on);
        }
    }

    // TODO: Finish this one off
    @Override
    public String LogLine() {
        int alpha = (val >> 24) & 0xFF;
        if (alpha != 0 && alpha != 0xFF) {
            return logData(
                String.format("A(%d)R(%d)G(%d)B(%d)", alpha, (val >> 16) & 0xFF, (val >> 8) & 0xFF, val & 0xFF)
            );
        } else {
            return logData(String.format("R(%d)G(%d)B(%d)", (val >> 16) & 0xFF, (val >> 8) & 0xFF, val & 0xFF));
        }
    }

    @ColorInt
    @Override
    public int argb() {
        com.qualcomm.hardware.rev.RevColorSensorV3 device = getRawDevice();
        if (device != null) {
            // TODO: Figure out what we should be reading, here...
            val = device.rawOptical();
        }
        return val;
    }

    @Override
    public double getDistance() {
        com.qualcomm.hardware.rev.RevColorSensorV3 device = getRawDevice();
        if (device != null) {
            dist = device.getDistance(getUnit());
        }
        return dist;
    }

    @Override
    public double getDistance(DistanceUnit unit) {
        com.qualcomm.hardware.rev.RevColorSensorV3 device = getRawDevice();
        if (device != null) {
            dist = device.getDistance(unit);
        }
        return dist;
    }

    @Override
    public IDistanceSensor onUnit(DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
        return this;
    }

    @Override
    public DistanceUnit getUnit() {
        return distanceUnit;
    }
}
