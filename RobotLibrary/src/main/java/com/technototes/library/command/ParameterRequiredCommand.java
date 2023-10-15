package com.technototes.library.command;

import com.technototes.library.subsystem.Subsystem;
import java.util.function.BiConsumer;

public class ParameterRequiredCommand<T extends Subsystem, U> implements Command {

    T sub;
    U param;
    BiConsumer<T, U> method;

    public ParameterRequiredCommand(T s, BiConsumer<T, U> m, U arg) {
        super();
        s = sub;
        param = arg;
        method = m;
        addRequirements(sub);
    }

    @Override
    public void execute() {
        method.accept(sub, param);
    }
}
