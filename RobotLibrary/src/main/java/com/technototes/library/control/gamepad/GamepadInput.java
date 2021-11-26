package com.technototes.library.control.gamepad;

import com.technototes.library.command.Command;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.control.InputScheduler;

import java.util.function.BooleanSupplier;

/** Class for gamepad-command integration
 * @author Alex Stedman
 * @param <T> The type of Gamepad Button
 */
@FunctionalInterface
public interface GamepadInput<T extends GamepadButton> extends InputScheduler<T> {

    @Override
    default T whenPressed(Command command){
        return schedule(getInstance()::isJustPressed, command);
    }

    @Override
    default T whenReleased(Command command){
        return schedule(getInstance()::isJustReleased, command);
    }

    @Override
    default T whilePressed(Command command){
        return schedule(getInstance()::isPressed, command.cancelUpon(getInstance()::isReleased));
    }

    @Override
    default T whileReleased(Command command){
        return schedule(getInstance()::isReleased, command.cancelUpon(getInstance()::isPressed));
    }

    @Override
    default T whilePressedOnce(Command command){
        return schedule(getInstance()::isJustPressed, command.cancelUpon(getInstance()::isReleased));
    }

    @Override
    default T whilePressedContinuous(Command command){
        return schedule(getInstance()::isPressed, command);
    }

    @Override
    default T whileReleasedOnce(Command command){
        return schedule(getInstance()::isJustReleased, command.cancelUpon(getInstance()::isPressed));
    }

    @Override
    default T whenToggled(Command command){
        return schedule(getInstance()::isJustToggled, command);
    }

    @Override
    default T whenInverseToggled(Command command){
        return schedule(getInstance()::isJustInverseToggled, command);
    }

    @Override
    default T whileToggled(Command command){
        return schedule(getInstance()::isToggled, command.cancelUpon(getInstance()::isInverseToggled));
    }

    @Override
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

}
