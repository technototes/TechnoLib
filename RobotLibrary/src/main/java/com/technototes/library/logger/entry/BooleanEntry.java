package com.technototes.library.logger.entry;

import com.technototes.library.util.Color;
import java.util.function.Supplier;

public class BooleanEntry extends Entry<Boolean> {

    private String trueEntry, falseEntry;

    public BooleanEntry(String n, Supplier<Boolean> s, int index, String wt, String wf) {
        super(n, s, index);
        trueEntry = wt;
        falseEntry = wf;
    }

    @Override
    public String toString() {
        return (get() ? trueEntry : falseEntry);
    }
}
