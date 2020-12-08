package com.technototes.library.hardware;

import com.technototes.logger.Stated;

/** Class for hardware devices with a sensor value
 * @author Alex Stedman
 */
public interface Sensored extends Stated<Double> {
    /** Get the sensor value
     *
     * @return The value
     */
    double getSensorValue();

    @Override
    default Double getState(){
        return getSensorValue();
    }
}
