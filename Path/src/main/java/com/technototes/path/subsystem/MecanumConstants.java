package com.technototes.path.subsystem;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@SuppressWarnings("unused")
@FunctionalInterface
public interface MecanumConstants {

    Class getConstant();

    default double getDouble(Class<? extends Annotation> a) {
        for (Field f : getConstant().getFields()) {
            try {
                if (f.isAnnotationPresent(a)){
                    System.out.println(a.toString()+" as "+f.getDouble(null));
                    return f.getDouble(null);
                }
            }catch(IllegalAccessException e){
                System.err.println(f.getType() + " is inaccessible for some reason");
            }
        }
        return 0;
    }

    default int getInt(Class<? extends Annotation> a) {
        for (Field f : getConstant().getFields()) {
            try {
                if (f.isAnnotationPresent(a)){
                    System.out.println(a.toString()+" as "+f.getInt(null));
                    return f.getInt(null);
                }
            }catch(IllegalAccessException e){
                System.err.println(f.getType() + " is inaccessible for some reason");
            }
        }
        return 0;
    }

    default boolean getBoolean(Class<? extends Annotation> a) {
        for (Field f : getConstant().getFields()) {
            try {
                if (f.isAnnotationPresent(a)){
                    System.out.println(a.toString()+" as "+f.getBoolean(null));
                    return f.getBoolean(null);
                }
            }catch(IllegalAccessException e){
                System.err.println(f.getType() + " is inaccessible for some reason");
            }
        }
        return false;
    }
    default PIDCoefficients getPID(Class<? extends Annotation> a) {
        for (Field f : getConstant().getFields()) {
            try {
                if (f.isAnnotationPresent(a)){
                    System.out.println(a.toString()+" as "+f.get(null));
                    return (PIDCoefficients) f.get(null);
                }
            }catch(IllegalAccessException e){
                System.err.println(f.getType() + " is inaccessible for some reason");
            }
        }
        return null;
    }

    default PIDFCoefficients getPIDF(Class<? extends Annotation> a) {
        for (Field f : getConstant().getFields()) {
            try {
                if (f.isAnnotationPresent(a)){
                    System.out.println(a.toString()+" as "+f.get(null));
                    return (PIDFCoefficients) f.get(null);
                }
            }catch(IllegalAccessException e){
                System.err.println(f.getType() + " is inaccessible for some reason");
            }
        }
        return null;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface TicksPerRev{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface MaxRPM{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface UseDriveEncoder {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface MotorVeloPID {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface WheelRadius{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface GearRatio{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface TrackWidth{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface KV{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface KA{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface KStatic{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface MaxVelo{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface MaxAccel{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface MaxAngleVelo {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface MaxAngleAccel {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface TransPID{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface HeadPID{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface LateralMult{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface VXWeight{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface VYWeight{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface OmegaWeight{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface PoseLimit {}


    static double encoderTicksToInches(double ticks, double rad, double rat, double rev) {
        return rad * 2 * Math.PI * rat * ticks / rev;
    }

    static double rpmToVelocity(double rpm, double rad, double rat) {
        return rpm * rat * 2 * Math.PI * rad / 60.0;
    }

    static double getMotorVelocityF(double ticksPerSecond) {
        return 32767 / ticksPerSecond;
    }

}
