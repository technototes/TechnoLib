package com.technototes.library.general;

/** Interface for anything that can be inverted
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public interface Invertable<T extends Invertable<T>>{
    /** Set the inversion
     *
     * @param invert Inversion to set
     * @return this
     */
    T setInverted(boolean invert);

    /** Invert
     *
     * @return this
     */
    default T invert(){
        return setInverted(!getInverted());
    }

    /** Get current inversion
     *
     * @return Current inversion
     */
    boolean getInverted();
}
