package com.technototes.library.control;

import com.technototes.library.command.Command;

public interface InputScheduler<T> {
    /** Schedule command when gamepad button is just pressed
     *
     * @param command The command
     * @return this
     */
    T whenPressed(Command command);
    /** Schedule command when gamepad button is just released
     *
     * @param command The command
     * @return this
     */
    T whenReleased(Command command);
    /** Schedule command when gamepad button is pressed and cancels it when released
     *
     * @param command The command
     * @return this
     */
    T whilePressed(Command command);

    /** Schedule command when gamepad button is released and cancels it when pressed
     *
     * @param command The command
     * @return this
     */
    T whileReleased(Command command);

    /** Schedule command once when gamepad button is pressed and cancels it when released
     *
     * @param command The command
     * @return this
     */
    T whilePressedOnce(Command command);

    T whilePressedContinuous(Command command);

    /** Schedule command once when gamepad button is released and cancels it when pressed
     *
     * @param command The command
     * @return this
     */
    T whileReleasedOnce(Command command);

    /** Schedule command when gamepad button is just toggled
     *
     * @param command The command
     * @return this
     */
    T whenToggled(Command command);
    /** Schedule command when gamepad button is just inverse toggled
     *
     * @param command The command
     * @return this
     */
    T whenInverseToggled(Command command);

    /** Schedule command when gamepad button is toggled
     *
     * @param command The command
     * @return this
     */
    T whileToggled(Command command);
    /** Schedule command when gamepad button is inverse toggled
     *
     * @param command The command
     * @return this
     */
    T whileInverseToggled(Command command);

    default T whenPressedReleased(Command press, Command release){
        whenPressed(press);
        return whenReleased(release);
    }

    default T whilePressedReleased(Command press, Command release){
        whilePressed(press);
        return whileReleased(release);
    }

    default T toggle(Command toggle, Command itoggle){
        whenToggled(toggle);
        return whenInverseToggled(itoggle);
    }

}
