package com.technototes.library.util;

import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
public interface SmartConsumer<T> {

    Map<SmartConsumer<?>, Object> map = new HashMap<>();

    void consume(T t);

    default SmartConsumer<T> accept(T t){
        if(!t.equals(map.get(this))){
            consume(t);
            map.put(this, t);
        }
        return this;
    }

    default T getLast(){
        return (T) map.get(this);
    }


    static void reset(){
        map.clear();
    }

}