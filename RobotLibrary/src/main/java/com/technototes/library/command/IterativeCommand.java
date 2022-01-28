package com.technototes.library.command;

import java.util.function.BooleanSupplier;
import java.util.function.Function;

public class IterativeCommand extends SequentialCommandGroup {
    /**
     * iterative command for anything
     * @param func
     * @param start
     * @param end
     * @param accum
     * @param <T>
     */
    public <T> IterativeCommand(Function<T, Command> func, T start, T end, Function<T, T> accum){
        for(T t = start; !t.equals(end); t = accum.apply(t)) addCommands(func.apply(t));
    }

    public <T> IterativeCommand(Function<T, Command> func, T start, T end, Function<T, T> accum, BooleanSupplier stopCondition){
        for(T t = start; !t.equals(end); t = accum.apply(t)) addCommands(func.apply(t));
    }

    /**
     * iterative command for an int
     * @param func
     * @param loops
     */
    public IterativeCommand(Function<Integer, Command> func, int loops){
        this(func, 0, loops, i->i+1);
    }

    public IterativeCommand(Function<Integer, Command> func, int loops, BooleanSupplier stopCondition){
        this(func, 0, loops, i->i+1, stopCondition);
    }
    //TODO make limitless but 10 will make sure i dont yeet to much
    public IterativeCommand(Function<Integer, Command> func, BooleanSupplier stopCondition){
        this(func, 0, 10, i->i+1, stopCondition);
    }

}
