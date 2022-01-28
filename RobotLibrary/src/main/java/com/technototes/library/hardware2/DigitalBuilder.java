package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.DigitalChannel;

public class DigitalBuilder extends HardwareBuilder<DigitalChannel> {
    public DigitalBuilder(String name) {
        super(name);
    }

    public DigitalBuilder(DigitalChannel device) {
        super(device);
    }

    public DigitalBuilder(int port) {
        super(port);
    }

    public DigitalBuilder mode(DigitalChannel.Mode mode){
        product.setMode(mode);
        return this;
    }

    public DigitalBuilder input(){
        return mode(DigitalChannel.Mode.INPUT);
    }

    public DigitalBuilder output(){
        return mode(DigitalChannel.Mode.OUTPUT);
    }
}
