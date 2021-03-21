package com.technototes.library.hardware.sensor;
//TODO
public class GyroSensor extends Sensor<com.qualcomm.robotcore.hardware.GyroSensor> {

    public GyroSensor(com.qualcomm.robotcore.hardware.GyroSensor device) {
        super(device);
    }

    @Override
    public double getSensorValue() {
        return 0;
    }
}
