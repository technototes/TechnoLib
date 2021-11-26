package com.technototes.vision.hardware;


import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera;
@SuppressWarnings("unused")
public class InternalCamera extends Camera<OpenCvInternalCamera, OpenCvInternalCamera.CameraDirection> {


    public InternalCamera() {
        this(OpenCvInternalCamera.CameraDirection.FRONT);
   }

    public InternalCamera(OpenCvInternalCamera.CameraDirection dir) {
        super(dir);
        createCamera();
    }


    public OpenCvInternalCamera.CameraDirection getCameraDirection() {
        return getDevice();
    }

    @Override
    public OpenCvInternalCamera createCamera() {
        return OpenCvCameraFactory.getInstance().createInternalCamera(getDevice(),
                hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                        hardwareMap.appContext.getPackageName()));
    }

}
