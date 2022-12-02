package com.technototes.library.util;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * A simple Observation-based integral calculator over time
 */
public class Integral {

    private double accumulator;
    private ElapsedTime deltaTime;

    /**
     * Initialize it with the value c
     *
     * @param c Initial value
     */
    public Integral(double c) {
        accumulator = c;
        deltaTime = new ElapsedTime();
    }

    /**
     * Initialize it with a value of 0
     */
    public Integral() {
        this(0);
    }

    /**
     * Set the value to C
     *
     * @param c the value
     * @return this
     */
    public Integral set(double c) {
        accumulator = c;
        return this;
    }

    /**
     * Set the value to zero
     *
     * @return this
     */
    public Integral zero() {
        return set(0);
    }

    /**
     * Update the accumulated value for the number of seconds since last update
     *
     * @param change the value that was observed since last integration
     * @return the current accumulations
     */
    public double update(double change) {
        accumulator += change * deltaTime.seconds();
        deltaTime.reset();
        return accumulator;
    }

    /**
     * Get the current accumulation
     *
     * @return the current accumulation
     */
    public double getValue() {
        return accumulator;
    }
}
