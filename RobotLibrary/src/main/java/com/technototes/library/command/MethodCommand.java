package com.technototes.library.command;

import com.technototes.library.subsystem.Subsystem;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This is an alternative to using the Command.create(...) interface, as it's a little more
 * obvious to new/non-Java-informed coders as to what's happening, while the Command.create
 * function may require understanding that subsystem::method is a 'Command' because it's Runnable
 * and Java will promote it to a Command from a Runnable.
 */
public class MethodCommand implements Command {

    Runnable method;

    /**
     * This is an alternative to the Command.create(...) interface
     * @param m A method to invoke
     * @param subs Any subsystems this command requires
     */
    public MethodCommand(Runnable m, Subsystem... subs) {
        super();
        method = m;
        addRequirements(subs);
    }

    /**
     * This is an alternative to the Command.create(...) interface
     * @param <T> The type of the argument for the method being invoked
     * @param m A method to invoke
     * @param arg The argument to pass to the method
     * @param subs Any subsystems this command requires
     */
    public <T> MethodCommand(Consumer<T> m, T arg, Subsystem... subs) {
        super();
        method = () -> m.accept(arg);
        addRequirements(subs);
    }

    /**
     * This is an alternative to the Command.create(...) interface
     * @param <T> The type of the argument for the method being invoked
     * @param m A method to invoke
     * @param argSupplier The function to compute the argument to the method
     * @param subs Any subsystems this command requires
     */
    public <T> MethodCommand(Consumer<T> m, Supplier<T> argSupplier, Subsystem... subs) {
        super();
        method = () -> m.accept(argSupplier.get());
        addRequirements(subs);
    }

    /**
     * This is an alternative to the Command.create(...) interface
     * @param <T> The type of the first argument for the method being invoked
     * @param <U> The type of the second argument for the method being invoked
     * @param m A method to invoke
     * @param arg1supplier The function to compute the argument to pass to the method
     * @param arg2 The second argument to pass to the method
     * @param subs Any subsystems this command requires
     */
    public <T, U> MethodCommand(BiConsumer<T, U> m, Supplier<T> arg1supplier, U arg2, Subsystem... subs) {
        super();
        method = () -> m.accept(arg1supplier.get(), arg2);
        addRequirements(subs);
    }

    /**
     * This is an alternative to the Command.create(...) interface
     * @param <T> The type of the first argument for the method being invoked
     * @param <U> The type of the second argument for the method being invoked
     * @param m A method to invoke
     * @param arg1 The first argument to pass to the method
     * @param arg2supplier The function to compute the second argument to pass to the method
     * @param subs Any subsystems this command requires
     */
    public <T, U> MethodCommand(BiConsumer<T, U> m, T arg1, Supplier<U> arg2supplier, Subsystem... subs) {
        super();
        method = () -> m.accept(arg1, arg2supplier.get());
        addRequirements(subs);
    }

    /**
     * This is an alternative to the Command.create(...) interface
     * @param <T> The type of the first argument for the method being invoked
     * @param <U> The type of the second argument for the method being invoked
     * @param m A method to invoke
     * @param arg1supplier The function to compute the first argument to pass to the method
     * @param arg2supplier The function to compute the second argument to pass to the method
     * @param subs Any subsystems this command requires
     */
    public <T, U> MethodCommand(BiConsumer<T, U> m, Supplier<T> arg1supplier, Supplier<U> arg2supplier, Subsystem... subs) {
        super();
        method = () -> m.accept(arg1supplier.get(), arg2supplier.get());
        addRequirements(subs);
    }

    /**
     * This is an alternative to the Command.create(...) interface
     * @param <T> The type of the first argument for the method being invoked
     * @param <U> The type of the second argument for the method being invoked
     * @param m A method to invoke
     * @param arg1 The first argument to pass to the method
     * @param arg2 The second argument to pass to the method
     * @param subs Any subsystems this command requires
     */
    public <T, U> MethodCommand(BiConsumer<T, U> m, T arg1, U arg2, Subsystem... subs) {
        super();
        method = () -> m.accept(arg1, arg2);
        addRequirements(subs);
    }

    @Override
    public void execute() {
        method.run();
    }
}
