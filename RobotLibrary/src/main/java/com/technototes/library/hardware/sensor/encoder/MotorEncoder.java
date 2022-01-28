package com.technototes.library.hardware.sensor.encoder;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.sensor.Sensor;

/**
 * Wraps a motor instance to provide corrected velocity counts and allow reversing independently of the corresponding
 * slot's motor direction
 */
public class MotorEncoder extends Sensor<DcMotorEx> implements Encoder{
    private final static int CPS_STEP = 0x10000;
    public int offset = 0;

    private static double inverseOverflow(double input, double estimate) {
        double real = input;
        while (Math.abs(estimate - real) > CPS_STEP / 2.0) {
            real += Math.signum(estimate - real) * CPS_STEP;
        }
        return real;
    }

    @Override
    public double getSensorValue() {
        return getCurrentPosition();
    }

    @Override
    public void zeroEncoder() {
        offset = motor.getCurrentPosition();
    }

    public enum Direction {
        FORWARD(1),
        REVERSE(-1);

        private int multiplier;

        Direction(int multiplier) {
            this.multiplier = multiplier;
        }

        public int getMultiplier() {
            return multiplier;
        }
    }

    private DcMotorEx motor;
    private ElapsedTime clock;

    private Direction direction;

    private int lastPosition;
    private double velocityEstimate;
    private double lastUpdateTime;

    public MotorEncoder(DcMotorEx motor, ElapsedTime clock) {
        super(motor);
        //motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.motor = motor;
        this.clock = clock;
        clock.reset();

        this.direction = Direction.FORWARD;

        this.lastPosition = 0;
        this.velocityEstimate = 0.0;
        this.lastUpdateTime = clock.seconds();
    }

    public MotorEncoder(DcMotorEx motor) {
        this(motor, new ElapsedTime());
    }

    public MotorEncoder(EncodedMotor<DcMotorEx> motor){
        this(motor.getDevice());
    }
    public MotorEncoder(String deviceName){
        this(hardwareMap.get(DcMotorEx.class, deviceName));
    }

    public Direction getDirection() {
        return direction;
    }

    private int getMultiplier() {
        return getDirection().getMultiplier() * (motor.getDirection() == DcMotorSimple.Direction.FORWARD ? 1 : -1);
    }

    /**
     * Allows you to set the direction of the counts and velocity without modifying the motor's direction state
     * @param direction either reverse or forward depending on if encoder counts should be negated
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public MotorEncoder invert(){
        setDirection(getDirection() == Direction.FORWARD ? Direction.REVERSE : Direction.FORWARD);
        return this;
    }

    public int getCurrentPosition() {
        int multiplier = getMultiplier();
        int currentPosition = (motor.getCurrentPosition()-offset) * multiplier;
        if (currentPosition != lastPosition) {
            double currentTime = clock.seconds();
            double dt = currentTime - lastUpdateTime;
            velocityEstimate = (currentPosition - lastPosition) / dt;
            lastPosition = currentPosition;
            lastUpdateTime = currentTime;
        }
        return currentPosition;
    }

    public double getRawVelocity() {
        int multiplier = getMultiplier();
        return motor.getVelocity() * multiplier;
    }

    public double getCorrectedVelocity() {
        return inverseOverflow(getRawVelocity(), velocityEstimate);
    }
}
