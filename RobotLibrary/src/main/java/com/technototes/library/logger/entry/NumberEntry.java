package com.technototes.library.logger.entry;

import com.technototes.library.util.Color;
import java.util.function.Supplier;

public class NumberEntry extends Entry<Number> {

    protected Color numberColor;

    public NumberEntry(String n, Supplier<Number> s, int x) {
        super(n, s, x);
    }

    @Override
    public String toString() {
        return numberColor.format(get());
    }
}
