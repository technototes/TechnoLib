package com.technototes.vision;

import org.opencv.core.Scalar;

/**
 * This is used to detect colors in a particular HSV 'range'
 */
/**
 * This is used to detect colors in a particular HSV 'range'
 */
public class HSVRange {

    int hueLow, hueHigh;
    int satLow, satHigh;
    int valLow, valHigh;

    // Private constructor for wrap around/truncation stuff
    private HSVRange(int hLo, int hHi, HSVRange copyFrom) {
        hueLow = hLo;
        hueHigh = hHi;
        satLow = copyFrom.satLow;
        satHigh = copyFrom.satHigh;
        valLow = copyFrom.valLow;
        valHigh = copyFrom.valHigh;
    }

    /**
     * Create an HSV color range, around 'hue' (0-180, 2 degrees)
     *
     * @param hue The hue (0-179): each unit is *2* degrees!
     * @param hRange The +/- on the hue for detection
     * @param sLo The low saturation range
     * @param sHi The high saturation range
     * @param vLo The low value range
     * @param vHi The high value range
     */
    public HSVRange(int hue, int hRange, int sLo, int sHi, int vLo, int vHi) {
        while (hue > 180) {
            hue -= 180;
        }
        while (hue < 0) {
            hue += 180;
        }
        hueLow = hue - Math.min(Math.abs(hRange), 89);
        hueHigh = hue + Math.min(Math.abs(hRange), 89);
        satLow = Math.min(sLo, sHi);
        satHigh = Math.max(sLo, sHi);
        valLow = Math.min(vLo, vHi);
        valHigh = Math.max(vLo, vHi);
    }

    /**
     * @return An HSVRange for the 'low end' of the hue range, if it's below 0, null otherwise
     */
    public HSVRange makeWrapAround() {
        if (hueLow >= 0) {
            return null;
        }
        return new HSVRange(180 + hueLow, 180, this);
    }

    /**
     * @return An HSVRange for the positive part of a hue range if it spans across 0, null otherwise
     */
    public HSVRange truncateRange() {
        if (hueLow < 0) {
            return new HSVRange(0, hueHigh, this);
        }
        return null;
    }

    /**
     * @param newHue The new hue for an existing HSVrange
     * @param hRange The new range for an existing HSVRange
     * @return A new HSVRange using the current Saturation and Values, with a new hue &amp; hue range
     */
    public HSVRange newHue(int newHue, int hRange) {
        return new HSVRange(newHue, hRange, satLow, satHigh, valLow, valHigh);
    }

    /**
     * @return A Scalar for OpenCV use for color detection for the low end
     */
    public Scalar lowEdge() {
        return new Scalar(hueLow, satLow, valLow);
    }

    /**
     * @return A Scalar for OpenCV use for color detection for the high end
     */
    public Scalar highEdge() {
        return new Scalar(hueHigh, satHigh, valHigh);
    }
}
