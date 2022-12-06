package com.technototes.library.util;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a functional interface, when 'accept' is invoked, will only invoke the 'consume' method
 * when a different value is provided. It basically removed duplicates.
 * <p>
 * TODO: This seems like the HashMap is completely unnecessary. Maybe I'm not following the java
 * TODO: object model, but I don't see how map ever has more than a single value in it...
 *
 * @param <T> The type of the value to be accepted/consumed
 */
@FunctionalInterface
public interface SmartConsumer<T> {
    /**
     * The map of values that have been consumed.
     */
    Map<SmartConsumer<?>, Object> map = new HashMap<>();

    /**
     * The 'consume' function
     */
    void consume(T t);

    /** The public interface for a SmartConsumer: accept should be invoked, not consume :)
     *
     * @param t The value being consumed/accepted
     * @return
     */
    default SmartConsumer<T> accept(T t) {
        if (!t.equals(getLast())) {
            consume(t);
            map.put(this, t);
        }
        return this;
    }

    default T getLast() {
        return (T) map.get(this);
    }

    static void reset() {
        map.clear();
    }
}
