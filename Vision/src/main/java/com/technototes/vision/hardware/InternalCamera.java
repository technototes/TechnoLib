package com.technototes.vision.hardware;

import com.technototes.library.hardware.DummyDevice;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera;

/**
 * This is a Camera for use with a phone's internal camera
 */
@SuppressWarnings("unused")
public class InternalCamera
    extends Camera<OpenCvInternalCamera, DummyDevice<OpenCvInternalCamera.CameraDirection>> {

    /**
     * Create the front-facing internal camera
     */
    public InternalCamera() {
        this(OpenCvInternalCamera.CameraDirection.FRONT);
    }

    /**
     * Create a camera (using the one on the phone for the CameraDirection)
     *
     * @param dir The direction (FRONT or REAR, probably)
     */
    public InternalCamera(OpenCvInternalCamera.CameraDirection dir) {
        super(new DummyDevice<>(dir));
        createCamera();
    }

    /**
     * Get which internal camera is being used
     *
     * @return FRONT or REAR, probably (TODO)
     */
    public OpenCvInternalCamera.CameraDirection getCameraDirection() {
        return getDevice().get();
    }

    /**
     * Create an OpenCvInternalCamera from this Camera
     *
     * @return the OpenCvInternalCamera device
     */
    @Override
    public OpenCvInternalCamera createCamera() {
        return OpenCvCameraFactory
            .getInstance()
            .createInternalCamera(
                getCameraDirection(),
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
