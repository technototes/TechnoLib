package com.technototes.path.subsystem;

import static com.technototes.path.subsystem.DeadWheelConstants.EncoderOverflow;
import static com.technototes.path.subsystem.DeadWheelConstants.ForwardOffset;
import static com.technototes.path.subsystem.DeadWheelConstants.GearRatio;
import static com.technototes.path.subsystem.DeadWheelConstants.LateralDistance;
import static com.technototes.path.subsystem.DeadWheelConstants.TicksPerRev;
import static com.technototes.path.subsystem.DeadWheelConstants.WheelRadius;

import androidx.annotation.NonNull;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.acmerobotics.roadrunner.localization.TwoTrackingWheelLocalizer;
import com.technototes.library.hardware.sensor.IMU;
import com.technototes.library.hardware.sensor.encoder.MotorEncoder;
import com.technototes.library.subsystem.Subsystem;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleSupplier;

/*
 * Sample tracking wheel localizer implementation assuming a standard configuration:
 *
 *    /---------------\
 *    |      ____     |
 *    |      ----     |
 *    | ||      ^     |
 *    | ||<- lr |     |
 *    |         fb    |
 *    |               |
 *    \---------------/
 *
 * COMPLETELY UNTESTED!!
 */
public class TwoDeadWheelLocalizer extends TwoTrackingWheelLocalizer implements Subsystem {

    protected MotorEncoder leftrightEncoder, frontbackEncoder;
    protected DoubleSupplier headingSupplier;
    protected double lateralDistance, forwardOffset, gearRatio, wheelRadius, ticksPerRev;

    protected boolean encoderOverflow;

    public TwoDeadWheelLocalizer(IMU imu, MotorEncoder lr, MotorEncoder fb, DeadWheelConstants constants) {
        super(
            Arrays.asList(
                new Pose2d(0, constants.getDouble(LateralDistance.class), 0), // left
                new Pose2d(constants.getDouble(ForwardOffset.class), 0, Math.toRadians(90)) // front
            )
        );
        leftrightEncoder = lr;
        frontbackEncoder = fb;
        headingSupplier = () -> imu.gyroHeading();

        lateralDistance = constants.getDouble(LateralDistance.class);
        forwardOffset = constants.getDouble(ForwardOffset.class);
        encoderOverflow = constants.getBoolean(EncoderOverflow.class);
        gearRatio = constants.getDouble(GearRatio.class);
        ticksPerRev = constants.getDouble(TicksPerRev.class);
        wheelRadius = constants.getDouble(WheelRadius.class);
    }

    public double encoderTicksToInches(double ticks) {
        return ((getWheelRadius() * 2 * Math.PI * getGearRatio() * ticks) / getTicksPerRev());
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
            encoderTicksToInches(leftrightEncoder.getCurrentPosition()),
            encoderTicksToInches(frontbackEncoder.getCurrentPosition())
        );
    }

    @NonNull
    @Override
    public List<Double> getWheelVelocities() {
        // TODO: If your encoder velocity can exceed 32767 counts / second (such as the REV Through Bore and other
        //  competing magnetic encoders), change Encoder.getRawVelocity() to Encoder.getCorrectedVelocity() to enable a
        //  compensation method

        return Arrays.asList(
            encoderTicksToInches(leftrightEncoder.getCorrectedVelocity()),
            encoderTicksToInches(frontbackEncoder.getCorrectedVelocity())
        );
    }

    public double getTicksPerRev() {
        return ticksPerRev;
    }

    public double getWheelRadius() {
        return wheelRadius;
    }

    public double getGearRatio() {
        return gearRatio;
    }

    @Override
    public double getHeading() {
        return headingSupplier.getAsDouble();
    }
}
