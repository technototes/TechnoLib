package org.firstinspires.ftc.teamcode.strafer.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.technototes.library.command.Command;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.subsystem.drivebase.MecanumDrivebaseSubsystem;

public class DrivebaseSubsystem extends MecanumDrivebaseSubsystem {
    public DrivebaseSubsystem(Motor flMotor, Motor frMotor, Motor rlMotor, Motor rrMotor) {
        super(flMotor, frMotor, rlMotor, rrMotor);
    }
}
