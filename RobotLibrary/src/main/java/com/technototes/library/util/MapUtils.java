package com.technototes.library.util;

import android.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

    @SafeVarargs
    public static <T, U> Map<T, U> of(Pair<T, U>... entries){
        Map<T, U> map = new HashMap<>();
        for(Pair<T, U> e : entries){
            map.put(e.first, e.second);
        }
        return map;
    }

}
