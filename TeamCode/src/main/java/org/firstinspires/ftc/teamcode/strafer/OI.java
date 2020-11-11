package org.firstinspires.ftc.teamcode.strafer;

import com.technototes.library.command.InstantCommand;
import com.technototes.library.control.gamepad.CommandGamepad;
import com.technototes.subsystem.DrivebaseSubsystem.DriveSpeed;

public class OI{

    public Robot robot;
    public CommandGamepad driverGamepad, codriverGamepad;
    public OI(CommandGamepad g1, CommandGamepad g2, Robot r) {
        driverGamepad = g1;
        codriverGamepad = g2;
        robot = r;
        setDriverControls();
    }

    public void setDriverControls() {
//        CommandScheduler.getRunInstance().schedule(new MecanumDriveCommand(
//           robot.drivebaseSubsystem, driverGamepad.leftStick, driverGamepad.rightStick).setFieldCentric(robot.hardware.imu).addRequirements(robot.drivebaseSubsystem));
        driverGamepad.y.whenToggled(new InstantCommand(() -> robot.drivebaseSubsystem.driveSpeed = DriveSpeed.TURBO))
                .whenInverseToggled(new InstantCommand(() -> robot.drivebaseSubsystem.driveSpeed = DriveSpeed.NORMAL));
    }

}
