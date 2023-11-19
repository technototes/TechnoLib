package com.technototes.library.logger.entry;

import com.technototes.library.util.Color;
import java.util.function.Supplier;

public class BooleanEntry extends Entry<Boolean> {

    private StringEntry trueEntry, falseEntry;

    public BooleanEntry(
        String n,
        Supplier<Boolean> s,
        int index,
        String wt,
        String wf,
        String tf,
        String ff
    ) {
        super(n, s, index);
        trueEntry = new StringEntry("", () -> wt, -1, tf);
        falseEntry = new StringEntry("", () -> wf, -1, ff);
    }

    @Override
    public String toString() {
        return (get() ? trueEntry : falseEntry).get();
    }
}
