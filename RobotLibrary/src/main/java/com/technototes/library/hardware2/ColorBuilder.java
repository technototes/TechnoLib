package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class ColorBuilder extends HardwareBuilder<ColorSensor> {
    public ColorBuilder(String name) {
        super(name);
    }

    public ColorBuilder(ColorSensor device) {
        super(device);
    }
}
