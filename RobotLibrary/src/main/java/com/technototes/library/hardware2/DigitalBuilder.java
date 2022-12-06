package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.DigitalChannel;

/**
 * TODO: Remove this. I don't believe this adds much value. Yeah, HardwareMap.get is weird, but
 * it's in all the documentation, so just read it, see it in the examples, and you're done.
 */
public class DigitalBuilder extends HardwareBuilder<DigitalChannel> {

    /**
     * Don't use this in the future
     *
     * @param name undoc
     */
    public DigitalBuilder(String name) {
        super(name);
    }

    /**
     * Don't use this in the future
     *
     * @param device undoc
     */
    public DigitalBuilder(DigitalChannel device) {
        super(device);
    }

    /**
     * Don't use this in the future
     *
     * @param port undoc
     */
    public DigitalBuilder(int port) {
        super(port);
    }

    /**
     * Don't use this in the future
     *
     * @param mode undoc
     * @return undoc
     */
    public DigitalBuilder mode(DigitalChannel.Mode mode) {
        product.setMode(mode);
        return this;
    }

    /**
     * Don't use this in the future
     *
     * @return undoc
     */
    public DigitalBuilder input() {
        return mode(DigitalChannel.Mode.INPUT);
    }

    /**
     * Don't use this in the future
     *
     * @return undoc
     */
    public DigitalBuilder output() {
        return mode(DigitalChannel.Mode.OUTPUT);
    }
}
