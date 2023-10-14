package com.technototes.library.command;

import com.technototes.library.subsystem.Subsystem;

import java.util.function.Consumer;

public class SimpleRequiredCommand<T extends Subsystem> implements Command {
    T sub;
    Consumer<T> method;

    public SimpleRequiredCommand(T s, Consumer<T> m) {
        super();
        s = sub;
        method = m;
        addRequirements(sub);
    }

    @Override
    public void execute() {
        method.accept(sub);
    }
}
