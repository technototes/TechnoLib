package com.technototes.library.util;

import static java.lang.annotation.ElementType.TYPE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An enumeration to specify which alliance the bot is on (Red, Blue, or None)
 */
public enum Alliance {
    /**
     * RED alliance selector
     */
    RED(Color.RED),
    /**
     * BLUE alliance selector
     */
    BLUE(Color.BLUE),
    /**
     * NO ALLIANCE SELECTED
     */
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

    /**
     * This feels over-engineered, but it's probably for some obnoxious Java thing,
     * unfortunate. This lets you pick between two types based on the Alliance
     *
     * @param <T> The type of object to select
     */
    public static class Selector<T> {

        private final T r, b;

        /**
         * Create a Selelector for red/blue
         *
         * @param red  For Red selection
         * @param blue For Blue selection
         */
        protected Selector(T red, T blue) {
            r = red;
            b = blue;
        }

        /**
         * Select the red or blue item based on the Alliance
         *
         * @param a The alliance
         * @return The object selected
         */
        public T select(Alliance a) {
            return a == RED ? r : b;
        }

        /**
         * Selector factory method
         *
         * @param red  The object to select if red
         * @param blue The object to select if blue
         * @param <T>  The type of red &amp; blue
         * @return The Selector
         */
        public static <T> Selector<T> of(T red, T blue) {
            return new Selector<>(red, blue);
        }

        /**
         * Based on Alliance, choose red or blue
         *
         * @param alliance The alliance
         * @param red      The thing to choose for red
         * @param blue     The thing to choose for blue
         * @param <T>      The type of the red &amp; blue things
         * @return The thing (red or blue) selected
         */
        public static <T> T selectOf(Alliance alliance, T red, T blue) {
            return Selector.of(red, blue).select(alliance);
        }
    }

    /**
     * Get the alliance set for the OpMode passed in, if it's set in the annotation
     *
     * @param c The OpMode
     * @return The alliance of the opmode
     */
    public static Alliance get(Class<? extends OpMode> c) {
        if (c.isAnnotationPresent(Red.class)) return RED;
        if (c.isAnnotationPresent(Blue.class)) return BLUE;
        return NONE;
    }

    /**
     * Not sure what this is for. Something for annotation processing? No idea :/
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(TYPE)
    public @interface Red {
    }

    /**
     * Not sure what this is for. Something for annotation processing? No idea :/
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(TYPE)
    public @interface Blue {
    }
}
