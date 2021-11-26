package com.technototes.library.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Command;
import com.qualcomm.robotcore.robocol.TelemetryMessage;
import com.qualcomm.robotcore.util.RobotLog;
import com.technototes.library.structure.CommandOpMode;

import org.firstinspires.ftc.robotcore.internal.network.NetworkConnectionHandler;
import org.firstinspires.ftc.robotcore.internal.network.RobotCoreCommandList;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;

public class OpModeTest {
    private OpMode opMode;
    private HardwareMap hardwareMap;
    private Gamepad g1, g2;

    public void setup(){
        opMode = new TestOpMode();
        hardwareMap = new HardwareMap(null);
        g1 = new Gamepad();
        g2 = new Gamepad();
        opMode.gamepad1 = g1;
        opMode.gamepad2 = g2;
        opMode.hardwareMap = hardwareMap;
    }

    public void run(){

    }

    public class TestOpMode extends CommandOpMode {
        @Override
        public void universalLoop() {
            System.out.println(getOpModeState());
        }
    }

    public boolean callToInitNeeded = false, callToStartNeeded = false, looping = false;

    public void runActiveOpMode() {
//
//        opMode.time = opMode.getRuntime();
//
//
//
//        if (callToInitNeeded) {
//
//            // The point about resetting the hardware is to have it in the same state
//            // every time for the *user's* code so that they can simplify their initialization
//            // logic. There's no point in bothering / spending the time for the default opmode.
//
//            opMode.resetStartTime();
//            opMode.internalPreInit();
//            opMode.init();
//            looping = false;
//            callToInitNeeded = false;
//        }
//
//        /*
//         * NOTE: it's important that we use else if here, to avoid more than one user method
//         * being called during any one iteration. That, in turn, is important to make sure
//         * that if the force-stop logic manages to capture rogue user code, we can cleanly
//         * terminate the opmode immediately, without any other user methods being called.
//         */
//
//        else if (callToStartNeeded) {
//            opMode.inter
//            opMode.start();
//            looping = true;
//            callToStartNeeded = false;
//        }
//
//        else if (!looping) {
//           opMode.init_loop();
//        } else if (looping) {
//            opMode.loop();
//        }
    }
}
