package com.technototes.library.command;

import static org.junit.jupiter.api.Assertions.*;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CancelCommandTest {

    class cmd implements Command {

        @Override
        public void execute() {
            //        System.out.println(getRuntime().seconds());
        }

        @Override
        public boolean isFinished() {
            return false;
        }
    }

    @BeforeEach
    public void setup() {
        CommandScheduler.resetScheduler();
    }

    @Test
    public void scheduleCommand() {
        Command c = new cmd();
        ElapsedTime t = new ElapsedTime();
        t.reset();
        CommandScheduler.scheduleOnce(c.cancelUpon(() -> c.getRuntime().seconds() > 1));
        int finCount = 0;
        while (t.seconds() < 5.5) {
            CommandScheduler.run();
            if (c.justFinished()) finCount++; //System.out.println("finish");
            // System.out.println(e++);
        }
        assertEquals(5, finCount);
    }
}
