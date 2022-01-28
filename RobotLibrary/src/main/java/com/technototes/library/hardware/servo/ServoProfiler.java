package com.technototes.library.hardware.servo;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class ServoProfiler {
    private final Servo servo;
    private double maxVel, maxAccel, targetPosition, servoRange, targetTolerance;
    private final ElapsedTime deltaTime;
    private double delta;
    private double proportion;

    public static class Constraints{

        public double maxVelocity, maxAcceleration, proportion;
        public Constraints(double vel, double accel, double prop){
            maxVelocity = vel;
            maxAcceleration = accel;
            proportion = prop;
        }
        public double getMaxVelocity() {
            return maxVelocity;
        }

        public double getMaxAcceleration() {
            return maxAcceleration;
        }

        public double getProportion() {
            return proportion;
        }
    }

    public ServoProfiler(Servo s) {
        servo = s;
        setServoRange(0.75);
        setTargetTolerance(0.01);
        deltaTime = new ElapsedTime();
        delta = 0;
    }

    public ServoProfiler setConstraints(double vel, double accel, double prop) {
        maxVel = vel;
        maxAccel = accel;
        proportion = prop;
        return this;
    }
    public ServoProfiler setConstraints(Constraints c) {
        return setConstraints(c.maxVelocity, c.maxAcceleration, c.proportion);
    }

    public ServoProfiler setServoRange(double rangeInRotations) {
        servoRange = rangeInRotations;
        return this;
    }

    public ServoProfiler setTargetPosition(double target) {
        targetPosition = target;
        deltaTime.reset();
        return this;
    }

    public ServoProfiler setTargetTolerance(double tolerance) {
        targetTolerance = tolerance;
        return this;
    }
    //fun method to update servo
    public ServoProfiler update() {
        //if at the target dont do anything
        if (isAtTarget()) return this;
        // set the past delta pos
        double pastDelta = delta;
        //get the change in time, then reset the timer instantly
        double deltaSec = deltaTime.seconds();
        deltaTime.reset();

        //generate the new change in servo pos.
        //range.clip makes the change fit the max constraints
        // the min and max make sure both constraints are hit
        // the deltasec makes it independent of looptime
        delta = Range.clip(deltaSec * servoRange * proportion * (getTargetPosition() - getCurrentPosition()),
                Math.max(pastDelta -maxAccel*deltaSec, -maxVel*deltaSec),
                Math.min(pastDelta +maxAccel*deltaSec, maxVel*deltaSec));
        servo.setPosition(getCurrentPosition()+delta/servoRange);

        return this;
    }

    public boolean isAtTarget() {
        return Math.abs(getCurrentPosition()-getTargetPosition()) < targetTolerance;
    }

    public double getCurrentPosition() {
        return servo.getPosition();
    }

    public Servo getServo() {
        return servo;
    }

    public double getMaxVel() {
        return maxVel;
    }

    public double getMaxAccel() {
        return maxAccel;
    }

    public double getTargetPosition() {
        return targetPosition;
    }

    public double getTargetTolerance() {
        return targetTolerance;
    }

}
