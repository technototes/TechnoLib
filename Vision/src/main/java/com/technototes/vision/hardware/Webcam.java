package com.technototes.vision.hardware;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

/**
 * Acquire webcamera is just a quit constructor away :)
 */
public class Webcam extends Camera<OpenCvWebcam, WebcamName> {

    /**
     * TODO: I'm not sure where WebcamName comes from. I should read up.
     *
     * @param device The WebcamName for the camera?
     */
    public Webcam(WebcamName device) {
        super(device);
    }

    /**
     * Create a webcam device configured on the device with the given name
     *
     * @param device The name of the device as configured in "Robot Configuration"
     */
    public Webcam(String device) {
        super(device);
    }

    /**
     * Create an OpenCvWebcam, which can then be used in your vision pipeline
     *
     * @return The OpenCvWebcam to use
     */
    @Override
    public OpenCvWebcam createCamera() {
        return OpenCvCameraFactory
            .getInstance()
            .createWebcam(
                getDevice(),
                hardwareMap.appContext
                    .getResources()
                    .getIdentifier(
                        "cameraMonitorViewId",
                        "id",
                        hardwareMap.appContext.getPackageName()
                    )
            );
    }
}
