package com.technototes.library.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleCommandTest {

    @BeforeEach
    public void setup() {
        CommandScheduler.resetScheduler();
    }

    @Test
    public void scheduleCommandNoCancel() {
        CommandForTesting command = new CommandForTesting();

        // Creating a command shouldn't cause it to be scheduled
        CommandScheduler.run();
        assertTrue(command.check(0, 0, 0, 0));
        CommandScheduler.schedule(command);
        // Scheduling a command won't cause it to run until after run()
        assertTrue(command.check(0, 0, 0, 0));
        CommandScheduler.run();
        // ?? The first run after scheduling a command doesn't do anything for the command
        // Yes because the first one puts the command into the state of initialization,
        // so that other commands can be scheduled off this command just starting
        // for parallel groups
        assertTrue(command.check(0, 0, 0, 0));
        CommandScheduler.run();

        /* KBF: This is a little odd. For reasons that are obvious in the code,
               the initialized state exists only before first execution, but not between command
               scheduler runs. The odd thing is that we have to run the command scheduler twice
               before the scheduler inits & executes the command. I should dig into this. Later.
        */

        // ?? The second run after scheduling a command initializes the command
        // see above
        // assertTrue(command.check(1, 0, 0, 0));
        CommandScheduler.run();
        // The third run after scheduling a command finally runs it
        assertTrue(command.check(1, 1, 0, 0));
        CommandScheduler.run();
        // The fourth run after scheduling a 'one-shot' command finally ends it
        assertTrue(command.check(1, 1, 1, 0));
        CommandScheduler.run();
        // An ended command doesn't get scheduled anymore
        assertTrue(command.check(1, 1, 1, 0));
        CommandScheduler.run();
        // An ended command doesn't get scheduled anymore
        // ?? But it does get initialized
        // when you schedule a command, its added to a loop.
        // just scheduling means the command will run again the moment it is finished
        // it might be smart to change this at some point because of larger loops in the loop set,
        // but would mean you have to loop anything that schedules a command, so same problem i think

        // KBF: Commented out: See comment above
        // assertTrue(command.check(2, 1, 1, 0));
        CommandScheduler.run();
        // An ended command doesn't get scheduled anymore
        // ?? But it does get initialized
        // ?? And executed??
        assertTrue(command.check(2, 2, 1, 0));
        CommandScheduler.run();
        // An ended command doesn't get scheduled anymore
        // ?? But it does get initialized
        // ?? And executed??
        // ?? And ends again?
        assertTrue(command.check(2, 2, 2, 0));
        CommandScheduler.run();
        assertTrue(command.check(2, 2, 2, 0));
        CommandScheduler.run();
        // KBF: Commented out, see comment above
        // assertTrue(command.check(3, 2, 2, 0));
        CommandScheduler.run();
        assertTrue(command.check(3, 3, 2, 0));
        CommandScheduler.run();
        assertTrue(command.check(3, 3, 3, 0));
    }
}
