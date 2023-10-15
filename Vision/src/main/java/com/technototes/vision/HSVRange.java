package com.technototes.vision;

import org.opencv.core.Scalar;

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

    // Create an HSV color range, around 'hue' (0-180, 2 degrees)
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

    public HSVRange makeWrapAround() {
        if (hueLow >= 0) {
            return null;
        }
        return new HSVRange(180 + hueLow, 180, this);
    }

    public HSVRange truncateRange() {
        if (hueLow < 0) {
            return new HSVRange(0, hueHigh, this);
        }
        return null;
    }

    public HSVRange newHue(int newHue, int hRange) {
        return new HSVRange(newHue, hRange, satLow, satHigh, valLow, valHigh);
    }

    public Scalar lowEdge() {
        return new Scalar(hueLow, satLow, valLow);
    }

    public Scalar highEdge() {
        return new Scalar(hueHigh, satHigh, valHigh);
    }
}
