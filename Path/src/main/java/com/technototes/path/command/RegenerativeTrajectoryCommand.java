package com.technototes.path.command;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.technototes.path.subsystem.MecanumDrivebaseSubsystem;
import com.technototes.path.trajectorysequence.TrajectorySequence;
import com.technototes.path.trajectorysequence.TrajectorySequenceBuilder;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;


public class RegenerativeTrajectoryCommand extends TrajectoryCommand {
    public Supplier<Trajectory> trajFunc;
    public RegenerativeTrajectoryCommand(MecanumDrivebaseSubsystem sub, Function<Function<Pose2d, TrajectoryBuilder>, Trajectory> t) {
        super(sub, t);
        trajFunc = ()->t.apply(sub::trajectoryBuilder);
    }

   public RegenerativeTrajectoryCommand(MecanumDrivebaseSubsystem sub, Supplier<Trajectory> t){
        super(sub, t);
        trajFunc = t;
   }

    public <T> RegenerativeTrajectoryCommand(MecanumDrivebaseSubsystem sub, BiFunction<Function<Pose2d, TrajectoryBuilder>, T, Trajectory> t, T mux) {
        super(sub, t, mux);
        trajFunc = ()-> t.apply(sub::trajectoryBuilder, mux);
    }

    public <T> RegenerativeTrajectoryCommand(MecanumDrivebaseSubsystem sub, Function<T, Trajectory> t, T mux) {
        super(sub, t, mux);
        trajFunc = ()-> t.apply(mux);
    }


    @Override
    public void initialize() {
        subsystem.followTrajectoryAsync(trajFunc.get());
    }

}
