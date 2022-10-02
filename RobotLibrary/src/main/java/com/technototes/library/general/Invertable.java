package com.technototes.library.general;

/**
 * Interface for anything that can be inverted
 *
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public interface Invertable<T extends Invertable<T>> {
    /**
     * Set the inversion (true -> Is inverted, false -> Not inverted)
     *
     * @param invert Inversion value
     * @return this
     */
    T setInverted(boolean invert);

    /**
     * Toggle inversion
     *
     * @return this
     */
    default T invert() {
        return setInverted(!getInverted());
    }

    /**
     * Get current inversion
     *
     * @return Current inversion
     */
    boolean getInverted();
}
