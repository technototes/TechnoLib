package com.technototes.library.util;

import static java.lang.annotation.ElementType.TYPE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;
import java.util.function.Supplier;

public enum Alliance {
    RED(Color.RED), BLUE(Color.BLUE), NONE(Color.BLACK);
    Color color;
    Alliance(Color c) {
        color = c;
    }
    public Color getColor(){
        return color;
    }

    public <T> T selectOf(T a, T b){
        return Selector.selectOf(this, a, b);
    }



    public static class Selector<T>{
        private final T r, b;
        protected Selector(T red, T blue){
            r = red;
            b = blue;
        }
        public T select(Alliance a){
            return a == RED ? r : b;
        }
        public static <T> Selector<T> of(T red, T blue){
            return new Selector<>(red, blue);
        }

        public static <T> T selectOf(Alliance alliance, T red, T blue){
            return Selector.of(red, blue).select(alliance);
        }
    }
    public static Alliance get(Class<? extends OpMode> c){
        if(c.isAnnotationPresent(Red.class)) return RED;
        if(c.isAnnotationPresent(Blue.class)) return BLUE;
        return NONE;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(TYPE)
    public @interface Red{}
    @Retention(RetentionPolicy.RUNTIME)
    @Target(TYPE)
    public @interface Blue{}

}
