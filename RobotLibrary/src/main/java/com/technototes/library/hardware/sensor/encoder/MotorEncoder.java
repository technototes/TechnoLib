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
public class MotorEncoder extends Sensor<DcMotorEx> implements Encoder {

    private static final int CPS_STEP = 0x10000;
    public int offset = 0;

    private int curPos;
    private int rawPos;
    private double curVel;
    private double rawVel;
    private double corVel;

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

    public MotorEncoder(DcMotorEx motor, ElapsedTime clock, String nm) {
        super(motor, nm);
        // motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.motor = motor;
        this.clock = clock;
        clock.reset();

        this.direction = Direction.FORWARD;

        this.lastPosition = 0;
        this.velocityEstimate = 0.0;
        this.lastUpdateTime = clock.seconds();
    }

    public MotorEncoder(DcMotorEx motor, String nm) {
        this(motor, new ElapsedTime(), nm);
    }

    public MotorEncoder(String deviceName) {
        this(hardwareMap.get(DcMotorEx.class, deviceName), deviceName);
    }

    @Override
    public String LogLine() {
        return logData(
            String.format("%d (Raw: %d), Vel %f1.3 (Raw: %f1.3, Cor: %f1.)", curPos, rawPos, curVel, rawVel, corVel)
        );
    }

    public Direction getDirection() {
        return direction;
    }

    private int getMultiplier() {
        return (getDirection().getMultiplier() * (motor.getDirection() == DcMotorSimple.Direction.FORWARD ? 1 : -1));
    }

    /**
     * Allows you to set the direction of the counts and velocity without modifying the motor's direction state
     * @param direction either reverse or forward depending on if encoder counts should be negated
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public MotorEncoder invert() {
        setDirection(getDirection() == Direction.FORWARD ? Direction.REVERSE : Direction.FORWARD);
        return this;
    }

    public int getCurrentPosition() {
        int multiplier = getMultiplier();
        rawPos = motor.getCurrentPosition();
        curPos = (rawPos - offset) * multiplier;
        if (curPos != lastPosition) {
            double currentTime = clock.seconds();
            double dt = currentTime - lastUpdateTime;
            velocityEstimate = (curPos - lastPosition) / dt;
            lastPosition = curPos;
            lastUpdateTime = currentTime;
        }
        return curPos;
    }

    public double getRawVelocity() {
        int multiplier = getMultiplier();
        rawVel = motor.getVelocity();
        curVel = rawVel * multiplier;
        return curVel;
    }

    public double getCorrectedVelocity() {
        corVel = inverseOverflow(getRawVelocity(), velocityEstimate);
        return corVel;
    }
}
