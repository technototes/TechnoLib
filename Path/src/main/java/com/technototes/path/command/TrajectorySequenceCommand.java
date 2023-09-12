package com.technototes.path.command;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.technototes.library.command.Command;
import com.technototes.path.subsystem.PathingMecanumDrivebaseSubsystem;
import com.technototes.path.trajectorysequence.TrajectorySequence;
import com.technototes.path.trajectorysequence.TrajectorySequenceBuilder;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class TrajectorySequenceCommand implements Command {

    public TrajectorySequence trajectory;
    public PathingMecanumDrivebaseSubsystem subsystem;

    public TrajectorySequenceCommand(PathingMecanumDrivebaseSubsystem sub, TrajectorySequence t) {
        addRequirements(sub);
        subsystem = sub;
        trajectory = t;
    }

    public TrajectorySequenceCommand(
        PathingMecanumDrivebaseSubsystem sub,
        Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence> t
    ) {
        addRequirements(sub);
        subsystem = sub;
        trajectory = t.apply(sub::trajectorySequenceBuilder);
    }

    public TrajectorySequenceCommand(PathingMecanumDrivebaseSubsystem sub, Supplier<TrajectorySequence> t) {
        addRequirements(sub);
        subsystem = sub;
        trajectory = t.get();
    }

    public <T> TrajectorySequenceCommand(
        PathingMecanumDrivebaseSubsystem sub,
        BiFunction<Function<Pose2d, TrajectorySequenceBuilder>, T, TrajectorySequence> t,
        T mux
    ) {
        addRequirements(sub);
        subsystem = sub;
        trajectory = t.apply(sub::trajectorySequenceBuilder, mux);
    }

    public <T> TrajectorySequenceCommand(
        PathingMecanumDrivebaseSubsystem sub,
        Function<T, TrajectorySequence> t,
        T mux
    ) {
        addRequirements(sub);
        subsystem = sub;
        trajectory = t.apply(mux);
    }

    @Override
    public void initialize() {
        subsystem.followTrajectorySequenceAsync(trajectory);
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
        if (cancel) subsystem.stop();
    }
}
