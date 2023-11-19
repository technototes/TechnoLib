package com.technototes.library.logger.entry;

import java.util.function.Supplier;

public class StringEntry extends Entry<String> {

    private String format;

    public StringEntry(String n, Supplier<String> s, int x, String f) {
        super(n, s, x);
        format = f;
    }

    @Override
    public String toString() {
        return String.format(format, get());
    }
}
