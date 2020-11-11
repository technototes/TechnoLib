package org.firstinspires.ftc.teamcode.newcode.subsystems;


import com.technototes.library.hardware.sensor.ColorSensor;
import com.technototes.library.subsystem.Subsystem;

public class ColorSensorSubsystem extends Subsystem<ColorSensor> {
    public ColorSensor sensor;
    public ColorSensorSubsystem(ColorSensor s){
        sensor = s;
    }
    public int getRed(){
        return sensor.red();
    }
    public int getGreen(){
        return sensor.green();
    }
    public int getBlue(){
        return sensor.blue();
    }

}
