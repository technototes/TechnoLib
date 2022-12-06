package com.technototes.library.hardware2;

import com.qualcomm.robotcore.hardware.HardwareMap;
import java.util.function.UnaryOperator;

/**
 * TODO: Remove this. I don't believe this adds much value. Yeah, HardwareMap.get is weird, but
 * it's in all the documentation, so just read it, see it in the examples, and you're done.
 */
public class HardwareBuilder<T> {

    private static HardwareMap hardwareMap = null;

    public static HardwareMap getMap() {
        return hardwareMap;
    }

    /**
     * Deprecated
     *
     * @param h deprecated
     */
    public static void initMap(HardwareMap h) {
        // fix to maintain old code
        com.technototes.library.hardware.HardwareDevice.hardwareMap = h;
        hardwareMap = h;
    }

    /**
     * Deprecated
     *
     * @param s   Deprecated
     * @param <T> Deprecated
     * @return Deprecated
     */
    @SuppressWarnings("unchecked cast")
    public static <T> T create(String s) {
        if (hardwareMap == null) return null;
        return hardwareMap.get((Class<T>) Object.class, s);
    }

    /**
     * Deprecated
     *
     * @param port Deprecated
     * @param <T>  Deprecated
     * @return Deprecated
     */
    // TODO
    public static <T> T create(int port) {
        if (hardwareMap == null) return null;
        return null;
    }

    /**
     * Deprecated
     */
    protected T product;

    /**
     * Deprecated
     *
     * @param name Deprecated
     */
    public HardwareBuilder(String name) {
        this(HardwareBuilder.<T>create(name));
    }

    /**
     * Deprecated
     *
     * @param device Deprecated
     */
    public HardwareBuilder(T device) {
        product = device;
    }

    /**
     * Deprecated
     *
     * @param port Deprecated
     */
    public HardwareBuilder(int port) {
        this(HardwareBuilder.<T>create(port));
    }

    /**
     * Deprecated
     *
     * @param <U> Deprecated
     * @return Deprecated
     */
    @SuppressWarnings("unchecked cast")
    public <U extends T> U build() {
        try {
            return (U) product;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deprecated
     *
     * @param operator Deprecated
     * @return Deprecated
     */
    public HardwareBuilder<T> apply(UnaryOperator<T> operator) {
        product = operator.apply(product);
        return this;
    }

    /**
     * Deprecated
     *
     * @param name Deprecated
     * @return Deprecated
     */
    public static AnalogBuilder analog(String name) {
        return new AnalogBuilder(name);
    }

    /**
     * Deprecated
     *
     * @param name Deprecated
     * @return Deprecated
     */
    public static ColorBuilder color(String name) {
        return new ColorBuilder(name);
    }

    /**
     * Deprecated
     *
     * @param name Deprecated
     * @return Deprecated
     */
    public static ColorRangeBuilder colorRange(String name) {
        return new ColorRangeBuilder(name);
    }

    /**
     * Deprecated
     *
     * @param name Deprecated
     * @return Deprecated
     */
    public static CRServoBuilder crServo(String name) {
        return new CRServoBuilder(name);
    }

    /**
     * Deprecated
     *
     * @param name Deprecated
     * @return Deprecated
     */
    public static DigitalBuilder digital(String name) {
        return new DigitalBuilder(name);
    }

    /**
     * Deprecated
     *
     * @param name Deprecated
     * @return Deprecated
     */
    public static DistanceBuilder distance(String name) {
        return new DistanceBuilder(name);
    }

    /**
     * Deprecated
     *
     * @param name Deprecated
     * @return Deprecated
     */
    public static IMUBuilder imu(String name) {
        return new IMUBuilder(name);
    }

    /**
     * Deprecated
     *
     * @param name Deprecated
     * @return Deprecated
     */
    public static MotorBuilder motor(String name) {
        return new MotorBuilder(name);
    }

    /**
     * Deprecated
     *
     * @param name Deprecated
     * @return Deprecated
     */
    public static ServoBuilder servo(String name) {
        return new ServoBuilder(name);
    }

    /**
     * Deprecated
     *
     * @param port Deprecated
     * @return Deprecated
     */
    public static AnalogBuilder analog(int port) {
        return new AnalogBuilder(port);
    }

    /**
     * Deprecated
     *
     * @param port Deprecated
     * @return Deprecated
     */
    public static CRServoBuilder crServo(int port) {
        return new CRServoBuilder(port);
    }

    /**
     * Deprecated
     *
     * @param port Deprecated
     * @return Deprecated
     */
    public static DigitalBuilder digital(int port) {
        return new DigitalBuilder(port);
    }

    /**
     * Deprecated
     *
     * @param port Deprecated
     * @return Deprecated
     */
    public static MotorBuilder motor(int port) {
        return new MotorBuilder(port);
    }

    /**
     * Deprecated
     *
     * @param port Deprecated
     * @return Deprecated
     */
    public static ServoBuilder servo(int port) {
        return new ServoBuilder(port);
    }
}
