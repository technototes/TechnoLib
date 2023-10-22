package com.technototes.library.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.technototes.library.hardware.HardwareDeviceGroup;

/**
 * Class for encoded motor groups
 *
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class EncodedMotorGroup<T extends DcMotorSimple>
    extends EncodedMotor<T>
    implements HardwareDeviceGroup<EncodedMotor<T>> {

    private final EncodedMotor<T>[] followers;

    /**
     * Create an encoded motor groupM
     *
     * @param leader    The Lead motor
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
    public void setVelocity(double tps) {
        super.setVelocity(tps);
        propagate(obj -> obj.setVelocity(tps));
    }

    public void setVelocities(double... tps) {
        for (int i = 0; i < tps.length && i < getDeviceCount(); i++) {
            getDeviceNum(i).setVelocity(tps[i]);
        }
    }

    @Override
    public boolean setPosition(double ticks, double speed) {
        boolean b = true;
        for (int i = 0; i < getDeviceCount(); i++) {
            b = getDeviceNum(i).setPosition(ticks, speed) && b;
        }
        return b;
    }

    public boolean setPositions(double... ticks_then_speeds) {
        boolean b = true;
        for (int i = 0; i < ticks_then_speeds.length / 2 && i < getDeviceCount(); i++) {
            b = getDeviceNum(i).setPosition(ticks_then_speeds[i * 2], ticks_then_speeds[i * 2 + 1]) && b;
        }
        return b;
    }

    public EncodedMotor<T> getDeviceNum(int i) {
        return (i == 0) ? this : followers[i - 1];
    }
}
