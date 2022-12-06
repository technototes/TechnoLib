package com.technototes.path.geometry;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public class ConfigurableVector {

    public double x, y;

    public ConfigurableVector(Vector2d pose) {
        x = pose.getX();
        y = pose.getY();
    }

    public ConfigurableVector(double x, double y) {
        this(new Vector2d(x, y));
    }

    public ConfigurableVector() {
        this(new Vector2d());
    }

    public ConfigurableVector set(double newX, double newY) {
        return set(new Vector2d(newX, newY));
    }

    public ConfigurableVector set(Vector2d vec) {
        x = vec.getX();
        y = vec.getY();
        return this;
    }

    public ConfigurableVector setX(double val) {
        x = val;
        return this;
    }

    public ConfigurableVector setY(double val) {
        y = val;
        return this;
    }

    public ConfigurableVector mutateVec(UnaryOperator<Vector2d> callback) {
        return set(mapVec(callback));
    }

    public ConfigurableVector mutateX(UnaryOperator<Double> callback) {
        return setX(mapX(callback));
    }

    public ConfigurableVector mutateY(UnaryOperator<Double> callback) {
        return setY(mapY(callback));
    }

    public <T> T mapVec(Function<Vector2d, T> callback) {
        return callback.apply(toVec());
    }

    public <T> T mapX(Function<Double, T> callback) {
        return callback.apply(getX());
    }

    public <T> T mapY(Function<Double, T> callback) {
        return callback.apply(getY());
    }

    public Vector2d toVec() {
        return new Vector2d(getX(), getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
