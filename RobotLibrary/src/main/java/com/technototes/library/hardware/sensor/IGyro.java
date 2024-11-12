package com.technototes.library.hardware.sensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * An interface for a single-angle gyroscope
 */
public interface IGyro {
    /**
     * Set the units for the various values
     */
    void setUnits(AngleUnit u);

    /**
     * Get the units the gyroscope is set to
     */
    AngleUnit getUnits();

    /**
     * Get the heading (in specified units)
     */
    double getHeading(AngleUnit u);

    /**
     * Sets the current heading (in default units)
     *
     * @param newHeading The new heading (in default units)
     * @param u The angle units of the new heading
     */
    void setHeading(double newHeading, AngleUnit u);

    /**
     * Gets the current angular velocity (in specified units)
     */
    double getVelocity(AngleUnit u);

    default void degrees() {
        setUnits(AngleUnit.DEGREES);
    }

    default void radians() {
        setUnits(AngleUnit.RADIANS);
    }

    /**
     * Zeroes the current heading (in default units)
     */
    default void zero() {
        setHeading(0, getUnits());
    }

    /**
     * Get the heading (in default units)
     */
    default double getHeading() {
        return getHeading(getUnits());
    }

    /**
     * Get the heading (in degrees)
     */
    default double getHeadingInDegrees() {
        return getHeading(AngleUnit.DEGREES);
    }

    /**
     * Get the heading (in radians)
     */
    default double getHeadingInRadians() {
        return getHeading(AngleUnit.RADIANS);
    }

    /**
     * Gets the current angular velocity (in default units)
     */
    default double getVelocity() {
        return getVelocity(getUnits());
    }

    /**
     * Gets the current angular velocity (in radians)
     */
    default double getVelocityInDegrees() {
        return getVelocity(AngleUnit.DEGREES);
    }

    /**
     * Gets the current angular velocity (in degrees)
     */
    default double getVelocityInRadians() {
        return getVelocity(AngleUnit.RADIANS);
    }

    /**
     * Sets the current heading (in default units)
     *
     * @param newHeading The new heading (in default units)
     */
    default void setHeading(double newHeading) {
        setHeading(newHeading, getUnits());
    }

    /**
     * Sets the current heading (in default units)
     *
     * @param newHeading The new heading (in default units)
     */
    default void setHeadingInDegrees(double newHeading) {
        setHeading(newHeading, AngleUnit.DEGREES);
    }

    /**
     * Sets the current heading (in default units)
     *
     * @param newHeading The new heading (in default units)
     */
    default void setHeadingInRadians(double newHeading) {
        setHeading(newHeading, AngleUnit.RADIANS);
    }
}
