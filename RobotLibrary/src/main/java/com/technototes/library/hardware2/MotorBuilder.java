package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class MotorBuilder extends HardwareBuilder<DcMotorEx> {
    public MotorBuilder(String name) {
        super(name);
    }

    public MotorBuilder(DcMotorEx device) {
        super(device);
    }

    public MotorBuilder(int port) {
        super(port);
    }

    public MotorBuilder direction(DcMotorSimple.Direction dir){
        product.setDirection(dir);
        return this;
    }

    public MotorBuilder forward(){
        return direction(DcMotorSimple.Direction.FORWARD);
    }

    public MotorBuilder reverse(){
        return direction(DcMotorSimple.Direction.REVERSE);
    }

    public MotorBuilder idle(DcMotor.ZeroPowerBehavior zeroPowerBehavior){
        product.setZeroPowerBehavior(zeroPowerBehavior);
        return this;
    }

    public MotorBuilder brake(){
        return idle(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public MotorBuilder coast(){
        return idle(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public MotorBuilder mode(DcMotor.RunMode runMode){
        product.setMode(runMode);
        return this;
    }

    public MotorBuilder position(double p){
        product.setPositionPIDFCoefficients(p);
        return mode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public MotorBuilder velocity(PIDFCoefficients p){
        product.setVelocityPIDFCoefficients(p.p, p.i, p.d, p.f);
        return mode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public MotorBuilder raw(){
        return mode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public MotorBuilder tolerance(int tolerance){
        product.setTargetPositionTolerance(tolerance);
        return this;
    }

    public MotorBuilder enable(){
        product.setMotorEnable();
        return this;
    }

    public MotorBuilder disable(){
        product.setMotorDisable();
        return this;
    }

    public MotorBuilder tare(){
        DcMotor.RunMode pastMode = product.getMode();
        product.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        product.setMode(pastMode);
        return this;
    }

}
