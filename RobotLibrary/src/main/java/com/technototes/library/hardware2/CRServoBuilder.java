package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

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

    public CRServoBuilder direction(DcMotorSimple.Direction dir){
        product.setDirection(dir);
        return this;
    }

    public CRServoBuilder forward(){
        return direction(DcMotorSimple.Direction.FORWARD);
    }

    public CRServoBuilder reverse(){
        return direction(DcMotorSimple.Direction.REVERSE);
    }

}
