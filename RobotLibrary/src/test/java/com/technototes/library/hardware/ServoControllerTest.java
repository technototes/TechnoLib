package com.technototes.library.hardware;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.hardware.servo.ServoController;

import org.junit.jupiter.api.Test;

public class ServoControllerTest {
    public ServoController servoController;
    public Servo servo;
    public ElapsedTime time;

    @Test
    public void test(){
        servo = new Servo(new MockServo()).setStartingPosition(0);
        servoController = new ServoController(servo).setConstraints(1, 0.4, 1);
        time = new ElapsedTime();
        time.reset();
        servoController.setTargetPosition(0.7);
        while(!servoController.isAtTarget()){
            System.out.println(time.seconds()+": "+servoController.update().getCurrentPosition());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
