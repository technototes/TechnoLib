package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * TODO: Remove this. I don't believe this adds much value. Yeah, HardwareMap.get is weird, but
 * it's in all the documentation, so just read it, see it in the examples, and you're done.
 */
public class ServoBuilder extends HardwareBuilder<Servo> {

    public ServoBuilder(String name) {
        super(name);
    }

    public ServoBuilder(Servo device) {
        super(device);
    }

    public ServoBuilder(int port) {
        super(port);
    }

    public ServoBuilder direction(Servo.Direction dir) {
        product.setDirection(dir);
        return this;
    }

    public ServoBuilder forward() {
        return direction(Servo.Direction.FORWARD);
    }

    public ServoBuilder reverse() {
        return direction(Servo.Direction.REVERSE);
    }

    public ServoBuilder at(double position) {
        product.setPosition(position);
        return this;
    }

    public ServoBuilder range(double min, double max) {
        product.scaleRange(min, max);
        return this;
    }

    public ServoBuilder pwmRange(double min, double max) {
        if (product instanceof PwmControl) ((PwmControl) product).setPwmRange(
                new PwmControl.PwmRange(min, max)
            ); else throw new UnsupportedOperationException(
            "scaling pwm range only supported on devices that implement pwmcontrol"
        );
        return this;
    }

    public ServoBuilder expandedPWM() {
        return pwmRange(500, 2500);
    }
}
