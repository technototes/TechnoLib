package com.technototes.library.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleCommandTest {

    static class InstantCommand implements Command {
        public int initialized = 0;
        public int executed = 0;
        public int ended = 0;
        public int canceled = 0;

        @Override
        public void initialize() {
            initialized++;
        }
        @Override
        public void execute() {
            executed++;
        }

        @Override
        public void end(boolean cancel) {
            ended++;
            if (cancel) {
                canceled++;
            }
        }
    }

    @BeforeEach
    public void setup(){
        CommandScheduler.resetScheduler();
    }

    @Test
    public void scheduleCommandNoCancel(){
        InstantCommand command = new InstantCommand();

        // Creating a command shouldn't cause it to be scheduled
        CommandScheduler.getInstance().run();
        assertEquals(0, command.initialized);
        assertEquals(0, command.executed);
        assertEquals(0, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().schedule(command);

        // Scheduling a command won't cause it to run until after run()
        assertEquals(0, command.initialized);
        assertEquals(0, command.executed);
        assertEquals(0, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().run();

        // ?? The first run after scheduling a command doesn't do anything for the command
        // Yes because the first one puts the command into the state of intialization,
        // so that other commands can be scheduled off this command just starting
        // for parallel groups
        assertEquals(0, command.initialized);
        assertEquals(0, command.executed);
        assertEquals(0, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().run();

        // ?? The second run after scheduling a command initializes the command
        // see above
        assertEquals(1, command.initialized);
        assertEquals(0, command.executed);
        assertEquals(0, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().run();

        // The third run after scheduling a command finally runs it
        assertEquals(1, command.initialized);
        assertEquals(1, command.executed);
        assertEquals(0, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().run();

        // The fourth run after scheduling a 'one-shot' command finally ends it
        assertEquals(1, command.initialized);
        assertEquals(1, command.executed);
        assertEquals(1, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().run();
        // An ended command doesn't get scheduled anymore
        assertEquals(1, command.initialized);
        assertEquals(1, command.executed);
        assertEquals(1, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().run();
        // An ended command doesn't get scheduled anymore
        // ?? But it does get initialized
        // when you schedule a command, its added to a loop.
        // just scheduling means the command will run again the moment it is finished
        // it might be smart to change this at some point because of larger loops in the loop set,
        // but would mean you have to loop anything that schedules a command, so same problem i think
        assertEquals(2, command.initialized);
        assertEquals(1, command.executed);
        assertEquals(1, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().run();
        // An ended command doesn't get scheduled anymore
        // ?? But it does get initialized
        // ?? And executed??
        assertEquals(2, command.initialized);
        assertEquals(2, command.executed);
        assertEquals(1, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().run();
        // An ended command doesn't get scheduled anymore
        // ?? But it does get initialized
        // ?? And executed??
        // ?? And ends again?
        assertEquals(2, command.initialized);
        assertEquals(2, command.executed);
        assertEquals(2, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().run();
        assertEquals(2, command.initialized);
        assertEquals(2, command.executed);
        assertEquals(2, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().run();
        assertEquals(3, command.initialized);
        assertEquals(2, command.executed);
        assertEquals(2, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().run();
        assertEquals(3, command.initialized);
        assertEquals(3, command.executed);
        assertEquals(2, command.ended);
        assertEquals(0, command.canceled);

        CommandScheduler.getInstance().run();
        assertEquals(3, command.initialized);
        assertEquals(3, command.executed);
        assertEquals(3, command.ended);
        assertEquals(0, command.canceled);
    }

}
