package com.technototes.library.control;

import com.technototes.library.command.Command;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.ParameterCommand;
import com.technototes.library.command.ParameterRequiredCommand;
import com.technototes.library.command.SimpleCommand;
import com.technototes.library.command.SimpleRequiredCommand;
import com.technototes.library.subsystem.Subsystem;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

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
    default <S extends Subsystem> T whenPressed(S req, Consumer<S> methodRef) {
        return whenPressed(new SimpleRequiredCommand<>(req, methodRef));
    }
    default <S extends Subsystem, R> T whenPressed(S req, BiConsumer<S, R> methodRef, R param) {
        return whenPressed(new ParameterRequiredCommand<>(req, methodRef, param));
    }
    default T whenPressed(Runnable methodRef) {
        return whenPressed(new SimpleCommand(methodRef));
    }
    default <R> T whenPressed(Consumer<R> methodRef, R param) {
        return whenPressed(new ParameterCommand<>(methodRef, param));
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
    default <S extends Subsystem> T whenReleased(S req, Consumer<S> methodRef) {
        return whenReleased(new SimpleRequiredCommand<>(req, methodRef));
    }
    default <S extends Subsystem, R> T whenReleased(S req, BiConsumer<S, R> methodRef, R param) {
        return whenReleased(new ParameterRequiredCommand<>(req, methodRef, param));
    }
    default T whenReleased(Runnable methodRef) {
        return whenReleased(new SimpleCommand(methodRef));
    }
    default <R> T whenReleased(Consumer<R> methodRef, R param) {
        return whenReleased(new ParameterCommand<>(methodRef, param));
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
    default <S extends Subsystem> T whilePressed(S req, Consumer<S> methodRef) {
        return whilePressed(new SimpleRequiredCommand<>(req, methodRef));
    }
    default <S extends Subsystem, R> T whilePressed(S req, BiConsumer<S, R> methodRef, R param) {
        return whilePressed(new ParameterRequiredCommand<>(req, methodRef, param));
    }
    default T whilePressed(Runnable methodRef) {
        return whilePressed(new SimpleCommand(methodRef));
    }
    default <R> T whilePressed(Consumer<R> methodRef, R param) {
        return whilePressed(new ParameterCommand<>(methodRef, param));
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
    default <S extends Subsystem> T whileReleased(S req, Consumer<S> methodRef) {
        return whileReleased(new SimpleRequiredCommand<>(req, methodRef));
    }
    default <S extends Subsystem, R> T whileReleased(S req, BiConsumer<S, R> methodRef, R param) {
        return whileReleased(new ParameterRequiredCommand<>(req, methodRef, param));
    }
    default T whileReleased(Runnable methodRef) {
        return whileReleased(new SimpleCommand(methodRef));
    }
    default <R> T whileReleased(Consumer<R> methodRef, R param) {
        return whileReleased(new ParameterCommand<>(methodRef, param));
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
        return schedule(getInstance()::isJustPressed, command.cancelUpon(getInstance()::isReleased));
    }
    default <S extends Subsystem> T whilePressedOnce(S req, Consumer<S> methodRef) {
        return whilePressedOnce(new SimpleRequiredCommand<>(req, methodRef));
    }
    default <S extends Subsystem, R> T whilePressedOnce(S req, BiConsumer<S, R> methodRef, R param) {
        return whilePressedOnce(new ParameterRequiredCommand<>(req, methodRef, param));
    }
    default T whilePressedOnce(Runnable methodRef) {
        return whilePressedOnce(new SimpleCommand(methodRef));
    }
    default <R> T whilePressedOnce(Consumer<R> methodRef, R param) {
        return whilePressedOnce(new ParameterCommand<>(methodRef, param));
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
    default <S extends Subsystem> T whilePressedContinuous(S req, Consumer<S> methodRef) {
        return whilePressedContinuous(new SimpleRequiredCommand<>(req, methodRef));
    }
    default <S extends Subsystem, R> T whilePressedContinuous(S req, BiConsumer<S, R> methodRef, R param) {
        return whilePressedContinuous(new ParameterRequiredCommand<>(req, methodRef, param));
    }
    default T whilePressedContinuous(Runnable methodRef) {
        return whilePressedContinuous(new SimpleCommand(methodRef));
    }
    default <R> T whilePressedContinuous(Consumer<R> methodRef, R param) {
        return whilePressedContinuous(new ParameterCommand<>(methodRef, param));
    }
    /**
     * Schedule the command to be run when the input is released, but only once!
     *
     * @param command The command to be run
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whileReleasedOnce(Command command) {
        return schedule(getInstance()::isJustReleased, command.cancelUpon(getInstance()::isPressed));
    }
    default <S extends Subsystem> T whileReleasedOnce(S req, Consumer<S> methodRef) {
        return whileReleasedOnce(new SimpleRequiredCommand<>(req, methodRef));
    }
    default <S extends Subsystem, R> T whileReleasedOnce(S req, BiConsumer<S, R> methodRef, R param) {
        return whileReleasedOnce(new ParameterRequiredCommand<>(req, methodRef, param));
    }
    default T whileReleasedOnce(Runnable methodRef) {
        return whileReleasedOnce(new SimpleCommand(methodRef));
    }
    default <R> T whileReleasedOnce(Consumer<R> methodRef, R param) {
        return whileReleasedOnce(new ParameterCommand<>(methodRef, param));
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
    default <S extends Subsystem> T whenToggled(S req, Consumer<S> methodRef) {
        return whenToggled(new SimpleRequiredCommand<>(req, methodRef));
    }
    default <S extends Subsystem, R> T whenToggled(S req, BiConsumer<S, R> methodRef, R param) {
        return whenToggled(new ParameterRequiredCommand<>(req, methodRef, param));
    }
    default T whenToggled(Runnable methodRef) {
        return whenToggled(new SimpleCommand(methodRef));
    }
    default <R> T whenToggled(Consumer<R> methodRef, R param) {
        return whenToggled(new ParameterCommand<>(methodRef, param));
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
    default <S extends Subsystem> T whenInverseToggled(S req, Consumer<S> methodRef) {
        return whenInverseToggled(new SimpleRequiredCommand<>(req, methodRef));
    }
    default <S extends Subsystem, R> T whenInverseToggled(S req, BiConsumer<S, R> methodRef, R param) {
        return whenInverseToggled(new ParameterRequiredCommand<>(req, methodRef, param));
    }
    default T whenInverseToggled(Runnable methodRef) {
        return whenInverseToggled(new SimpleCommand(methodRef));
    }
    default <R> T whenInverseToggled(Consumer<R> methodRef, R param) {
        return whenInverseToggled(new ParameterCommand<>(methodRef, param));
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
        return schedule(getInstance()::isToggled, command.cancelUpon(getInstance()::isInverseToggled));
    }
    default <S extends Subsystem> T whileToggled(S req, Consumer<S> methodRef) {
        return whileToggled(new SimpleRequiredCommand<>(req, methodRef));
    }
    default <S extends Subsystem, R> T whileToggled(S req, BiConsumer<S, R> methodRef, R param) {
        return whileToggled(new ParameterRequiredCommand<>(req, methodRef, param));
    }
    default T whileToggled(Runnable methodRef) {
        return whileToggled(new SimpleCommand(methodRef));
    }
    default <R> T whileToggled(Consumer<R> methodRef, R param) {
        return whileToggled(new ParameterCommand<>(methodRef, param));
    }

    /**
     * Schedule the command to run over &amp; over while the input is *not* changing
     * It will be canceled once the input is toggled.
     *
     * @param command The command to be run repetetively
     * @return The CommandInput&lt;T&gt; instance
     */
    default T whileInverseToggled(Command command) {
        return schedule(getInstance()::isInverseToggled, command.cancelUpon(getInstance()::isToggled));
    }
    default <S extends Subsystem> T whileInverseToggled(S req, Consumer<S> methodRef) {
        return whileInverseToggled(new SimpleRequiredCommand<>(req, methodRef));
    }
    default <S extends Subsystem, R> T whileInverseToggled(S req, BiConsumer<S, R> methodRef, R param) {
        return whileInverseToggled(new ParameterRequiredCommand<>(req, methodRef, param));
    }
    default T whileInverseToggled(Runnable methodRef) {
        return whileInverseToggled(new SimpleCommand(methodRef));
    }
    default <R> T whileInverseToggled(Consumer<R> methodRef, R param) {
        return whileInverseToggled(new ParameterCommand<>(methodRef, param));
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
        CommandScheduler.scheduleJoystick(command, condition);
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
     * You could just use button.whenPressed(press).whenRelease(release)
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
     * 'release' will be executed over &amp; over while the input is released.
     *
     * Just use button.whilePressed(...).whileReleased(...)
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
     * For non-command (method ref) usage, just use
     * button.whenToggled(toggled).whenInverseToggled(notToggled)
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
