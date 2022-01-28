package com.technototes.library.hardware.sensor;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/** Class for range sensors
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class Rev2MDistanceSensor extends Sensor<DistanceSensor> implements IDistanceSensor {

    private DistanceUnit distanceUnit;

    /**
     * Create a range sensor
     *
     * @param device The sensor device
     */
    public Rev2MDistanceSensor(DistanceSensor device) {
        super(device);
    }

    /**
     * Create a range sensor
     *
     * @param deviceName The device name
     */
    public Rev2MDistanceSensor(String deviceName) {
        super(deviceName);
    }

    /**
     * Get the value with a specified distance Unit
     *
     * @param distanceUnit The unit
     * @return The distance
     */
    @Override
    public double getDistance(DistanceUnit distanceUnit) {
        return getDevice().getDistance(distanceUnit);
    }

    /**
     * Get the current distance unit
     *
     * @return The distance unit
     */
    @Override
    public DistanceUnit getUnit() {
        return distanceUnit;
    }


    /**
     * Set the distance unit
     *
     * @param distanceUnit The unit
     * @return This
     */
    @Override
    public Rev2MDistanceSensor onUnit(DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
        return this;
    }
}
