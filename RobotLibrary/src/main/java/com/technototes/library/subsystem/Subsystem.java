package com.technototes.library.subsystem;

import com.technototes.library.command.Command;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.general.Periodic;

public interface Subsystem extends Periodic {
    default void register() {
        CommandScheduler.register(this);
    }

    default Subsystem setDefaultCommand(Command c) {
        CommandScheduler.scheduleDefault(c, this);
        return this;
    }

    default Command getDefaultCommand() {
        return CommandScheduler.getDefault(this);
    }

    @Override
    default void periodic() {}
}
