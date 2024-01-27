package com.technototes.library.command;

import com.technototes.library.subsystem.Subsystem;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MethodCommand implements Command {

    Runnable method;

    public MethodCommand(Runnable m, Subsystem... subs) {
        super();
        method = m;
        addRequirements(subs);
    }

    public <T> MethodCommand(Consumer<T> m, T arg, Subsystem... subs) {
        super();
        method = () -> m.accept(arg);
        addRequirements(subs);
    }

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
