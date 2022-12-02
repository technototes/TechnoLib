package com.technototes.library.logger.entry;

import com.technototes.library.util.Color;
import java.util.function.Supplier;

/**
 * The root class for logging entries
 *
 * @param <T> The type of value being stored by the entry
 * @author Alex Stedman
 */
public abstract class Entry<T> implements Supplier<T> {

    /**
     * The index (in the list) of the entry
     */
    protected int x;
    /**
     * The priority (in the telemetry list) of the entry
     */
    protected int priority;
    /**
     * The function called to get the value to display
     */
    protected Supplier<T> supplier;
    /**
     * The name of the Entry
     */
    protected String name;
    /**
     * String to use a 'header' (calculated from name and color)
     */
    protected String tag;
    /**
     * Color to display
     */
    protected Color color;

    /**
     * Create an entry with name, value, index, and color
     *
     * @param n     Name of the entry
     * @param s     Value function to display
     * @param index Index of the entry
     * @param c     Color to display
     */
    public Entry(String n, Supplier<T> s, int index, Color c) {
        x = index;
        supplier = s;
        name = n;
        color = c;
        tag = (name.equals("") ? " " : color.format(name) + " ` ");
    }

    /**
     * Set's the priority for this log line (handy for telemetry overflow)
     *
     * @param p The priority
     * @return Self (for chaining)
     */
    public Entry<T> setPriority(int p) {
        priority = p;
        return this;
    }

    @Override
    public T get() {
        return supplier.get();
    }

    /**
     * The String for the logged item
     *
     * @return The String
     */
    @Override
    public String toString() {
        return supplier.get().toString();
    }

    /**
     * The tag for the entry
     *
     * @return The tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Get the name (unformatted tag)
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the index for the entry
     *
     * @return The index
     */
    public int getIndex() {
        return x;
    }

    /**
     * Set index
     *
     * @param i New index
     * @return this
     */
    public Entry setIndex(int i) {
        x = i;
        return this;
    }

    /**
     * Get Priority for the entry
     *
     * @return The priority
     */
    public int getPriority() {
        return priority;
    }
}
