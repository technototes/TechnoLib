package com.technototes.path.subsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.localization.Localizer;
import com.acmerobotics.roadrunner.util.Angle;
import com.technototes.library.hardware.sensor.IDistanceSensor;
import com.technototes.library.hardware.sensor.IGyro;
import com.technototes.library.hardware.sensor.IMU;
import com.technototes.library.hardware.sensor.Rev2MDistanceSensor;
import com.technototes.library.subsystem.Subsystem;
import com.technototes.library.util.MathUtils;
import com.technototes.path.geometry.ConfigurableVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DistanceSensorLocalizer implements Localizer, Subsystem {


    private Pose2d poseEstimate;

    private final double maxSensorDistance = 40;

    private final Map<IDistanceSensor, Pose2d> sensorMap;
    private final IGyro gyro;

    public DistanceSensorLocalizer(IGyro g, Map<IDistanceSensor, Pose2d> map){
        gyro = g;
        sensorMap = map;
        poseEstimate = new Pose2d();
    }


    @NonNull
    @Override
    public Pose2d getPoseEstimate() {
        return poseEstimate;
    }

    @Override
    public void setPoseEstimate(@NonNull Pose2d pose2d) {
        poseEstimate = pose2d;
    }

    @Nullable
    @Override
    public Pose2d getPoseVelocity() {
        return null;
    }

    @Override
    public void update() {
        update(null);

    }
    public void update(@Nullable Pose2d old){
        double heading = getHeading();
        double accumX = 0, accumY = 0;
        int totalX = 0, totalY = 0;
        for(Map.Entry<IDistanceSensor, Pose2d> entry : sensorMap.entrySet()){
            IDistanceSensor sensor = entry.getKey();
            Pose2d sensorPose = entry.getValue();
            double distance = sensor.getDistance();
            if(distance<maxSensorDistance && distance > 0.5) {
                sensorPose = new Pose2d(sensorPose.vec().rotated(heading), Angle.norm(sensorPose.getHeading() + heading));
                double change;
                switch (MathUtils.closestTo(2*sensorPose.getHeading()/Math.PI,  0, 1, 2, 3, 4)){
                    case 0: case 4:
                        change=71-sensorPose.getX()-Math.cos(sensorPose.getHeading())*distance;
                        if(old != null && Math.abs(old.getX()-change) > 10) break;
                        accumX+=change;
                        totalX++;
                        break;
                    case 1:
                        change=71-sensorPose.getY()-Math.sin(sensorPose.getHeading())*distance;
                        if(old != null && Math.abs(old.getY()-change) > 10) break;
                        accumY+=change;
                        totalY++;
                        break;
                    case 2:
                        change=71+sensorPose.getX()+Math.cos(sensorPose.getHeading())*distance;
                        if(old != null && Math.abs(old.getX()+change) > 10) break;
                        accumX-=change;
                        totalX++;
                        break;
                    case 3:
                        change=71+sensorPose.getY()+Math.sin(sensorPose.getHeading())*distance;
                        if(old != null && Math.abs(old.getY()+change) > 10) break;
                        accumY-=change;
                        totalY++;
                        break;

                }
            }
        }
        if(old == null) old = new Pose2d();
        poseEstimate = new Pose2d(totalX != 0 ? accumX/totalX : old.getX(), totalY != 0 ? accumY/totalY : old.getY() , heading);
    }

    public double gyroOffset = 0;

    public void setGyroOffset(double v){
        gyroOffset = v;
    }

    public double getHeading(){
        return Angle.norm(gyro.gyroHeadingInRadians()-gyroOffset);
    }

}
