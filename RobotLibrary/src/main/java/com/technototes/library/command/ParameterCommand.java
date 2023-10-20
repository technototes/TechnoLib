package com.technototes.library.command;

import com.technototes.library.subsystem.Subsystem;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Use SimpleCommand: It can handle a single Function (Runnable)
 * or a F(T) (Consumer<T>)
 * @param <U>
 */
@Deprecated
public class ParameterCommand<U> implements Command {

    U param;
    Consumer<U> method;

    public ParameterCommand(Consumer<U> m, U arg) {
        super();
        param = arg;
        method = m;
    }

    @Override
    public void execute() {
        method.accept(param);
    }
}
