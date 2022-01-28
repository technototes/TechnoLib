package com.technototes.library.hardware.sensor;

/** Class for color sensors
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class ColorSensor extends Sensor<com.qualcomm.robotcore.hardware.ColorSensor> implements IColorSensor {
    /** Make a color Sensor
     *
     * @param device The hardware device
     */
    public ColorSensor(com.qualcomm.robotcore.hardware.ColorSensor device) {
        super(device);
    }

    /** Make a color sensor
     *
     * @param deviceName The device name in hardware map
     */
    public ColorSensor(String deviceName) {
        super(deviceName);
    }

    @Override
    public int argb() {
        return getDevice().argb();
    }
}
