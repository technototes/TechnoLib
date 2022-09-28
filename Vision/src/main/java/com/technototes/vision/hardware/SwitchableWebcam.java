package com.technototes.vision.hardware;


import com.technototes.library.hardware.DummyDevice;
import com.technototes.library.hardware.HardwareDevice;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvSwitchableWebcam;

import java.util.Arrays;
@SuppressWarnings("unused")
public class SwitchableWebcam extends Camera<OpenCvSwitchableWebcam, DummyDevice<WebcamName[]>>{

    private final Webcam[] devices;

    public SwitchableWebcam(WebcamName... device) {
        super(new DummyDevice<>(device));
        devices = Arrays.stream(device).map(Webcam::new).toArray(Webcam[]::new);
    }
    public SwitchableWebcam(String... device) {
        this(Arrays.stream(device).map((s)->hardwareMap.get(WebcamName.class, s)).toArray(WebcamName[]::new));
    }

    public SwitchableWebcam(Webcam... device) {
        this(Arrays.stream(device).map(HardwareDevice::getDevice).toArray(WebcamName[]::new));
    }

    @Override
    public OpenCvSwitchableWebcam createCamera() {
        return OpenCvCameraFactory.getInstance().createSwitchableWebcam(
                hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                        hardwareMap.appContext.getPackageName()), getDevice().get());
    }

    public SwitchableWebcam setActiveCamera(Webcam w){
        return setActiveCamera(w.getDevice());
    }

    public SwitchableWebcam setActiveCamera(WebcamName device) {
        openCvCamera.setActiveCamera(device);
        return this;
    }

    public SwitchableWebcam setActiveCamera(String device) {
        return setActiveCamera(hardwareMap.get(WebcamName.class, device));
    }


    public Webcam getActiveCamera(){
        for(Webcam c : devices){
            if(c.openCvCamera == openCvCamera){
                return c;
            }
        }
        return null;
    }

}
