package com.technototes.library.hardware;

/** Interface for hardware device groups
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public interface HardwareDeviceGroup {
    /** Get the followers for the lead device
     *
     * @return The followers
     */
    HardwareDevice[] getFollowers();

    /** Get all devices in group
     *
     * @return All devices
     */
    HardwareDevice[] getAllDevices();

    /** Propogate actions across the followers
     *
     * @param value the value to propogate
     */
    default void propogate(double value){}
}
