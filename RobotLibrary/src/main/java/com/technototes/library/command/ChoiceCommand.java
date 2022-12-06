package com.technototes.library.command;

import android.util.Pair;
import java.util.Arrays;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

/**
 * A command that allows choosing among a number of commands based on variety of conditions
 */
@SuppressWarnings("unused")
public class ChoiceCommand extends ParallelRaceGroup {

    /**
     * Each pair represents a condition to check, and the command to execute if that condition is
     * true
     *
     * @param cs The pair of BooleanSupplier (function that returns true/false) and Command to
     *           execute if the function is true
     */
    @SafeVarargs
    public ChoiceCommand(Pair<BooleanSupplier, Command>... cs) {
        super(
            Arrays
                .stream(cs)
                .<Command>map(p -> new ConditionalCommand(p.first, p.second))
                .collect(Collectors.toList())
                .toArray(new Command[] {})
        );
    }

    /**
     * This is a simplistic ChoiceCommand that is simply a single conditional command
     * I *think* this will wwait until b is true
     *
     * @param b The function to invoke to determine if the command should be run
     * @param c The command to be run
     */
    public ChoiceCommand(BooleanSupplier b, Command c) {
        this(new Pair<>(b, c));
    }
}
