package com.technototes.library.general;

/**
 * Interface for anything that can be enabled/disabled
 * <p>
 * You must have "setEnabled" and "isEnabled" functions. Everything else has
 * functional implementations in the interface
 *
 * @param <T> An Enable-able interface/class type
 */
public interface Enablable<T extends Enablable<T>> {
    /**
     * Enable the object
     *
     * @return The object
     */
    default T enable() {
        return setEnabled(true);
    }

    /**
     * Disable the object
     *
     * @return the object
     */
    default T disable() {
        return setEnabled(false);
    }

    /**
     * Set whether or not the device is enabled
     *
     * @param enable True for enabled, false for disabled
     * @return The object
     */
    T setEnabled(boolean enable);

    /**
     * Toggle whether this object is enabled or not
     *
     * @return The object
     */
    default T toggleEnabled() {
        return setEnabled(!isEnabled());
    }

    /**
     * @return Is this object enabled?
     */
    boolean isEnabled();

    /**
     * @return Is this object disabled?
     */
    default boolean isDisabled() {
        return !isEnabled();
    }
}
