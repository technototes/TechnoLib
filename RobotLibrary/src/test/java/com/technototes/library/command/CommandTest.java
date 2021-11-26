package com.technototes.library.command;

import com.technototes.library.control.gamepad.CommandGamepad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandTest {

    Command c = this::act;


    public void act(){
        System.out.println("it do");
    }

    @BeforeEach
    public void setup(){
        CommandScheduler.resetScheduler();
    }
    @Test
    public void scheduleCommand(){
        long i = System.currentTimeMillis();
        int e = 0;
        CommandScheduler.getInstance().schedule(c.sleep(1));
        while(System.currentTimeMillis()-i < 10500){
            CommandScheduler.getInstance().run();
            if(c.justFinished()) System.out.println("finish");
            //System.out.println(e++);
        }
    }

}
