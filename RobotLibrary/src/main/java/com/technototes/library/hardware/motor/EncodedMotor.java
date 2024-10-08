package com.technototes.library.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.technototes.library.hardware.Sensored;
import com.technototes.library.hardware.sensor.encoder.Encoder;
import com.technototes.library.hardware.sensor.encoder.MotorEncoder;

/**
 * Class for encoded motors
 *
 * @param <T> The qualcomm motor device interface
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class EncodedMotor<T extends DcMotorSimple> extends Motor<T> implements Sensored {

    /**
     * Deadzone for going to positions with encoder
     */
    public double positionThreshold = 50;

    private Encoder encoder;
    private DcMotor.RunMode runMode;

    /**
     * Make encoded motor
     *
     * @param device The dcmotor object
     * @param e      The encoder for the motor
     */
    public EncodedMotor(T device, Encoder e, String nm) {
        super(device, nm);
        encoder = e;
    }

    /**
     * Make encoded motor
     *
     * @param device The name of the motor
     * @param e      The encoder for the motor
     */
    public EncodedMotor(String device, Encoder e) {
        super(device);
        encoder = e;
    }

    /**
     * Make encoded motor, with the default encoder configured
     *
     * @param device The dcmotor object
     */
    public EncodedMotor(T device, String nm) {
        super(device, nm);
        if (device instanceof DcMotorEx) {
            encoder = new MotorEncoder((DcMotorEx) device, nm);
        }
    }

    /**
     * Make encoded motor, with the default encoder configured
     *
     * @param deviceName The dcmotor device name in hardware map
     */
    public EncodedMotor(String deviceName) {
        super(deviceName);
        T device = getRawDevice();
        if (device instanceof DcMotorEx) {
            encoder = new MotorEncoder((DcMotorEx) device, deviceName);
        }
    }

    /**
     * Explicitly set the encoder for the motor
     *
     * @param enc The encoder
     * @return The motor (for chaining)
     */
    public EncodedMotor<T> setEncoder(Encoder enc) {
        encoder = enc;
        return this;
    }

    /**
     * Configure the PIDF constants for the motor
     *
     * @param p The Proportional coefficient
     * @param i The Integral coefficient
     * @param d The Derivative coefficient
     * @param f The Forward Feedback coefficient
     * @return The motor (for chaining)
     */
    public EncodedMotor<T> setPIDFCoefficients(double p, double i, double d, double f) {
        T device = getRawDevice();
        if (device instanceof DcMotorEx) {
            ((DcMotorEx) device).setVelocityPIDFCoefficients(p, i, d, f);
        }
        return this;
    }

    /**
     * Configure the PIDF constants for the motor
     *
     * @param coefficients The PIDF coefficients to set
     * @return The motor (for chaining)
     */
    public EncodedMotor<T> setPIDFCoefficients(PIDFCoefficients coefficients) {
        return setPIDFCoefficients(coefficients.p, coefficients.i, coefficients.d, coefficients.f);
    }

    /**
     * Configure the PIDF constants for the motor
     *
     * @param c The PIDF coefficients to set
     * @return The motor (for chaining)
     */
    public EncodedMotor<T> setPIDFCoefficients(DcMotor.RunMode m, PIDFCoefficients c) {
        T device = getRawDevice();
        if (device instanceof DcMotorEx) {
            ((DcMotorEx) device).setPIDFCoefficients(m, c);
        }
        return this;
    }

    /**
     * Set the runmode for the motor
     *
     * @param m The RunMode to set
     * @return The motor (for chaining)
     */
    public EncodedMotor<T> setRunMode(DcMotor.RunMode m) {
        T device = getRawDevice();
        if (device instanceof DcMotor) {
            ((DcMotor) device).setMode(m);
        }
        runMode = m;
        return this;
    }

    /**
     * Set the motor to go *backward*
     */
    @Override
    public EncodedMotor<T> setBackward() {
        super.setBackward();
        return this;
    }

    /**
     * Set the motor to go *forward*
     */
    @Override
    public EncodedMotor<T> setForward() {
        super.setForward();
        return this;
    }

    /**
     * Set the motor to go in a particular direction
     */
    @Override
    public EncodedMotor<T> setDirection(DcMotorSimple.Direction dir) {
        super.setDirection(dir);
        return this;
    }

    /**
     * Get the encoder position value
     *
     * @return The encoder position value
     */
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

    /**
     * Set the position of the motor
     *
     * @param ticks The encoder ticks
     * @return Is the motor at this position
     */
    public boolean setPosition(double ticks) {
        return setPosition(ticks, 0.5);
    }

    /**
     * Set the power for the motor to try to get to the position specified.
     * This is not particularly useful, unfortunately. You probably want to go look
     * at the MotorAsServoSubsystem code (which may or may not be in this repo currently)
     *
     * @param ticks The encoder ticks to try to go to
     * @param speed The speed to run the motor at
     * @return Is the motor at this position (with no deadzone at all)
     */
    public boolean setPosition(double ticks, double speed) {
        if (!isAtPosition(ticks)) {
            setPower(getSensorValue() < ticks ? speed : -speed);
        } else {
            setPower(0);
            return true;
        }
        return false;
    }

    /**
     * Is the motor at the specified position
     *
     * @param ticks The position
     * @return Is the motor here or not
     */
    public boolean isAtPosition(double ticks) {
        return Math.abs(ticks - getSensorValue()) < positionThreshold;
    }

    /**
     * Get the encoder object
     *
     * @return The encoder
     */
    public Encoder getEncoder() {
        return encoder;
    }

    /**
     * Zero the encoder
     *
     * @return This
     */
    public EncodedMotor<T> tare() {
        encoder.zeroEncoder();
        return this;
    }

    @Override
    public Double get() {
        return getSensorValue();
    }

    /**
     * Set velocity of motor in tps
     *
     * @param tps the speed in encoder ticks per second
     */
    public void setVelocity(double tps) {
        T device = getRawDevice();
        if (device instanceof DcMotor) {
            setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
            device.setPower(tps);
        }
    }

    /**
     * Get the power for the motor (Velocity, I guess?)
     *
     * @return the power for the motor
     */
    public double getVelocity() {
        return getPower();
    }

    @Override
    public EncodedMotor<T> brake() {
        return (EncodedMotor<T>) super.brake();
    }

    @Override
    public EncodedMotor<T> coast() {
        return (EncodedMotor<T>) super.coast();
    }

    @Override
    public EncodedMotor<T> setLimits(double mi, double ma) {
        return (EncodedMotor<T>) super.setLimits(mi, ma);
    }

    // Ah, Java, you're such a hideous language...
    public <U extends DcMotorSimple> U getRawMotor(Class<U> type) {
        T device = getRawDevice();
        return (device != null && type.isInstance(device)) ? type.cast(device) : null;
    }
}
