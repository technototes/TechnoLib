package com.technototes.library.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConditionalCommandTest {

    public static boolean shouldRun = false;

    @BeforeEach
    public void setup() {
        CommandScheduler.resetScheduler();
    }

    @Test
    public void scheduleCommandNoCancel() {
        CommandForTesting command = new CommandForTesting();
        shouldRun = true;
        Command toSchedule = new ConditionalCommand(() -> shouldRun, command);
        // Creating a command shouldn't cause it to be scheduled
        CommandScheduler.run();
        assertTrue(command.check(0, 0, 0, 0));
        CommandScheduler.schedule(toSchedule);
        // Scheduling a command won't cause it to run until after run()
        assertTrue(command.check(0, 0, 0, 0));
        CommandScheduler.run();
        // ?? The first run after scheduling a command doesn't do anything for the command
        // Yes because the first one puts the command into the state of intialization,
        // so that other commands can be scheduled off this command just starting
        // for parallel groups
        assertTrue(command.check(0, 0, 0, 0));
        CommandScheduler.run();
        // ?? The first run after scheduling a command doesn't do anything for the command
        // Yes because the first one puts the command into the state of intialization,
        // so that other commands can be scheduled off this command just starting
        // for parallel groups
        assertTrue(command.check(0, 0, 0, 0));
        CommandScheduler.run();
        // ?? The second run after scheduling a command initializes the command
        // see above
        assertTrue(command.check(1, 0, 0, 0));
        CommandScheduler.run();
        // The third run after scheduling a command finally runs it
        assertTrue(command.check(1, 1, 0, 0));
        CommandScheduler.run();
        // The fourth run after scheduling a 'one-shot' command finally ends it
        assertTrue(command.check(1, 1, 1, 0));
        CommandScheduler.run();
        // An ended command doesn't get scheduled anymore
        assertTrue(command.check(1, 1, 1, 0));
        shouldRun = false;
        CommandScheduler.run();
        // An ended command doesn't get scheduled anymore
        // ?? But it does get initialized
        // when you schedule a command, its added to a loop.
        // just scheduling means the command will run again the moment it is finished
        // it might be smart to change this at some point because of larger loops in the loop set,
        // but would mean you have to loop anything that schedules a command, so same problem i think

        // An ended command doesn't get scheduled anymore
        assertTrue(command.check(1, 1, 1, 0));
        CommandScheduler.run();
        assertTrue(command.check(1, 1, 1, 0));
        CommandScheduler.run();
        assertTrue(command.check(1, 1, 1, 0));
        CommandScheduler.run();
        assertTrue(command.check(1, 1, 1, 0));
        CommandScheduler.run();
    }
}
