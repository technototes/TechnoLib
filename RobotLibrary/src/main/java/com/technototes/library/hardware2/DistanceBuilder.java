package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.DistanceSensor;

/**
 * TODO: Remove this. I don't believe this adds much value. Yeah, HardwareMap.get is weird, but
 * it's in all the documentation, so just read it, see it in the examples, and you're done.
 */
public class DistanceBuilder extends HardwareBuilder<DistanceSensor> {

    public DistanceBuilder(String name) {
        super(name);
    }

    public DistanceBuilder(DistanceSensor device) {
        super(device);
    }
}
