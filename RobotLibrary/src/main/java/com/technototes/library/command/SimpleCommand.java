package com.technototes.library.command;

import com.technototes.library.subsystem.Subsystem;
import java.util.function.Consumer;

public class SimpleCommand implements Command {

    Runnable method;

    public SimpleCommand(Runnable m) {
        super();
        method = m;
    }

    public <T> SimpleCommand(Consumer<T> m, T arg) {
        super();
        method = () -> m.accept(arg);
    }

    @Override
    public void execute() {
        method.run();
    }
}
