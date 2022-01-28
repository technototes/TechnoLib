package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.AnalogSensor;

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
