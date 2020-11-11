package org.firstinspires.ftc.teamcode.newcode;

import com.technototes.library.command.InstantCommand;
import com.technototes.library.control.gamepad.CommandGamepad;

import com.technototes.subsystem.DrivebaseSubsystem.DriveSpeed;

import org.firstinspires.ftc.teamcode.newcode.commands.claw.ClawRotateLeftCommand;
import org.firstinspires.ftc.teamcode.newcode.commands.claw.ClawRotateRightCommand;
import org.firstinspires.ftc.teamcode.newcode.commands.lift.LiftDownCommand;
import org.firstinspires.ftc.teamcode.newcode.commands.lift.LiftToBottomCommand;
import org.firstinspires.ftc.teamcode.newcode.commands.lift.LiftToLastBrickHeightCommand;
import org.firstinspires.ftc.teamcode.newcode.commands.lift.LiftUpCommand;

public class OI {
    public Robot robot;
    public CommandGamepad driverGamepad, codriverGamepad;
    public OI(CommandGamepad g1, CommandGamepad g2, Robot r) {
        driverGamepad = g1;
        codriverGamepad = g2;
        robot = r;
        setDriverControls();
        setCodriverControls();
    }

    public void setDriverControls() {
        driverGamepad.dpad.down.whenToggled(new InstantCommand(() -> robot.drivebaseSubsystem.driveSpeed = DriveSpeed.TURBO))
                .whenInverseToggled(new InstantCommand(() -> robot.drivebaseSubsystem.driveSpeed = DriveSpeed.NORMAL));
        driverGamepad.dpad.up.whenActivated(new InstantCommand(() -> robot.blockFlipperSubsystem.setPosition(0.15)))
                .whenDeactivated(new InstantCommand(() -> robot.blockFlipperSubsystem.setPosition(0.75)));

        driverGamepad.dpad.left.whenActivated(new InstantCommand(() -> robot.capstonePusherSubsystem.setSpeed(-1)))
                .whenDeactivated(new InstantCommand(() -> robot.capstonePusherSubsystem.setSpeed(0)));
        driverGamepad.dpad.right.whenActivated(new InstantCommand(() -> robot.capstonePusherSubsystem.setSpeed(1)))
                .whenDeactivated(new InstantCommand(() -> robot.capstonePusherSubsystem.setSpeed(0)));
    }

    public void setCodriverControls() {
        driverGamepad.rightTrigger.whenActivated(new InstantCommand(() -> robot.clawSubsystem.setPosition(1)));
        driverGamepad.leftTrigger.whenActivated(new InstantCommand(() -> robot.clawSubsystem.setPosition(0)));

        driverGamepad.leftBumper.whenActivated(new ClawRotateLeftCommand(robot.clawRotateSubsystem));
        driverGamepad.rightBumper.whenActivated(new ClawRotateRightCommand(robot.clawRotateSubsystem));

//        driverGamepad.back.whenActivated(new InstantCommand(() -> robot.slideSubsytem.setSpeed(0.5)))
//                .whenDeactivated(new InstantCommand(() -> robot.slideSubsytem.setSpeed(0)));
//        driverGamepad.start.whenActivated(new InstantCommand(() -> robot.slideSubsytem.setSpeed(-0.5)))
//                .whenDeactivated(new InstantCommand(() -> robot.slideSubsytem.setSpeed(0)));

        driverGamepad.y.whenActivated(new LiftUpCommand(robot.liftSubsystem));
        driverGamepad.x.whenActivated(new LiftDownCommand(robot.liftSubsystem));
        driverGamepad.a.whenActivated(new LiftToLastBrickHeightCommand(robot.liftSubsystem));
        driverGamepad.b.whenActivated(new LiftToBottomCommand(robot.liftSubsystem));
    }

}
