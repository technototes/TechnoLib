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
        driverGamepad.dpad.up.whenPressed(new InstantCommand(() -> robot.blockFlipperSubsystem.setPosition(0.15)))
                .whenReleased(new InstantCommand(() -> robot.blockFlipperSubsystem.setPosition(0.75)));

        driverGamepad.dpad.left.whenPressed(new InstantCommand(() -> robot.capstonePusherSubsystem.setSpeed(-1)))
                .whenReleased(new InstantCommand(() -> robot.capstonePusherSubsystem.setSpeed(0)));
        driverGamepad.dpad.right.whenPressed(new InstantCommand(() -> robot.capstonePusherSubsystem.setSpeed(1)))
                .whenReleased(new InstantCommand(() -> robot.capstonePusherSubsystem.setSpeed(0)));
    }

    public void setCodriverControls() {
        driverGamepad.rightTrigger.whenPressed(new InstantCommand(() -> robot.clawSubsystem.setPosition(1)));
        driverGamepad.leftTrigger.whenPressed(new InstantCommand(() -> robot.clawSubsystem.setPosition(0)));

        driverGamepad.leftBumper.whenPressed(new ClawRotateLeftCommand(robot.clawRotateSubsystem));
        driverGamepad.rightBumper.whenPressed(new ClawRotateRightCommand(robot.clawRotateSubsystem));

//        driverGamepad.back.whenActivated(new InstantCommand(() -> robot.slideSubsytem.setSpeed(0.5)))
//                .whenDeactivated(new InstantCommand(() -> robot.slideSubsytem.setSpeed(0)));
//        driverGamepad.start.whenActivated(new InstantCommand(() -> robot.slideSubsytem.setSpeed(-0.5)))
//                .whenDeactivated(new InstantCommand(() -> robot.slideSubsytem.setSpeed(0)));

        driverGamepad.y.whenPressed(new LiftUpCommand(robot.liftSubsystem));
        driverGamepad.x.whenPressed(new LiftDownCommand(robot.liftSubsystem));
        driverGamepad.a.whenPressed(new LiftToLastBrickHeightCommand(robot.liftSubsystem));
        driverGamepad.b.whenPressed(new LiftToBottomCommand(robot.liftSubsystem));
    }

}
