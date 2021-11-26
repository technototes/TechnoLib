package com.technototes.library.hardware;

import java.util.function.DoubleSupplier;

/** Class for hardware devices with a sensor value
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public interface Sensored extends DoubleSupplier {
    /** Get the sensor value
     *
     * @return The value
     */
    double getSensorValue();


    @Override
    default double getAsDouble(){
        return getSensorValue();
    }
}
