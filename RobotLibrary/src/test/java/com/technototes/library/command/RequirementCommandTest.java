package com.technototes.library.command;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.technototes.library.subsystem.Subsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RequirementCommandTest {
    public static class DummySubsystem implements Subsystem{
        public void method1(){
            System.out.print(1);
        }
        public void method2(){
            System.out.print(2);
        }
        public void method3(){
            System.out.print(3);
        }
    }
    public DummySubsystem subsystem;
    public Command command1, command2, command3;
    @BeforeEach
    public void setup(){
        subsystem = new DummySubsystem();
        CommandScheduler.resetScheduler();
        command1 = Command.create(subsystem::method1, subsystem);
        command2 = Command.create(subsystem::method2, subsystem);
        command3 = Command.create(subsystem::method3, subsystem);

    }
    @Test
    public void run(){
        int[] i = new int[1];
        CommandScheduler.getInstance().schedule(command1, ()->i[0]==0);
        CommandScheduler.getInstance().schedule(command2, ()->i[0]==1);
        CommandScheduler.getInstance().schedule(command3, ()->i[0]==2);


        for(i[0] = 0; i[0] < 100; i[0]++){
            CommandScheduler.getInstance().run();
            System.out.println(" - "+command1.getState()+" - "+command2.getState()+" - "+command3.getState());
        }
    }
}
