package com.technototes.library.hardware.sensor;

import com.qualcomm.robotcore.hardware.DigitalChannel;

/** Class for digital sensors
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class DigitalSensor extends Sensor<DigitalChannel> {
    /** Make a digital sensor
     *
     * @param device The device
     */
    public DigitalSensor(DigitalChannel device) {
        super(device);
    }

    /** Make a digital sensor
     *
     * @param deviceName The device name in hardware map
     */
    public DigitalSensor(String deviceName) {
        super(deviceName);
    }


    /** Get the sensor value as a boolean
     *
     * @return Sensor value as boolean
     */
    public boolean getValue() {
        return getDevice().getState();
    }
}
