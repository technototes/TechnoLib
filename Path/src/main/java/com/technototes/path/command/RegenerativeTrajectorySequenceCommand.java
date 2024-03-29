package com.technototes.path.command;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.technototes.path.subsystem.PathingMecanumDrivebaseSubsystem;
import com.technototes.path.trajectorysequence.TrajectorySequence;
import com.technototes.path.trajectorysequence.TrajectorySequenceBuilder;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class RegenerativeTrajectorySequenceCommand extends TrajectorySequenceCommand {

    public Supplier<TrajectorySequence> trajFunc;

    public RegenerativeTrajectorySequenceCommand(
        PathingMecanumDrivebaseSubsystem sub,
        Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence> t
    ) {
        super(sub, t);
        trajFunc = () -> t.apply(sub::trajectorySequenceBuilder);
    }

    public RegenerativeTrajectorySequenceCommand(PathingMecanumDrivebaseSubsystem sub, Supplier<TrajectorySequence> t) {
        super(sub, t);
        trajFunc = t;
    }

    public <T> RegenerativeTrajectorySequenceCommand(
        PathingMecanumDrivebaseSubsystem sub,
        BiFunction<Function<Pose2d, TrajectorySequenceBuilder>, T, TrajectorySequence> t,
        T mux
    ) {
        super(sub, t, mux);
        trajFunc = () -> t.apply(sub::trajectorySequenceBuilder, mux);
    }

    public <T> RegenerativeTrajectorySequenceCommand(
        PathingMecanumDrivebaseSubsystem sub,
        Function<T, TrajectorySequence> t,
        T mux
    ) {
        super(sub, t, mux);
        trajFunc = () -> t.apply(mux);
    }

    @Override
    public void initialize() {
        subsystem.followTrajectorySequenceAsync(trajFunc.get());
    }
}
