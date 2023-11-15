package com.technototes.library.hardware.sensor;

/** Class for color sensors
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class ColorSensor extends Sensor<com.qualcomm.robotcore.hardware.ColorSensor> implements IColorSensor {

    public int val;

    /** Make a color Sensor
     *
     * @param device The hardware device
     */
    public ColorSensor(com.qualcomm.robotcore.hardware.ColorSensor device, String nm) {
        super(device, nm);
    }

    /** Make a color sensor
     *
     * @param deviceName The device name in hardware map
     */
    public ColorSensor(String deviceName) {
        super(deviceName);
    }

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

    @Override
    public int argb() {
        val = getDevice().argb();
        return val;
    }
}
