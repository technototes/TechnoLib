package com.technototes.library.control;

/** Command implementation of {@link Binding}
 * @author Alex Stedman
 */
public class CommandBinding extends CommandButton implements Binding<CommandInput> {
    private CommandInput[] inputs;
    private Type defaultType;

    public CommandBinding(CommandInput... b){
        this(Type.ALL_ACTIVE, b);
    }

    public CommandBinding(Type type, CommandInput... b){
        super(null);
        inputs = b;
        defaultType = type;
        booleanSupplier = this::get;
    }

    @Override
    public CommandInput[] getSuppliers() {
        return inputs;
    }

    @Override
    public Type getDefaultType() {
        return defaultType;
    }

}
