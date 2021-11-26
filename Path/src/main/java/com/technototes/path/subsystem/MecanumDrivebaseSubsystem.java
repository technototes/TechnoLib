package com.technototes.path.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.drive.DriveSignal;
import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.acmerobotics.roadrunner.followers.HolonomicPIDVAFollower;
import com.acmerobotics.roadrunner.followers.TrajectoryFollower;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.Localizer;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.technototes.library.hardware.HardwareDevice;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.sensor.IMU;
import com.technototes.library.subsystem.Subsystem;
import com.technototes.path.subsystem.MecanumConstants.GearRatio;
import com.technototes.path.subsystem.MecanumConstants.HeadPID;
import com.technototes.path.subsystem.MecanumConstants.KA;
import com.technototes.path.subsystem.MecanumConstants.KStatic;
import com.technototes.path.subsystem.MecanumConstants.KV;
import com.technototes.path.subsystem.MecanumConstants.LateralMult;
import com.technototes.path.subsystem.MecanumConstants.MaxAccel;
import com.technototes.path.subsystem.MecanumConstants.MaxAngleAccel;
import com.technototes.path.subsystem.MecanumConstants.MaxAngleVelo;
import com.technototes.path.subsystem.MecanumConstants.MaxRPM;
import com.technototes.path.subsystem.MecanumConstants.MaxVelo;
import com.technototes.path.subsystem.MecanumConstants.MotorVeloPID;
import com.technototes.path.subsystem.MecanumConstants.OmegaWeight;
import com.technototes.path.subsystem.MecanumConstants.PoseLimit;
import com.technototes.path.subsystem.MecanumConstants.TicksPerRev;
import com.technototes.path.subsystem.MecanumConstants.TrackWidth;
import com.technototes.path.subsystem.MecanumConstants.TransPID;
import com.technototes.path.subsystem.MecanumConstants.UseDriveEncoder;
import com.technototes.path.subsystem.MecanumConstants.VXWeight;
import com.technototes.path.subsystem.MecanumConstants.VYWeight;
import com.technototes.path.subsystem.MecanumConstants.WheelRadius;
import com.technototes.path.trajectorysequence.TrajectorySequence;
import com.technototes.path.trajectorysequence.TrajectorySequenceBuilder;
import com.technototes.path.trajectorysequence.TrajectorySequenceRunner;
import com.technototes.path.util.LynxModuleUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class MecanumDrivebaseSubsystem extends MecanumDrive implements Subsystem {
    public double speed = 1;

    public enum Mode {
        IDLE,
        TURN,
        FOLLOW_TRAJECTORY
    }

    public final double TICKS_PER_REV, MAX_RPM;

    public final boolean RUN_USING_ENCODER;

    public final PIDFCoefficients MOTOR_VELO_PID;

    public final double WHEEL_RADIUS, GEAR_RATIO, TRACK_WIDTH, kV, kA, kStatic;

    public final double MAX_VEL, MAX_ACCEL, MAX_ANG_VEL, MAX_ANG_ACCEL;

    public final PIDCoefficients TRANSLATIONAL_PID, HEADING_PID;

    public final double LATERAL_MULTIPLIER, VX_WEIGHT, VY_WEIGHT, OMEGA_WEIGHT;

    public final int POSE_HISTORY_LIMIT;

    private final TrajectorySequenceRunner trajectorySequenceRunner;

    private static TrajectoryVelocityConstraint VEL_CONSTRAINT;
    private static TrajectoryAccelerationConstraint ACCEL_CONSTRAINT;

    private final TrajectoryFollower follower;


    protected DcMotorEx leftFront, leftRear, rightRear, rightFront;
    protected List<DcMotorEx> motors;
    protected IMU imu;

    protected VoltageSensor batteryVoltageSensor;


    public MecanumDrivebaseSubsystem(EncodedMotor<DcMotorEx> fl, EncodedMotor<DcMotorEx> fr,
                                     EncodedMotor<DcMotorEx> rl, EncodedMotor<DcMotorEx> rr,
                                     IMU i, MecanumConstants c) {
        this(fl, fr, rl, rr, i, c, null);
    }

    public MecanumDrivebaseSubsystem(EncodedMotor<DcMotorEx> fl, EncodedMotor<DcMotorEx> fr,
                                     EncodedMotor<DcMotorEx> rl, EncodedMotor<DcMotorEx> rr,
                                     IMU i, MecanumConstants c, Localizer localizer) {
        super(c.getDouble(KV.class), c.getDouble(KA.class), c.getDouble(KStatic.class), c.getDouble(TrackWidth.class), c.getDouble(LateralMult.class));
        leftFront = fl.getDevice();
        leftRear = rl.getDevice();
        rightRear = rr.getDevice();
        rightFront = fr.getDevice();

        motors = Arrays.asList(leftFront, leftRear, rightRear, rightFront);


        TICKS_PER_REV = c.getDouble(TicksPerRev.class);
        MAX_RPM = c.getDouble(MaxRPM.class);

        RUN_USING_ENCODER = c.getBoolean(UseDriveEncoder.class);

        MOTOR_VELO_PID = c.getPIDF(MotorVeloPID.class);

        WHEEL_RADIUS = c.getDouble(WheelRadius.class);
        GEAR_RATIO = c.getDouble(GearRatio.class);
        TRACK_WIDTH = c.getDouble(TrackWidth.class);
        kV = c.getDouble(KV.class);
        kA = c.getDouble(KA.class);
        kStatic = c.getDouble(KStatic.class);

        MAX_VEL = c.getDouble(MaxVelo.class);
        MAX_ACCEL = c.getDouble(MaxAccel.class);
        MAX_ANG_VEL = c.getDouble(MaxAngleVelo.class);
        MAX_ANG_ACCEL = c.getDouble(MaxAngleAccel.class);

        TRANSLATIONAL_PID = c.getPID(TransPID.class);
        HEADING_PID = c.getPID(HeadPID.class);

        LATERAL_MULTIPLIER = c.getDouble(LateralMult.class);
        VX_WEIGHT = c.getDouble(VXWeight.class);
        VY_WEIGHT = c.getDouble(VYWeight.class);
        OMEGA_WEIGHT = c.getDouble(OmegaWeight.class);

        POSE_HISTORY_LIMIT = c.getInt(PoseLimit.class);

        VEL_CONSTRAINT = getVelocityConstraint(MAX_VEL, MAX_ANG_VEL, TRACK_WIDTH);
        ACCEL_CONSTRAINT = getAccelerationConstraint(MAX_ACCEL);

        follower = new HolonomicPIDVAFollower(TRANSLATIONAL_PID, TRANSLATIONAL_PID, HEADING_PID,
                new Pose2d(0.5, 0.5, Math.toRadians(5.0)), 0.5);

        LynxModuleUtil.ensureMinimumFirmwareVersion(HardwareDevice.hardwareMap);

        batteryVoltageSensor = HardwareDevice.hardwareMap.voltageSensor.iterator().next();

        for (LynxModule module : HardwareDevice.hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        // TODO: adjust the names of the following hardware devices to match your configuration
        imu = i.radians().initialize();

        // TODO: if your hub is mounted vertically, remap the IMU axes so that the z-axis points
        // upward (normal to the floor) using a command like the following:
        // BNO055IMUUtil.remapAxes(imu, AxesOrder.XYZ, AxesSigns.NPN);


        for (DcMotorEx motor : motors) {
            MotorConfigurationType motorConfigurationType = motor.getMotorType().clone();
            motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
            motor.setMotorType(motorConfigurationType);
        }

        if (RUN_USING_ENCODER) {
            setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if (RUN_USING_ENCODER && MOTOR_VELO_PID != null) {
            setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, MOTOR_VELO_PID);
        }

        // TODO: reverse any motors using DcMotor.setDirection()

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);

        // TODO: if desired, use setLocalizer() to change the localization method
        if(localizer != null) setLocalizer(localizer);

        trajectorySequenceRunner = new TrajectorySequenceRunner(follower, HEADING_PID);

    }

    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose) {
        return new TrajectoryBuilder(startPose, VEL_CONSTRAINT, ACCEL_CONSTRAINT);
    }

    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose, boolean reversed) {
        return new TrajectoryBuilder(startPose, reversed, VEL_CONSTRAINT, ACCEL_CONSTRAINT);
    }

    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose, double startHeading) {
        return new TrajectoryBuilder(startPose, startHeading, VEL_CONSTRAINT, ACCEL_CONSTRAINT);
    }

    public TrajectorySequenceBuilder trajectorySequenceBuilder(Pose2d startPose) {
        return new TrajectorySequenceBuilder(
                startPose,
                VEL_CONSTRAINT, ACCEL_CONSTRAINT,
                MAX_ANG_VEL, MAX_ANG_ACCEL
        );
    }

    public TrajectorySequenceBuilder trajectorySequenceBuilder() {
        return trajectorySequenceBuilder(getPoseEstimate());
    }

    public void turnAsync(double angle) {
        trajectorySequenceRunner.followTrajectorySequenceAsync(
                trajectorySequenceBuilder(getPoseEstimate())
                        .turn(angle)
                        .build()
        );
    }

    public void turn(double angle) {
        turnAsync(angle);
        waitForIdle();
    }

    public void followTrajectoryAsync(Trajectory trajectory) {
        if (trajectory == null) trajectorySequenceRunner.followTrajectorySequenceAsync(null);
        else trajectorySequenceRunner.followTrajectorySequenceAsync(
                trajectorySequenceBuilder(trajectory.start())
                        .addTrajectory(trajectory)
                        .build()
        );
    }

    public void followTrajectory(Trajectory trajectory) {
        followTrajectoryAsync(trajectory);
        waitForIdle();
    }

    public void followTrajectorySequenceAsync(TrajectorySequence trajectorySequence) {
        trajectorySequenceRunner.followTrajectorySequenceAsync(trajectorySequence);
    }

    public void followTrajectorySequence(TrajectorySequence trajectorySequence) {
        followTrajectorySequenceAsync(trajectorySequence);
        waitForIdle();
    }

    public Pose2d getLastError() {
        return trajectorySequenceRunner.getLastPoseError();
    }

    public void update() {
        updatePoseEstimate();
        DriveSignal signal = trajectorySequenceRunner.update(getPoseEstimate(), getPoseVelocity());
        if (signal != null) setDriveSignal(signal);
    }

    public void stop() {
        followTrajectorySequenceAsync(null);
        followTrajectoryAsync(null);
        setDriveSignal(new DriveSignal());
    }

    public void waitForIdle() {
        while (!Thread.currentThread().isInterrupted() && isBusy())
            update();
    }

    public boolean isBusy() {
        return trajectorySequenceRunner.isBusy();
    }

    public void setMode(DcMotor.RunMode runMode) {
        for (DcMotorEx motor : motors) {
            motor.setMode(runMode);
        }
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(zeroPowerBehavior);
        }
    }

    public void setPIDFCoefficients(DcMotor.RunMode runMode, PIDFCoefficients coefficients) {
        PIDFCoefficients compensatedCoefficients = new PIDFCoefficients(
                coefficients.p, coefficients.i, coefficients.d,
                coefficients.f * 12 / batteryVoltageSensor.getVoltage()
        );

        for (DcMotorEx motor : motors) {
            motor.setPIDFCoefficients(runMode, compensatedCoefficients);
        }
    }

    public void setWeightedDrivePower(Pose2d drivePower) {
        Pose2d vel = drivePower;

        if (Math.abs(drivePower.getX()) + Math.abs(drivePower.getY())
                + Math.abs(drivePower.getHeading()) > 1) {
            // re-normalize the powers according to the weights
            double denom = VX_WEIGHT * Math.abs(drivePower.getX())
                    + VY_WEIGHT * Math.abs(drivePower.getY())
                    + OMEGA_WEIGHT * Math.abs(drivePower.getHeading());

            vel = new Pose2d(
                    VX_WEIGHT * drivePower.getX(),
                    VY_WEIGHT * drivePower.getY(),
                    OMEGA_WEIGHT * drivePower.getHeading()
            ).div(denom);
        }

        setDrivePower(vel);
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        List<Double> wheelPositions = new ArrayList<>();
        for (DcMotorEx motor : motors) {
            wheelPositions.add(MecanumConstants.encoderTicksToInches(motor.getCurrentPosition(), WHEEL_RADIUS, GEAR_RATIO, TICKS_PER_REV));
        }
        return wheelPositions;
    }

    @Override
    public List<Double> getWheelVelocities() {
        List<Double> wheelVelocities = new ArrayList<>();
        for (DcMotorEx motor : motors) {
            wheelVelocities.add(MecanumConstants.encoderTicksToInches(motor.getVelocity(), WHEEL_RADIUS, GEAR_RATIO, TICKS_PER_REV));
        }
        return wheelVelocities;
    }

    @Override
    public void setMotorPowers(double v, double v1, double v2, double v3) {
        leftFront.setPower(v);
        leftRear.setPower(v1);
        rightRear.setPower(v2);
        rightFront.setPower(v3);
    }

    @Override
    public double getRawExternalHeading() {
        return imu.getAngularOrientation().firstAngle;
    }

    @Override
    public Double getExternalHeadingVelocity() {
        // TODO: This must be changed to match your configuration
        //                           | Z axis
        //                           |
        //     (Motor Port Side)     |   / X axis
        //                       ____|__/____
        //          Y axis     / *   | /    /|   (IO Side)
        //          _________ /______|/    //      I2C
        //                   /___________ //     Digital
        //                  |____________|/      Analog
        //
        //                 (Servo Port Side)
        //
        // The positive x axis points toward the USB port(s)
        //
        // Adjust the axis rotation rate as necessary
        // Rotate about the z axis is the default assuming your REV Hub/Control Hub is laying
        // flat on a surface

        return (double) imu.getAngularVelocity().zRotationRate;
    }

    public static TrajectoryVelocityConstraint getVelocityConstraint(double maxVel, double maxAngularVel, double trackWidth) {
        return new MinVelocityConstraint(Arrays.asList(
                new AngularVelocityConstraint(maxAngularVel),
                new MecanumVelocityConstraint(maxVel, trackWidth)
        ));
    }

    public static TrajectoryAccelerationConstraint getAccelerationConstraint(double maxAccel) {
        return new ProfileAccelerationConstraint(maxAccel);
    }

    public void zeroExternalHeading(){
        setExternalHeading(0);
    }
}
