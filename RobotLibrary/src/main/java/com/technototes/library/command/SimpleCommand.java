package com.technototes.library.command;

import com.technototes.library.subsystem.Subsystem;
import java.util.function.Consumer;

public class SimpleCommand implements Command {

    Runnable method;

    public SimpleCommand(Runnable m) {
        super();
        method = m;
    }

    @Override
    public void execute() {
        method.run();
    }
}
