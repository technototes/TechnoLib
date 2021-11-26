package com.technototes.library.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.technototes.library.hardware.Sensored;
import com.technototes.library.hardware.sensor.encoder.Encoder;
import com.technototes.library.hardware.sensor.encoder.MotorEncoder;

/** Class for encoded motors
 * @author Alex Stedman
 * @param <T> The qualcomm motor device interface
 */
@SuppressWarnings("unused")
public class EncodedMotor<T extends DcMotorSimple> extends Motor<T> implements Sensored {

    /** Deadzone for going to positions with encoder
     *
     */
    public double positionThreshold = 50;

    private Encoder encoder;

    /** Make encoded motor
     *
     * @param device The dcmotor object
     */
    public EncodedMotor(T device, Encoder e) {
        super(device);
        encoder = e;
    }
    public EncodedMotor(String device, Encoder e) {
        super(device);
        encoder = e;
    }

    public EncodedMotor(T device) {
        super(device);
        if (device instanceof DcMotorEx) {
            encoder = new MotorEncoder((DcMotorEx) device);
        }
    }



    /** Make encoded motor
     *
     * @param deviceName The dcmotor device name in hardware map
     */
    public EncodedMotor(String deviceName) {
        super(deviceName);
        if (getDevice() instanceof DcMotorEx) {
            encoder = new MotorEncoder((DcMotorEx) getDevice());
        }
    }

    public EncodedMotor<T> setEncoder(Encoder enc){
        encoder = enc;
        return this;
    }

    public EncodedMotor<T> setPIDFCoeffecients(double p, double i, double d, double f){
        if(getDevice() instanceof DcMotorEx){
            ((DcMotorEx) getDevice()).setVelocityPIDFCoefficients(p, i, d, f);
        }
        return this;
    }
    public EncodedMotor<T> setPIDFCoeffecients(PIDFCoefficients coeffecients){
        return setPIDFCoeffecients(coeffecients.p, coeffecients.i, coeffecients.d, coeffecients.f);
    }
    public EncodedMotor<T> setRunMode(DcMotor.RunMode m){
        if(getDevice() instanceof DcMotor)
            ((DcMotor) getDevice()).setMode(m);
        return this;
    }

    @Override
    public EncodedMotor<T> setInverted(boolean invert) {
        getDevice().setDirection(invert ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        return this;
    }

    @Override
    public EncodedMotor<T> invert() {
        return setInverted(!getInverted());
    }

    @Override
    public double getSensorValue() {
        return encoder.getSensorValue();
    }
//
//    @Override
//    public void setPIDValues(double p, double i, double d) {
//        coefficients = new PIDCoefficients(p, i, d);
//    }

//    @Override
//    public boolean setPositionPID(double val) {
//        if (!isAtPosition(val)) {
//            setSpeed(MathUtils.constrain(-0.1,(val-getSensorValue())/(coefficients.kP*10000), 0.1)*10);
//        } else {
//            setSpeed(0);
//            return true;
//        }
//        return false;
//    }

    /** Set the position of the motor
     *
     * @param ticks The encoder ticks
     * @return Is the motor at this position
     */
    public boolean setPosition(double ticks) {
        return setPosition(ticks, 0.5);
    }
    /** Set the position of the motor
     *
     * @param ticks The encoder ticks
     * @param speed The speed to run the motor at
     * @return Is the motor at this position
     */
    public boolean setPosition(double ticks, double speed) {
        if (!isAtPosition(ticks)) {
            setSpeed(getSensorValue() < ticks ? speed : -speed);
        } else {
            setSpeed(0);
            return true;
        }
        return false;
    }

    /** Is the motor at the specified position
     *
     * @param ticks The position
     * @return Is the motor here or not
     */
    public boolean isAtPosition(double ticks) {
        return Math.abs(ticks - getSensorValue()) < positionThreshold;
    }

    /** Get the encoder object
     *
     * @return The encoder
     */
    public Encoder getEncoder(){
        return encoder;
    }

    /** Zero the encoder
     * @return This
     */
    public EncodedMotor<T> zeroEncoder(){
        encoder.zeroEncoder();
        return this;
    }

    @Override
    public Double get() {
        return getSensorValue();
    }

    /** Set velocity of motor in tps
     *
     * @param tps the speed in encoder ticks per second
     */
    public void setVelocity(double tps) {
        if(getDevice() instanceof DcMotor){
            ((DcMotor) getDevice()).setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            getDevice().setPower(tps);
        }
    }

    public double getVelocity(){
        return getDevice().getPower();
    }
    @Override
    public double getSpeed(){
        return getDevice().getPower();
    }


    @Override
    public void setSpeed(double speed) {
        //if(getDevice() instanceof DcMotor) ((DcMotor) getDevice()).setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        super.setSpeed(speed);
    }
}
