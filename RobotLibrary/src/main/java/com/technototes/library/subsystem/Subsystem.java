package com.technototes.library.subsystem;

import com.technototes.library.command.Command;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.hardware.HardwareDevice;
import com.technototes.library.structure.CommandOpMode;

import java.util.HashMap;
import java.util.Map;

public interface Subsystem<T extends HardwareDevice> {

    Map<Subsystem<?>, Object> map = new HashMap<>();

    default Subsystem<T> setDefaultCommand(Command c){
        CommandScheduler.getInstance().scheduleDefault(c, this);
        return this;
    }

    default Command getDefaultCommand(){
        return CommandScheduler.getInstance().getDefault(this);
    }

    default void setDevice(T device){
        map.put(this, device);
    }

    default T getDevice(){
        return (T) map.get(this);
    }

    default void periodic(){

    }
    static void clear(){
        map.clear();
    }

}
