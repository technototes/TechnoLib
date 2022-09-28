package com.technototes.path.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.technototes.library.hardware.sensor.encoder.MotorEncoder;
import com.technototes.library.subsystem.Subsystem;


import java.util.Arrays;
import java.util.List;

import static com.technototes.path.subsystem.DeadWheelConstants.*;

/*
 * Sample tracking wheel localizer implementation assuming the standard configuration:
 *
 *    /--------------\
 *    |     ____     |
 *    |     ----     |
 *    | ||        || |
 *    | ||        || |
 *    |              |
 *    |              |
 *    \--------------/
 *
 */
public class ThreeDeadWheelLocalizer extends ThreeTrackingWheelLocalizer implements Subsystem {

    protected MotorEncoder leftEncoder, rightEncoder, frontEncoder;

    protected double lateralDistance, forwardOffset, gearRatio, wheelRadius, ticksPerRev;

    protected boolean encoderOverflow;


    public ThreeDeadWheelLocalizer(MotorEncoder l, MotorEncoder r, MotorEncoder f, DeadWheelConstants constants) {
        super(
                Arrays.asList(
                new Pose2d(0, constants.getDouble(LateralDistance.class) / 2, 0), // left
                new Pose2d(0, -constants.getDouble(LateralDistance.class) / 2, 0), // right
                new Pose2d(constants.getDouble(ForwardOffset.class), 0, Math.toRadians(90)) // front
        ));
        leftEncoder = l;
        rightEncoder = r;
        frontEncoder = f;

        lateralDistance = constants.getDouble(LateralDistance.class);
        forwardOffset = constants.getDouble(ForwardOffset.class);
        encoderOverflow = constants.getBoolean(EncoderOverflow.class);
        gearRatio = constants.getDouble(GearRatio.class);
        ticksPerRev = constants.getDouble(TicksPerRev.class);
        wheelRadius = constants.getDouble(WheelRadius.class);

    }

    public double encoderTicksToInches(double ticks) {
        return getWheelRadius() * 2 * Math.PI * getGearRatio() * ticks / getTicksPerRev();
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
                encoderTicksToInches(leftEncoder.getCurrentPosition()),
                encoderTicksToInches(rightEncoder.getCurrentPosition()),
                encoderTicksToInches(frontEncoder.getCurrentPosition())
        );
    }

    @NonNull
    @Override
    public List<Double> getWheelVelocities() {
        // TODO: If your encoder velocity can exceed 32767 counts / second (such as the REV Through Bore and other
        //  competing magnetic encoders), change Encoder.getRawVelocity() to Encoder.getCorrectedVelocity() to enable a
        //  compensation method

        return Arrays.asList(
                encoderTicksToInches(leftEncoder.getCorrectedVelocity()),
                encoderTicksToInches(rightEncoder.getCorrectedVelocity()),
                encoderTicksToInches(frontEncoder.getCorrectedVelocity())
        );
    }

    public double getTicksPerRev(){
        return ticksPerRev;
    }

    public double getWheelRadius(){
        return wheelRadius;
    }

    public double getGearRatio(){return gearRatio;}
}
