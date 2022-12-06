package com.technototes.library.control;

import com.technototes.library.command.Command;
import com.technototes.library.command.CommandScheduler;
import java.util.function.BooleanSupplier;

/**
 * Class for gamepad-command integration
 *
 * @param <T> The type of Gamepad Button
 * @author Alex Stedman
 */
public interface CommandInput<T extends ButtonBase> extends BooleanSupplier {
    /**
     * Schedule a command to be run once the input is pressed.
     *
     * @param command The command to be run
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whenPressed(Command command) {
        return schedule(getInstance()::isJustPressed, command);
    }

    /**
     * Schedule a command to be run once the input is released.
     *
     * @param command The command to be run
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whenReleased(Command command) {
        return schedule(getInstance()::isJustReleased, command);
    }

    /**
     * Schedule a command to be run over &amp; over while the input is pressed,
     * but once it's released, the command will be cancelled.
     *
     * @param command The command to be run
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whilePressed(Command command) {
        return schedule(getInstance()::isPressed, command.cancelUpon(getInstance()::isReleased));
    }

    /**
     * Schedule a command to be run over &amp; over while the input is released,
     * but once it's pressed, the command will be cancelled.
     *
     * @param command The command to be run
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whileReleased(Command command) {
        return schedule(getInstance()::isReleased, command.cancelUpon(getInstance()::isPressed));
    }

    /**
     * Schedule a command to be run while the input is pressed, but only once,
     * and if the command takes so long that the input is released, the command
     * will be cancelled.
     *
     * @param command The command to be run
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whilePressedOnce(Command command) {
        return schedule(
            getInstance()::isJustPressed,
            command.cancelUpon(getInstance()::isReleased)
        );
    }

    /**
     * Schedule a command to be run over &amp; over while the input is pressed
     *
     * @param command The command to be run
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whilePressedContinuous(Command command) {
        return schedule(getInstance()::isPressed, command);
    }

    /**
     * Schdule the command to be run when the input is released, but only once!
     *
     * @param command The command to be run
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whileReleasedOnce(Command command) {
        return schedule(
            getInstance()::isJustReleased,
            command.cancelUpon(getInstance()::isPressed)
        );
    }

    /**
     * Schedule a command to be run when the input was just toggled
     * (From pressed to released, or released to pressed)
     *
     * @param command The command to be run
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whenToggled(Command command) {
        return schedule(getInstance()::isJustToggled, command);
    }

    /**
     * Schedule the command to be run when the input has only stopped being toggled
     *
     * @param command The command to be run
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whenInverseToggled(Command command) {
        return schedule(getInstance()::isJustInverseToggled, command);
    }

    /**
     * Schedule the command to be run while the input is changing.
     * It will be canceled once the input is not changing.
     * This is questionably useful, as toggling over &amp; over is a 'frequency' problem.
     * I expect this is going to behave almost the same as whenToggled...
     *
     * @param command The command to be run
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whileToggled(Command command) {
        return schedule(
            getInstance()::isToggled,
            command.cancelUpon(getInstance()::isInverseToggled)
        );
    }

    /**
     * Schedule the command to run over &amp; over while the input is *not* changing
     * It will be canceled once the input is toggled.
     *
     * @param command The command to be run repetetively
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whileInverseToggled(Command command) {
        return schedule(
            getInstance()::isInverseToggled,
            command.cancelUpon(getInstance()::isToggled)
        );
    }

    /**
     * Return instance of class parameter
     *
     * @return The CommandInput&lt;T&gt; instance
     */
    T getInstance();

    /**
     * Schedule the command to run over &amp; over
     *
     * @param condition The condition
     * @param command   The command to schedule
     * @return The CommandInput&lt;T&gt; instance
     */
    default T schedule(BooleanSupplier condition, Command command) {
        CommandScheduler.getInstance().scheduleJoystick(command, condition);
        return getInstance();
    }

    /**
     * Schedule the command to run
     *
     * @param command The command
     * @return The CommandInput&lt;T&gt; instance
     */
    default T schedule(Command command) {
        return schedule(() -> true, command);
    }

    /**
     * For scheduling a pair commands for when the input is pressed and released.
     * 'press' will be executed once when the input is pressed.
     * 'release' will be executed once when the input is released.
     *
     * @param press   The command to run on Press
     * @param release The command to run on Release
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whenPressedReleased(Command press, Command release) {
        whenPressed(press);
        return whenReleased(release);
    }

    /**
     * For scheduling a pair commands for while the input is pressed and released.
     * 'press' will be executed over &amp; over while the input is pressed.
     * 'release' will be exeucted over &amp; over while the input is released.
     *
     * @param press   The command to run on Press
     * @param release The command to run on Release
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whilePressedReleased(Command press, Command release) {
        whilePressed(press);
        return whileReleased(release);
    }

    /**
     * For scheduling a pair of "opposite" commands for toggling when something
     * is or is not being toggled
     *
     * @param toggle  The command to run when the input flips states
     * @param itoggle The command to run when the input has not changed states
     * @return The CommandInput&lt;T&gt; instance
     */
    default T toggle(Command toggle, Command itoggle) {
        whenToggled(toggle);
        return whenInverseToggled(itoggle);
    }
}
