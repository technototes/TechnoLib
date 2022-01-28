package com.technototes.library.hardware.servo;

import com.technototes.library.hardware.HardwareDeviceGroup;

/** Class for servo group
 * @author Alex Stedman
 */
@SuppressWarnings("unused")
public class ServoGroup extends Servo implements HardwareDeviceGroup<Servo> {
    private final Servo[] followers;

    /** Create a servo group
     *
     * @param servos the servos
     */
    public ServoGroup(Servo... servos) {
        super(servos[0].getDevice());
        super.setInverted(servos[0].getInverted());
        followers = new Servo[servos.length-1];
        System.arraycopy(servos, 1, followers, 0, followers.length);
    }

    @Override
    public Servo[] getFollowers() {
        return followers;
    }

    @Override
    public Servo[] getAllDevices() {
        Servo[] m = new Servo[followers.length + 1];
        m[0] = this;
        System.arraycopy(followers, 0, m, 1, m.length - 1);
        return m;
    }

    @Override
    public void propogate(double value) {
        for(Servo s : followers){
            s.setPosition(value);
        }
    }

    @Override
    public void setPosition(double position) {
        super.setPosition(position);
        propogate(position);
    }

    @Override
    public ServoGroup startAt(double position) {
        return (ServoGroup) super.startAt(position);
    }
}