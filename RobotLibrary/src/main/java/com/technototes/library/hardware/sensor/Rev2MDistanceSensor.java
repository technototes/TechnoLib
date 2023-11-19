package com.technototes.library.hardware.sensor;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Class for the Rev '2m' range sensors
 * Note for users: The Rev 2m range sensor is actually only useful at about 1.1 - 1.2m :/
 *
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class Rev2MDistanceSensor extends Sensor<DistanceSensor> implements IDistanceSensor {

    private DistanceUnit distanceUnit;
    private double dist;

    /**
     * Create a range sensor
     *
     * @param device The sensor device
     */
    public Rev2MDistanceSensor(DistanceSensor device, String nm) {
        super(device, nm);
    }

    /**
     * Create a range sensor
     *
     * @param deviceName The device name
     */
    public Rev2MDistanceSensor(String deviceName) {
        super(deviceName);
    }

    @Override
    public String LogLine() {
        return logData(String.format("%f1.3%s", this.dist, this.distanceUnit));
    }

    /**
     * Get the value with a specified distance Unit
     *
     * @param distanceUnit The unit
     * @return The distance
     */
    @Override
    public double getDistance(DistanceUnit distanceUnit) {
        DistanceSensor device = getRawDevice();
        if (device != null) {
            dist = device.getDistance(distanceUnit);
        }
        return dist;
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
