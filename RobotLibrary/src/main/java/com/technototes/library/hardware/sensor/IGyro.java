package com.technototes.library.hardware.sensor;

/**
 * An interface for a single-angle gyroscope
 */
public interface IGyro {
    /**
     * The heading (in default units)
     *
     * @return the head (in default units)
     */
    double gyroHeading();

    /**
     * The heading (in degrees)
     *
     * @return the head (in degrees)
     */
    double gyroHeadingInDegrees();

    /**
     * The heading (in radians)
     *
     * @return the head (in radians units)
     */
    double gyroHeadingInRadians();

    /**
     * Sets the current heading (in default units)
     *
     * @param newHeading The new heading (in default units)
     */
    void setHeading(double newHeading);

    /**
     * Zeroes the current heading (in default units)
     */
    default void zero() {
        setHeading(0);
    }
}
