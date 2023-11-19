package com.technototes.library.hardware.sensor;

import com.qualcomm.robotcore.hardware.ColorRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorDistanceSensor extends Sensor<ColorRangeSensor> implements IDistanceSensor, IColorSensor {

    private DistanceUnit distanceUnit;
    private double dist;
    private double light;
    private int color;

    public ColorDistanceSensor(String name) {
        super(name);
        distanceUnit = DistanceUnit.CM;
    }

    @Override
    public String LogLine() {
        int alpha = (color >> 24) & 0xFF;
        if (alpha != 0 && alpha != 0xFF) {
            return logData(
                    String.format(
                            "d:%f1.2%s A(%d)R(%d)G(%d)B(%d) [%f1.3]",
                            dist,
                            distanceUnit,
                            alpha,
                            (color >> 16) & 0xFF,
                            (color >> 8) & 0xFF,
                            color & 0xFF,
                            light
                    )
            );
        } else {
            return logData(
                    String.format(
                            "d:%f1.2%s R(%d)G(%d)B(%d) [%f1.3]",
                            dist,
                            distanceUnit,
                            (color >> 16) & 0xFF,
                            (color >> 8) & 0xFF,
                            color & 0xFF,
                            light
                    )
            );
        }
    }

    public ColorDistanceSensor(ColorRangeSensor device, String nm) {
        super(device, nm);
    }

    @Override
    public double getDistance(DistanceUnit unit) {
        ColorRangeSensor device = getRawDevice();
        double val = dist;
        if (device != null) {
            val = device.getDistance(unit);
            dist = distanceUnit.fromUnit(unit, val);
        }
        return val;
    }

    @Override
    public ColorDistanceSensor onUnit(DistanceUnit unit) {
        distanceUnit = unit;
        return this;
    }

    @Override
    public DistanceUnit getUnit() {
        return distanceUnit;
    }

    @Override
    public int argb() {
        ColorRangeSensor device = getRawDevice();
        if (device != null) {
            color = device.argb();
        }
        return color;
    }

    public double getLight() {
        ColorRangeSensor device = getRawDevice();
        if (device != null) {
            light = device.getRawLightDetected();
        }
        return light;
    }
}
