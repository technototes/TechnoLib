package com.technototes.library.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.technototes.library.hardware.HardwareDeviceGroup;
import com.technototes.library.hardware.sensor.encoder.Encoder;

/** Class for encoded motor groups
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class EncodedMotorGroup<T extends DcMotorSimple> extends EncodedMotor<T> implements HardwareDeviceGroup<Motor<T>> {
    private final Motor<T>[] followers;

    /** Create an encoded motor groupM
     *
     * @param leader The Lead motor
     * @param followers The following motors
     */
    public EncodedMotorGroup(EncodedMotor<T> leader, Motor<T>... followers) {
        super(leader.getDevice());
        this.followers = followers;

    }

    @Override
    public Motor<T>[] getFollowers() {
        return followers;
    }

    @Override
    public Motor<T>[] getAllDevices() {
        Motor<T>[] m =  new Motor[followers.length + 1];
        m[0] = this;
        System.arraycopy(followers, 0, m, 1, m.length - 1);
        return m;
    }

    @Override
    public void propogate(double value) {
        for(Motor<T> m : followers){
            m.setSpeed(value);
        }
    }

    @Override
    public void setVelocity(double tps) {
        super.setVelocity(tps);
        propogate(super.getSpeed());
    }

    @Override
    public boolean setPosition(double ticks, double speed) {
        boolean b = super.setPosition(ticks, speed);
        propogate(super.getSpeed());
        return b;
    }
}
