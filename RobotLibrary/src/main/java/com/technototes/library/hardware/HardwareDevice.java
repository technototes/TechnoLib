package com.technototes.library.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

/** Class for hardware devices
 * @author Alex Stedman
 * @param <T> The class for the default device (ones found in ftcsdk)
 */
@SuppressWarnings("unused")
public abstract class HardwareDevice<T> {
    /** Hardware map object for stuff
     *
     */
    public static HardwareMap hardwareMap = null;
    protected T device;

    /** Make a hardware device
     *
     * @param device The default device
     */
    public HardwareDevice(T device) {
        this.device = device;
    }

    /** Make a hardware device with the string to get from hardwaremap
     *
     * @param deviceName The device name
     */
    @SuppressWarnings("unchecked cast")
    protected HardwareDevice(String deviceName) {
         this(hardwareMap.get((Class<T>) Object.class /*T.class*/, deviceName));
    }

    /** Get encapsulated device
      * @return The device
     */
    public T getDevice() {
        return device;
    }
}
