package com.technototes.library.hardware;

/** Interface for hardware devices that can be inverted
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public interface Invertable<T extends Invertable>{
    /** Set the inversion on the device
     *
     * @param invert Inversion to set device to
     * @return this
     */
    T setInverted(boolean invert);

    /** Invert the device
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
