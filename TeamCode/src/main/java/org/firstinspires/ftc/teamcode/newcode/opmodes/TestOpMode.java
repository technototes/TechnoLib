package org.firstinspires.ftc.teamcode.newcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.technototes.library.structure.CommandOpMode;
import com.technototes.logger.Loggable;

import org.firstinspires.ftc.teamcode.newcode.OI;
import org.firstinspires.ftc.teamcode.newcode.Robot;
@Disabled
@TeleOp(name = "test yay poggers")
public class TestOpMode extends CommandOpMode implements Loggable {

    public OI oi;

    public Robot robot;

    @Override
    public void uponInit() {
        robot = new Robot(hardwareMap, telemetry);
        oi = new OI(driverGamepad, codriverGamepad, robot);
        //  logger = new Logger(telemetry, this);
        //CommandScheduler.getRunInstance().schedule(new DriveCommand(robot.drivebaseSubsystem, driverGamepad.leftStick.x, driverGamepad.leftStick.y, driverGamepad.rightStick.x));

    }

    @Override
    public void runLoop() {
        robot.drivebaseSubsystem.joystickDriveWithGyro(driverGamepad.leftStick.getXAxis(), driverGamepad.leftStick.getYAxis(), driverGamepad.rightStick.getXAxis(), robot.hardware.imu.gyroHeading());
        //System.out.println(robot.hardware.gyroHeading2());
    }
}
