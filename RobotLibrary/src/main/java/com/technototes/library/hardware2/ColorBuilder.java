package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * TODO: Remove this. I don't believe this adds much value. Yeah, HardwareMap.get is weird, but
 * it's in all the documentation, so just read it, see it in the examples, and you're done.
 */
public class ColorBuilder extends HardwareBuilder<ColorSensor> {

    public ColorBuilder(String name) {
        super(name);
    }

    public ColorBuilder(ColorSensor device) {
        super(device);
    }
}
