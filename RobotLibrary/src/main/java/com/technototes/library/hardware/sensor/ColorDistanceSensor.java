package com.technototes.library.hardware.sensor;

import com.qualcomm.robotcore.hardware.ColorRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorDistanceSensor extends Sensor<ColorRangeSensor> implements IDistanceSensor, IColorSensor {

    private DistanceUnit distanceUnit;

    public ColorDistanceSensor(String name){
        super(name);
    }

    public ColorDistanceSensor(ColorRangeSensor device){
        super(device);
    }

    @Override
    public double getDistance(DistanceUnit unit) {
        return getDevice().getDistance(unit);
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
        return getDevice().argb();
    }

    public double getLight(){
        return getDevice().getRawLightDetected();
    }
}
