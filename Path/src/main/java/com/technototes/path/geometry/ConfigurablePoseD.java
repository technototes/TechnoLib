package com.technototes.path.geometry;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ConfigurablePoseD extends ConfigurableVector {

    public double heading;

    public ConfigurablePoseD(Pose2d pose) {
        super(pose.vec());
        heading = Math.toDegrees(pose.getHeading());
    }

    public ConfigurablePoseD(double x, double y, double headingDegrees) {
        this(new Pose2d(x, y, Math.toRadians(headingDegrees)));
    }

    public ConfigurablePoseD(double x, double y) {
        this(new Pose2d(x, y));
    }

    public ConfigurablePoseD(Vector2d vec) {
        this(vec.getX(), vec.getY());
    }

    public ConfigurablePoseD(Vector2d vec, double headingDegrees) {
        this(vec.getX(), vec.getY(), headingDegrees);
    }

    public ConfigurablePoseD() {
        this(new Pose2d());
    }

    @Override
    public ConfigurablePoseD set(double newX, double newY) {
        return (ConfigurablePoseD) super.set(newX, newY);
    }

    @Override
    public ConfigurablePoseD set(Vector2d vec) {
        return (ConfigurablePoseD) super.set(vec);
    }

    @Override
    public ConfigurablePoseD setX(double val) {
        return (ConfigurablePoseD) super.setX(val);
    }

    @Override
    public ConfigurablePoseD setY(double val) {
        return (ConfigurablePoseD) super.setY(val);
    }

    @Override
    public ConfigurablePoseD mutateVec(UnaryOperator<Vector2d> callback) {
        return (ConfigurablePoseD) super.mutateVec(callback);
    }

    @Override
    public ConfigurablePoseD mutateX(UnaryOperator<Double> callback) {
        return (ConfigurablePoseD) super.mutateX(callback);
    }

    @Override
    public ConfigurablePoseD mutateY(UnaryOperator<Double> callback) {
        return (ConfigurablePoseD) super.mutateY(callback);
    }

    public ConfigurablePoseD set(double newX, double newY, double newHeadingDegrees) {
        return set(new Pose2d(newX, newY, Math.toRadians(newHeadingDegrees)));
    }

    public ConfigurablePoseD set(Pose2d pose) {
        set(pose.vec());
        heading = Math.toDegrees(pose.getHeading());
        return this;
    }

    public ConfigurablePoseD set(Vector2d vec, double newHeadingDegrees) {
        set(vec);
        heading = newHeadingDegrees;
        return this;
    }

    public ConfigurablePoseD setHeading(double val) {
        heading = val;
        return this;
    }

    public ConfigurablePoseD mutatePose(UnaryOperator<Pose2d> callback) {
        return set(mapPose(callback));
    }

    public ConfigurablePoseD mutateHeading(UnaryOperator<Double> callback) {
        return setHeading(mapHeading(callback));
    }

    public <T> T mapPose(Function<Pose2d, T> callback) {
        return callback.apply(toPose());
    }

    private <T> T mapHeading(Function<Double, T> callback) {
        return callback.apply(getHeading());
    }

    public Pose2d toPose() {
        return new Pose2d(x, y, Math.toRadians(heading));
    }

    public double getHeading() {
        return heading;
    }

    public double getRadians() {
        return Math.toRadians(heading);
    }

    public static Pose2d mirrorOverX(Pose2d old) {
        return new Pose2d(old.getX(), old.times(-1).getY(), old.times(-1).getHeading());
    }

    public static Pose2d mirrorOverY(Pose2d old) {
        return new Pose2d(
            old.times(-1).getX(),
            old.getY(),
            old.headingVec().rotated(-Math.PI / 2).times(-1).rotated(Math.PI / 2).angle()
        );
    }
}
