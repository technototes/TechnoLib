package com.technototes.path.command;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.technototes.library.command.Command;
import com.technototes.path.subsystem.MecanumDrivebaseSubsystem;
import com.technototes.path.trajectorysequence.TrajectorySequence;
import com.technototes.path.trajectorysequence.TrajectorySequenceBuilder;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;


public class TrajectoryCommand implements Command {
    public Trajectory trajectory;
    public MecanumDrivebaseSubsystem subsystem;

    public TrajectoryCommand(MecanumDrivebaseSubsystem sub, Trajectory t) {
        addRequirements(sub);
        subsystem = sub;
        trajectory = t;
    }
    public TrajectoryCommand(MecanumDrivebaseSubsystem sub, Function<Function<Pose2d, TrajectoryBuilder>, Trajectory> t) {
        addRequirements(sub);
        subsystem = sub;
        trajectory = t.apply(sub::trajectoryBuilder);
    }
    public TrajectoryCommand(MecanumDrivebaseSubsystem sub, Supplier<Trajectory> t) {
        addRequirements(sub);
        subsystem = sub;
        trajectory = t.get();
    }
    public <T> TrajectoryCommand(MecanumDrivebaseSubsystem sub, BiFunction<Function<Pose2d, TrajectoryBuilder>, T, Trajectory> t, T mux) {
        addRequirements(sub);
        subsystem = sub;
        trajectory = t.apply(sub::trajectoryBuilder, mux);
    }
    public <T> TrajectoryCommand(MecanumDrivebaseSubsystem sub, Function<T, Trajectory> t, T mux) {
        addRequirements(sub);
        subsystem = sub;
        trajectory = t.apply(mux);
    }


    @Override
    public void initialize() {
        subsystem.followTrajectoryAsync(trajectory);
    }

    @Override
    public void execute() {
        subsystem.update();
    }

    @Override
    public boolean isFinished() {
        return !subsystem.isBusy();
    }

    @Override
    public void end(boolean cancel) {
        if(cancel) subsystem.stop();
    }
}
