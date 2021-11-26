package com.technototes.library.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.technototes.library.hardware.HardwareDeviceGroup;

/** Class for a group of motors
 *
 * @param <T> The type of motors to group
 */
@SuppressWarnings("unused")
public class MotorGroup<T extends Motor> extends Motor<DcMotorSimple> implements HardwareDeviceGroup {
    private final Motor[] followers;

    /** Make a motor group
     *
     * @param motors The motors
     */
    @SafeVarargs
    public MotorGroup(T... motors) {
        super((DcMotorSimple) motors[0].getDevice());
        followers = new Motor[motors.length-1];
        System.arraycopy(motors, 1, followers, 0, followers.length);
    }



    @Override
    public Motor[] getFollowers() {
        return followers;
    }

    @Override
    public Motor[] getAllDevices() {
        Motor[] m = new Motor[followers.length + 1];
        m[0] = this;
        System.arraycopy(followers, 0, m, 1, m.length - 1);
        return m;
    }

    @Override
    public void propogate(double value) {
        for(Motor m : followers){
            m.setSpeed(value);
        }
    }

    @Override
    public void setSpeed(double speed) {
        super.setSpeed(speed);
        propogate(speed);
    }
}
