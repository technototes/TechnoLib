package com.technototes.library.util;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * An enumeration to specify which alliance the bot is on (Red, Blue, or None)
 */
public enum Alliance {
    RED(Color.RED),
    BLUE(Color.BLUE),
    NONE(Color.BLACK);
    Color color;

    /**
     * Simple constructor from a Color
     *
     * @param c
     */
    Alliance(Color c) {
        color = c;
    }

    /**
     * Get the alliance color (Red, Blue, or Black)
     *
     * @return The *color* for the alliance
     */
    public Color getColor() {
        return color;
    }

    /**
     * Select either 'a' or 'b' depending on alliance
     *
     * @param a   The item to select if we're red
     * @param b   The item to select if we're blue
     * @param <T> The type of a &amp; b
     * @return a for Red, b for Blue
     */
    public <T> T selectOf(T a, T b) {
        return Selector.selectOf(this, a, b);
    }

    public static class Selector<T> {
        private final T r, b;

        protected Selector(T red, T blue) {
            r = red;
            b = blue;
        }

        public T select(Alliance a) {
            return a == RED ? r : b;
        }

        public static <T> Selector<T> of(T red, T blue) {
            return new Selector<>(red, blue);
        }

        public static <T> T selectOf(Alliance alliance, T red, T blue) {
            return Selector.of(red, blue).select(alliance);
        }
    }

    public static Alliance get(Class<? extends OpMode> c) {
        if (c.isAnnotationPresent(Red.class)) return RED;
        if (c.isAnnotationPresent(Blue.class)) return BLUE;
        return NONE;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(TYPE)
    public @interface Red {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(TYPE)
    public @interface Blue {
    }
}
