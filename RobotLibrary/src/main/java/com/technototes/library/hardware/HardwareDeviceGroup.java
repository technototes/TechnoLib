package com.technototes.library.hardware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Interface for hardware device groups
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public interface HardwareDeviceGroup<T extends HardwareDevice> {
    /** Get the followers for the lead device
     *
     * @return The followers
     */
    T[] getFollowers();

    default List<T> getFollowerist(){
        return Arrays.asList(getFollowers());
    }
    /** Get all devices in group
     *
     * @return All devices
     */
    T[] getAllDevices();

    default List<T> getAllDeviceList(){
        return Arrays.asList(getAllDevices());
    }

    /** Propogate actions across the followers
     *
     * @param value the value to propogate
     */
    default void propogate(double value){}
}
