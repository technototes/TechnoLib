package com.technototes.library;

/** Root class for the Robot Library (I will put important stuff here)
 * @author Alex Stedman
 *
 */
@SuppressWarnings("unused")
public class RobotLibrary {
    /** Get library version
     *
     * @return Library version
     */
    public static String getVersion(){
        return "1.0.2";
    }

    /** Get if the library is a pre release
     *
     * @return If this library version is a pre release
     */
    public static boolean isPreRelease(){
        return true;
    }
}
