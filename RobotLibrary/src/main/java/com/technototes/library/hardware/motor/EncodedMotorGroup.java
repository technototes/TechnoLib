package com.technototes.library.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.technototes.library.hardware.HardwareDeviceGroup;

/** Class for encoded motor groups
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class EncodedMotorGroup<T extends DcMotorSimple>
    extends EncodedMotor<T>
    implements HardwareDeviceGroup<Motor<T>> {

    private final EncodedMotor<T>[] followers;

    /** Create an encoded motor groupM
     *
     * @param leader The Lead motor
     * @param followers The following motors
     */
    public EncodedMotorGroup(EncodedMotor<T> leader, EncodedMotor<T>... followers) {
        super(leader.getDevice());
        this.followers = followers;
    }

    @Override
    public EncodedMotor<T>[] getFollowers() {
        return followers;
    }

    @Override
    public EncodedMotor<T>[] getAllDevices() {
        EncodedMotor<T>[] m = new EncodedMotor[followers.length + 1];
        m[0] = this;
        System.arraycopy(followers, 0, m, 1, m.length - 1);
        return m;
    }

    @Override
    public void propagate(double value) {
        for (EncodedMotor<T> m : followers) {
            m.setSpeed(value);
        }
    }

    @Override
    public void setVelocity(double tps) {
        super.setVelocity(tps);
        propagate(super.getSpeed());
    }

    @Override
    public boolean setPosition(double ticks, double speed) {
        boolean b = super.setPosition(ticks, speed);
        propagate(super.getSpeed());
        return b;
    }

    public EncodedMotor<T> getDeviceNum(int i) {
        return (i == 0) ? this : followers[i - 1];
    }
}
