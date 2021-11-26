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
public interface DeadWheelConstants {

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

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface LateralDistance{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface ForwardOffset{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface EncoderOverflow{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface GearRatio{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface TicksPerRev{}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface WheelRadius{}

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
