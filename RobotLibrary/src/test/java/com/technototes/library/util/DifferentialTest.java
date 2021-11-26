package com.technototes.library.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.DoubleConsumer;

public class DifferentialTest {
    public Differential differential;

    public double d1, d2;
    public DoubleConsumer i1 = d->d1=d;
    public DoubleConsumer i2 = d->d2=d;

    @BeforeEach
    public void setup(){
        differential = new Differential(i1, i2);
    }
    @Test
    public void test(){
        check(0, 0);
        differential.setOutputs(0.2, 0.2);
        check(0.4, 0);
        differential.setAverageOutput(0.9);
        check(1, 0.7);

        differential.setPriority(Differential.DifferentialPriority.AVERAGE);
        check(1, 0.8);

        differential.setPriority(Differential.DifferentialPriority.DIFFERENCE);
        check(1, 0.6);


        differential.setOutputs(-0.4, -0.7);
        differential.setPriority(Differential.DifferentialPriority.NEUTRAL);
        check(-1, 0.3);

        differential.setPriority(Differential.DifferentialPriority.AVERAGE);
        check(-1, 0.2);

        differential.setPriority(Differential.DifferentialPriority.DIFFERENCE);
        check(-1, 0.4);


        differential.setLimits(0, 1);
        differential.setPriority(Differential.DifferentialPriority.NEUTRAL);
        check(0, 0.3);


    }
    public void check(double a1, double a2){
        assert Math.abs(d1-a1)+Math.abs(d2-a2)<0.00001;
    }
}
