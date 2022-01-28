package com.technototes.library.util;

public class Range {
    public double min, max;
    public Range(double mi, double ma){
        min = mi;
        max = ma;
    }

    public boolean inRange(double val){
        return val>=min && val<=max;
    }

    public void scale(double scalar){
        min*=scalar;
        max*=scalar;
    }

    public double middle(){
        return (min+max)/2;
    }
}
