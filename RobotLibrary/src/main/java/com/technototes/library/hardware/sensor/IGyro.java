package com.technototes.library.hardware.sensor;

public interface IGyro {
    double gyroHeading();

    double gyroHeadingInDegrees();

    double gyroHeadingInRadians();

    void setHeading(double newHeading);

    default void zero(){
        setHeading(0);
    }
}
