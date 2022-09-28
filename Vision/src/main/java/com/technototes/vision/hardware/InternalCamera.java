package com.technototes.vision.hardware;


import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.technototes.library.hardware.DummyDevice;

import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera;
@SuppressWarnings("unused")
public class InternalCamera extends Camera<OpenCvInternalCamera, DummyDevice<OpenCvInternalCamera.CameraDirection>> {


    public InternalCamera() {
        this(OpenCvInternalCamera.CameraDirection.FRONT);
   }

    public InternalCamera(OpenCvInternalCamera.CameraDirection dir) {
        super(new DummyDevice<>(dir));
        createCamera();
    }


    public OpenCvInternalCamera.CameraDirection getCameraDirection() {
        return getDevice().get();
    }

    @Override
    public OpenCvInternalCamera createCamera() {
        return OpenCvCameraFactory.getInstance().createInternalCamera(getCameraDirection(),
                hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                        hardwareMap.appContext.getPackageName()));
    }

}
