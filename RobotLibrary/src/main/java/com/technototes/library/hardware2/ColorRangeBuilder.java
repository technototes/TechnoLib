package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.ColorRangeSensor;

public class ColorRangeBuilder extends HardwareBuilder<ColorRangeSensor> {
    public ColorRangeBuilder(String name) {
        super(name);
    }

    public ColorRangeBuilder(ColorRangeSensor device) {
        super(device);
    }

    public ColorRangeBuilder gain(float val){
        product.setGain(val);
        return this;
    }

    public ColorRangeBuilder led(boolean l){
        product.enableLed(l);
        return this;
    }

    public ColorRangeBuilder enable(){
        return led(true);
    }

    public ColorRangeBuilder disable(){
        return led(false);
    }

}
