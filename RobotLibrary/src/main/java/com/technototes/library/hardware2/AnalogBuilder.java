package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.AnalogSensor;

/**
 * TODO: Remove this. I don't believe this adds much value. Yeah, HardwareMap.get is weird, but
 * it's in all the documentation, so just read it, see it in the examples, and you're done.
 */
public class AnalogBuilder extends HardwareBuilder<AnalogSensor> {

    public AnalogBuilder(String name) {
        super(name);
    }

    public AnalogBuilder(AnalogSensor device) {
        super(device);
    }

    public AnalogBuilder(int port) {
        super(port);
    }
}
