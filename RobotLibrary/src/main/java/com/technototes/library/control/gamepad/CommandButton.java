package com.technototes.library.control.gamepad;

import com.technototes.library.command.Command;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;

/** Class for command buttons for gamepad
 * @author Alex Stedman
 */
public class CommandButton extends GamepadButton implements GamepadInput<CommandButton> {
    /** Make command button
     *
     * @param supplier The supplier for the button
     */
    public CommandButton(BooleanSupplier supplier) {
        super(supplier);
    }

    @Override
    public CommandButton getInstance() {
        return this;
    }

    public CommandButton schedule(Function<Boolean, Command> f){
            return schedule(f.apply(this.getAsBoolean()));
    }
    public CommandButton schedule(Consumer<Boolean> f){
        return schedule(()->f.accept(this.getAsBoolean()));
    }

}
