package com.technototes.vision.hardware;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

public class Webcam extends Camera<OpenCvWebcam, WebcamName> {

    public Webcam(WebcamName device) {
        super(device);
    }

    public Webcam(String device) {
        super(device);
    }


    @Override
    public OpenCvWebcam createCamera() {
        return OpenCvCameraFactory.getInstance().createWebcam(getDevice(),
                hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                        hardwareMap.appContext.getPackageName()));

    }

}
