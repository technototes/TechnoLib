package com.technototes.library.util;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Integral {
    private double accumulator;
    private ElapsedTime deltaTime;

    public Integral(double c){
        accumulator = c;
        deltaTime = new ElapsedTime();
    }
    public Integral(){
        this(0);
    }

    public Integral set(double c){
        accumulator = c;
        return this;
    }
    public Integral zero(){
        return set(0);
    }

    public double update(double change){
        accumulator+=change*deltaTime.seconds();
        deltaTime.reset();
        return accumulator;
    }

    public double getValue(){
        return accumulator;
    }


}
