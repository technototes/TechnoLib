package com.technototes.logger;


/** Class for returning things state to log at a higher level
 * @author Alex Stedman
 * @param <T> The type of object for the state
 */
@FunctionalInterface
public interface Stated<T> {
    /** The method to get the state
     *
     * @return The state as T
     */
    T getState();
}
