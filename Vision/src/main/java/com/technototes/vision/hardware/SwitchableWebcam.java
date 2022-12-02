package com.technototes.vision.hardware;

import com.technototes.library.hardware.DummyDevice;
import com.technototes.library.hardware.HardwareDevice;
import java.util.Arrays;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvSwitchableWebcam;

/**
 * A webcam device interface that allows you to switch between multiple cameras!
 */
@SuppressWarnings("unused")
public class SwitchableWebcam extends Camera<OpenCvSwitchableWebcam, DummyDevice<WebcamName[]>> {

    private final Webcam[] devices;

    /**
     * Constructor to create the switchable webcam object
     *
     * @param device The list of WebcamName devices (TODO: I don't know what this is!)
     */
    public SwitchableWebcam(WebcamName... device) {
        super(new DummyDevice<>(device));
        devices = Arrays.stream(device).map(Webcam::new).toArray(Webcam[]::new);
    }

    /**
     * Constructor to create the switchable webcam object
     *
     * @param device The list of names of cameras as configured in the Robot configuration
     */
    public SwitchableWebcam(String... device) {
        this(
            Arrays
                .stream(device)
                .map(s -> hardwareMap.get(WebcamName.class, s))
                .toArray(WebcamName[]::new)
        );
    }

    /**
     * Constructor that takes already-created Webcam's so they can be accessed
     * through the switchable interface
     *
     * @param device The list of Webcam devices
     */
    public SwitchableWebcam(Webcam... device) {
        this(Arrays.stream(device).map(HardwareDevice::getDevice).toArray(WebcamName[]::new));
    }

    /**
     * Get the switchable webcam object to use with your pipeline
     *
     * @return the OpenCvSwitchableWebcam object
     */
    @Override
    public OpenCvSwitchableWebcam createCamera() {
        return OpenCvCameraFactory
            .getInstance()
            .createSwitchableWebcam(
                hardwareMap.appContext
                    .getResources()
                    .getIdentifier(
                        "cameraMonitorViewId",
                        "id",
                        hardwareMap.appContext.getPackageName()
                    ),
                getDevice().get()
            );
    }

    /**
     * Set the active camera (and return it!)
     *
     * @param w the Webcam object that was included in the constructor
     * @return the SwitchableWebcam object for the 'w' device
     */
    public SwitchableWebcam setActiveCamera(Webcam w) {
        return setActiveCamera(w.getDevice());
    }

    /**
     * Set the active camera (and return it!)
     *
     * @param device the WebcamName device that was included in the constructor (TODO!)
     * @return the SwitchableWebcam object for the 'w' device
     */
    public SwitchableWebcam setActiveCamera(WebcamName device) {
        openCvCamera.setActiveCamera(device);
        return this;
    }

    /**
     * Set the active camera (and return it!)
     *
     * @param device the Webcam name that was included in the constructor
     * @return the SwitchableWebcam object for the 'w' device
     */
    public SwitchableWebcam setActiveCamera(String device) {
        return setActiveCamera(hardwareMap.get(WebcamName.class, device));
    }

    /**
     * Get the currently selected camera
     *
     * @return The active camera: MAY BE NULL!
     */
    public Webcam getActiveCamera() {
        for (Webcam c : devices) {
            if (c.openCvCamera == openCvCamera) {
                return c;
            }
        }
        return null;
    }
}
