package com.technototes.library.util;

/**
 * Helper class for tracking a range
 */
public class Range {

    /**
     * The minimum value of the range
     */
    public double min;

    /**
     * The maximum value of the range
     */
    public double max;

    /**
     * Create a range with the given minimum and maximum
     *
     * @param mi Min value
     * @param ma Max value
     */
    public Range(double mi, double ma) {
        min = mi;
        max = ma;
        if (mi > ma) {
            throw new IllegalArgumentException("Minimum is greater than maximum");
        }
    }

    /**
     * Check if the value is in the range
     *
     * @param val The value to check
     * @return True if in (inclusive) range, false otherwise
     */
    public boolean inRange(double val) {
        return val >= min && val <= max;
    }

    /**
     * Scale the range by a given value
     *
     * @param scalar The amount to scale the range by
     */
    public void scale(double scalar) {
        min *= scalar;
        max *= scalar;
        if (min > max) {
            double tmp = min;
            min = max;
            max = tmp;
        }
    }

    /**
     * Get the 'middle' of the range
     *
     * @return the average of min & max
     */
    public double middle() {
        return (min + max) / 2;
    }
}
