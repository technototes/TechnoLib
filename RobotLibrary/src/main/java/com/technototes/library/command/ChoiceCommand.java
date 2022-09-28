package com.technototes.library.command;

import android.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ChoiceCommand extends ParallelRaceGroup {
    @SafeVarargs
    public ChoiceCommand(Pair<BooleanSupplier, Command>... cs){
        super(Arrays.stream(cs).<Command>map(p -> new ConditionalCommand(p.first, p.second)).collect(Collectors.toList()).toArray(new Command[]{}));
    }

    public ChoiceCommand(BooleanSupplier b, Command c){
        this(new Pair<>(b, c));
    }
}
