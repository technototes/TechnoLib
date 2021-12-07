package com.technototes.library.control;

/** Command implementation of {@link Binding}
 * @author Alex Stedman
 */
//TODO make this less jank to use
public class CommandBinding extends CommandButton implements Binding<CommandButton> {
    private CommandButton[] buttons;
    private Type defaultType;

    public CommandBinding(CommandButton... b){
        this(Type.ALL_ACTIVE, b);
    }

    public CommandBinding(Type type, CommandButton... b){
        super(null);
        buttons = b;
        defaultType = type;
        booleanSupplier = this::get;
    }

    @Override
    public CommandButton[] getSuppliers() {
        return buttons;
    }

    @Override
    public Type getDefaultType() {
        return defaultType;
    }

}
