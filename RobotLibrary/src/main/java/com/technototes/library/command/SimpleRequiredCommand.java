package com.technototes.library.command;

import com.technototes.library.subsystem.Subsystem;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SimpleRequiredCommand<T extends Subsystem> implements Command {

    T sub;
    Consumer<T> method;

    public SimpleRequiredCommand(T s, Consumer<T> m) {
        super();
        sub = s;
        method = m;
        addRequirements(sub);
    }

    public <U> SimpleRequiredCommand(T s, BiConsumer<T, U> m, U arg) {
        super();
        sub = s;
        method = subsys -> m.accept(subsys, arg);
        addRequirements(sub);
    }

    @Override
    public void execute() {
        method.accept(sub);
    }
}
