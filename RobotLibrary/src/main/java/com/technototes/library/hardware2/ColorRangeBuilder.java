package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.ColorRangeSensor;

/**
 * TODO: Remove this. I don't believe this adds much value. Yeah, HardwareMap.get is weird, but
 * it's in all the documentation, so just read it, see it in the examples, and you're done.
 */
public class ColorRangeBuilder extends HardwareBuilder<ColorRangeSensor> {

    public ColorRangeBuilder(String name) {
        super(name);
    }

    public ColorRangeBuilder(ColorRangeSensor device) {
        super(device);
    }

    public ColorRangeBuilder gain(float val) {
        product.setGain(val);
        return this;
    }

    public ColorRangeBuilder led(boolean l) {
        product.enableLed(l);
        return this;
    }

    public ColorRangeBuilder enable() {
        return led(true);
    }

    public ColorRangeBuilder disable() {
        return led(false);
    }
}
