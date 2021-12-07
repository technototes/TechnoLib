package com.technototes.library.control;

import com.technototes.library.command.Command;
import com.technototes.library.command.CommandScheduler;

import java.util.function.BooleanSupplier;

/** Class for gamepad-command integration
 * @author Alex Stedman
 * @param <T> The type of Gamepad Button
 */
@FunctionalInterface
public interface CommandInput<T extends GamepadButton> {

    default T whenPressed(Command command){
        return schedule(getInstance()::isJustPressed, command);
    }

    default T whenReleased(Command command){
        return schedule(getInstance()::isJustReleased, command);
    }

    default T whilePressed(Command command){
        return schedule(getInstance()::isPressed, command.cancelUpon(getInstance()::isReleased));
    }

    default T whileReleased(Command command){
        return schedule(getInstance()::isReleased, command.cancelUpon(getInstance()::isPressed));
    }

    default T whilePressedOnce(Command command){
        return schedule(getInstance()::isJustPressed, command.cancelUpon(getInstance()::isReleased));
    }

    default T whilePressedContinuous(Command command){
        return schedule(getInstance()::isPressed, command);
    }

    default T whileReleasedOnce(Command command){
        return schedule(getInstance()::isJustReleased, command.cancelUpon(getInstance()::isPressed));
    }

    default T whenToggled(Command command){
        return schedule(getInstance()::isJustToggled, command);
    }

    default T whenInverseToggled(Command command){
        return schedule(getInstance()::isJustInverseToggled, command);
    }

    default T whileToggled(Command command){
        return schedule(getInstance()::isToggled, command.cancelUpon(getInstance()::isInverseToggled));
    }

    default T whileInverseToggled(Command command){
        return schedule(getInstance()::isInverseToggled, command.cancelUpon(getInstance()::isToggled));
    }

    /** Return instance of class parameter
     *
     * @return The instance
     */
    T getInstance();

    /** Schedule the commands
     *
     * @param condition The condition
     * @param command The command to schedule
     * @return The instance
     */
    default T schedule(BooleanSupplier condition, Command command){
        CommandScheduler.getInstance().scheduleJoystick(command, condition);
        return getInstance();
    }

    /** just schedules things
     *
     * @param command command
     * @return the instance
     */
    default T schedule(Command command){
        return schedule(()->true,command);
    }

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
