package com.technototes.library.hardware;

import java.util.function.DoubleSupplier;

/**
 * Class for hardware devices with a sensor value
 * <p>
 * This name is just bad. I'd also like to add normalization to it, so we could scale input values
 * to consistent ranges (triggers, joystick axes, distance sensors, servo ranges, etc...)
 *
 * @author Alex Stedman
 */
@Deprecated
@SuppressWarnings("unused")
public interface Sensored extends DoubleSupplier {
    /**
     * Get the sensor value
     *
     * @return The value
     */
    double getSensorValue();

    @Override
    default double getAsDouble() {
        return getSensorValue();
    }
}
