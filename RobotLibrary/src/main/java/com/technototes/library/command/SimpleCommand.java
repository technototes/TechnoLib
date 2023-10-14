package com.technototes.library.command;

import com.technototes.library.subsystem.Subsystem;

import java.util.function.Consumer;

public class SimpleCommand<T extends Subsystem> implements Command {
    T sub;
    Consumer<T> method;

    public SimpleCommand(T s, Consumer<T> m) {
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
