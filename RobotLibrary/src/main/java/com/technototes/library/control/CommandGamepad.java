package com.technototes.library.control;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.technototes.library.command.Command;
import com.technototes.library.command.CommandScheduler;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/** Class for command gamepads that specifies class params
 * @author Alex Stedman
 */
public class CommandGamepad extends GamepadBase<CommandButton, CommandAxis> {
    /** Make command gamepad
     *
     * @param gamepad The normal gamepad
     */

    public CommandGamepad(Gamepad gamepad) {
        super(gamepad, CommandButton.class, CommandAxis.class);

    }

    public CommandGamepad scheduleLeftStick(BiFunction<Double, Double, Command> f){
        return scheduleStick(leftStick, f);
    }
    public CommandGamepad scheduleLeftStick(BiConsumer<Double, Double> f){
        return scheduleStick(leftStick, f);
    }
    public CommandGamepad scheduleRightStick(BiFunction<Double, Double, Command> f){
        return scheduleStick(rightStick, f);
    }
    public CommandGamepad scheduleRightStick(BiConsumer<Double, Double> f){
        return scheduleStick(rightStick, f);
    }
    public CommandGamepad scheduleDpad(BiFunction<Double, Double, Command> f){
        return scheduleStick(dpad, f);
    }
    public CommandGamepad scheduleDpad(BiConsumer<Double, Double> f){
        return scheduleStick(dpad, f);
    }
    public CommandGamepad scheduleStick(Stick s, BiFunction<Double, Double, Command> f){
        CommandScheduler.getInstance().schedule(f.apply(s.getXAxis(), s.getXAxis()));
        return this;
    }
    public CommandGamepad scheduleStick(Stick s, BiConsumer<Double, Double> f){
        CommandScheduler.getInstance().schedule(()->f.accept(s.getXAxis(), s.getXAxis()));
        return this;
    }

}
