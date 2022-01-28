package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.DistanceSensor;

public class DistanceBuilder extends HardwareBuilder<DistanceSensor> {
    public DistanceBuilder(String name) {
        super(name);
    }

    public DistanceBuilder(DistanceSensor device) {
        super(device);
    }
}
