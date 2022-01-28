package com.technototes.library.hardware.sensor;

import com.qualcomm.robotcore.hardware.AnalogInput;

/** Class for analog sensors
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class AnalogSensor extends Sensor<AnalogInput> {
    /** Make an analog sensor
     *
     * @param device The analog device
     */
    public AnalogSensor(AnalogInput device) {
        super(device);
    }

    /** Make an analog sensor
     *
     * @param deviceName The device name in hardware map
     */
    public AnalogSensor(String deviceName) {
        super(deviceName );
    }


    public double getSensorValue() {
        return getDevice().getMaxVoltage();
    }

}
