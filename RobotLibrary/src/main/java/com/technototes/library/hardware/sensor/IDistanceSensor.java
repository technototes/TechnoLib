package com.technototes.library.hardware.sensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public interface IDistanceSensor {
    default double getDistance(){
        return getDistance(getUnit());
    }
    double getDistance(DistanceUnit unit);

    IDistanceSensor onUnit(DistanceUnit distanceUnit);
    DistanceUnit getUnit();

}
