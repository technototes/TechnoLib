package com.technototes.library.hardware.sensor;

import com.qualcomm.robotcore.hardware.AnalogInput;

/** Class for analog sensors
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class AnalogSensor extends Sensor<AnalogInput> {

    private double val;

    /** Make an analog sensor
     *
     * @param device The analog device
     */
    public AnalogSensor(AnalogInput device, String nm) {
        super(device, nm);
    }

    /** Make an analog sensor
     *
     * @param deviceName The device name in hardware map
     */
    public AnalogSensor(String deviceName) {
        super(deviceName);
    }

    @Override
    public String LogLine() {
        return logData(String.format("%f1.4", val));
    }

    public double getSensorValue() {
        AnalogInput device = getRawDevice();
        if (device != null) {
            val = device.getMaxVoltage();
        }
        return val;
    }
}
