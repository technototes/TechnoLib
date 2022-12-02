package com.technototes.library.hardware.sensor.encoder;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.technototes.library.hardware.sensor.Sensor;

/**
 * A wrapper around an AnalogInput that enables "zeroing" for usefulness
 */
public class ExternalEncoder extends Sensor<AnalogInput> implements Encoder {

    private double zero = 0;

    /**
     * Create an ExternalEncoder from an arbitrary AnalogInput
     *
     * @param device the AnalogInput device
     */
    public ExternalEncoder(AnalogInput device) {
        super(device);
    }

    /**
     * Create an ExternalEncoder from an arbitrary AnalogInput device
     *
     * @param deviceName the name of the AnalogInput device
     */
    public ExternalEncoder(String deviceName) {
        super(deviceName);
    }

    /**
     * Set the current device value as "zero"
     */
    @Override
    public void zeroEncoder() {
        zero = getDevice().getVoltage();
    }

    /**
     * Get the sensor value (relative to the assigned zero)
     *
     * @return the value of the input
     */
    @Override
    public double getSensorValue() {
        return getDevice().getVoltage() - zero;
    }
}
