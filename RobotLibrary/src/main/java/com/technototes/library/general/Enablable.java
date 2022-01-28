package com.technototes.library.general;

public interface Enablable<T extends Enablable<T>> {
    default T enable(){
        return setEnabled(true);
    }
    default T disable(){
        return setEnabled(false);
    }
    T setEnabled(boolean enable);
    default T toggleEnabled(){
        return setEnabled(!isEnabled());
    }
    boolean isEnabled();
    default boolean isDisabled(){
        return !isEnabled();
    }
}
