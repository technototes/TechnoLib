package com.technototes.library.hardware2;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.function.UnaryOperator;

public class HardwareBuilder<T> {

    private static HardwareMap hardwareMap = null;
    public static HardwareMap getMap(){
        return hardwareMap;
    }
    public static void initMap(HardwareMap h){
        //fix to maintain old code
        com.technototes.library.hardware.HardwareDevice.hardwareMap = h;
        hardwareMap = h;
    }


    @SuppressWarnings("unchecked cast")
    public static <T> T create(String s){
        if(hardwareMap == null) return null;
        return hardwareMap.get((Class<T>) Object.class, s);
    }
    //TODO
    public static <T> T create(int port){
        if(hardwareMap == null) return null;
        return null;
    }



    protected T product;

    public HardwareBuilder(String name){
        this(HardwareBuilder.<T>create(name));
    }
    public HardwareBuilder(T device){
        product = device;
    }
    public HardwareBuilder(int port){
        this(HardwareBuilder.<T>create(port));
    }

    @SuppressWarnings("unchecked cast")
    public <U extends T> U build(){
        try {
            return (U) product;
        }catch (ClassCastException e){
            e.printStackTrace();
            return null;
        }
    }

    public HardwareBuilder<T> apply(UnaryOperator<T> operator){
        product = operator.apply(product);
        return this;
    }

    public static AnalogBuilder analog(String name){
        return new AnalogBuilder(name);
    }

    public static ColorBuilder color(String name){
        return new ColorBuilder(name);
    }

    public static ColorRangeBuilder colorRange(String name){
        return new ColorRangeBuilder(name);
    }

    public static CRServoBuilder crServo(String name){
        return new CRServoBuilder(name);
    }

    public static DigitalBuilder digital(String name){
        return new DigitalBuilder(name);
    }

    public static DistanceBuilder distance(String name){
        return new DistanceBuilder(name);
    }

    public static IMUBuilder imu(String name){
        return new IMUBuilder(name);
    }

    public static MotorBuilder motor(String name){
        return new MotorBuilder(name);
    }

    public static ServoBuilder servo(String name){
        return new ServoBuilder(name);
    }

    public static AnalogBuilder analog(int port){
        return new AnalogBuilder(port);
    }

    public static CRServoBuilder crServo(int port){
        return new CRServoBuilder(port);
    }

    public static DigitalBuilder digital(int port){
        return new DigitalBuilder(port);
    }

    public static MotorBuilder motor(int port){
        return new MotorBuilder(port);
    }

    public static ServoBuilder servo(int port){
        return new ServoBuilder(port);
    }
}
