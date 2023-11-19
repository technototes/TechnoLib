package com.technototes.library.hardware.sensor;

import com.qualcomm.robotcore.hardware.DigitalChannel;

/** Class for digital sensors
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class DigitalSensor extends Sensor<DigitalChannel> {

    private boolean val;

    /** Make a digital sensor
     *
     * @param device The device
     */
    public DigitalSensor(DigitalChannel device, String nm) {
        super(device, nm);
        val = false;
    }

    /** Make a digital sensor
     *
     * @param deviceName The device name in hardware map
     */
    public DigitalSensor(String deviceName) {
        super(deviceName);
    }

    @Override
    public String LogLine() {
        return logData(val ? "T" : "F");
    }

    /** Get the sensor value as a boolean
     *
     * @return Sensor value as boolean
     */
    public boolean getValue() {
        DigitalChannel device = getRawDevice();
        if (device != null) {
            val = device.getState();
        }
        return val;
    }
}
