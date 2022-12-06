package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * TODO: Remove this. I don't believe this adds much value. Yeah, HardwareMap.get is weird, but
 * it's in all the documentation, so just read it, see it in the examples, and you're done.
 */
public class CRServoBuilder extends HardwareBuilder<CRServo> {

    public CRServoBuilder(String name) {
        super(name);
    }

    public CRServoBuilder(CRServo device) {
        super(device);
    }

    public CRServoBuilder(int port) {
        super(port);
    }

    public CRServoBuilder direction(DcMotorSimple.Direction dir) {
        product.setDirection(dir);
        return this;
    }

    public CRServoBuilder forward() {
        return direction(DcMotorSimple.Direction.FORWARD);
    }

    public CRServoBuilder reverse() {
        return direction(DcMotorSimple.Direction.REVERSE);
    }
}
