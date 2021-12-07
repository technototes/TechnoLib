package com.technototes.library.subsystem;

import com.technototes.library.command.Command;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.structure.Periodic;

public interface Subsystem extends Periodic {


    default Subsystem setDefaultCommand(Command c){
        CommandScheduler.getInstance().scheduleDefault(c, this);
        return this;
    }

    default Command getDefaultCommand(){
        return CommandScheduler.getInstance().getDefault(this);
    }


    default void periodic(){

    }
}
