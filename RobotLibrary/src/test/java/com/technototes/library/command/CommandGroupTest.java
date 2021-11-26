package com.technototes.library.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandGroupTest {
    Command c1 = ()-> print(1),
            c2 = ()-> print(2),
            c3 = ()-> print(3),
            c4 = ()-> print(4),
            c5 = ()-> print(5);



    @BeforeEach
    public void setup(){
        CommandScheduler.resetScheduler();
    }
    @Test
    public void scheduleCommand(){
        CommandGroup g = new SequentialCommandGroup(c1, c2, c3, c4, c5);
        CommandScheduler.getInstance().schedule(g);
        for(int i = 0; i < 100; i++){
            CommandScheduler.getInstance().run();
            //System.out.println(e++);'
        }
    }

    public void print(int i){
        System.out.println(i);
    }
}
