package com.technototes.vision.subsystem;

import com.technototes.library.subsystem.Subsystem;
import com.technototes.vision.hardware.Camera;
import org.openftc.easyopencv.OpenCvCameraRotation;

/**
 * A vision streaming pipeline to enable vision processing during an opmode
 */
@SuppressWarnings("unused")
public class PipelineSubsystem implements Subsystem {

    /**
     * The Camera object this subsystem is processing frames from
     */
    protected Camera camera;

    /**
     * Create the subsystem with the Camera provided
     *
     * @param c The camera to process frames from
     */
    public PipelineSubsystem(Camera c) {
        camera = c;
    }

    /**
     * Get the Camera device that frames are being processed from
     *
     * @return the Camera device in use
     */
    public Camera getDevice() {
        return camera;
    }

    /**
     * This begins streaming frames from the camera
     *
     * @param width  The width of the frame (constrained to a specific range, based on the camera device)
     * @param height The height of the frame (constrained based on the camera device)
     * @return this PipelineSubsystem (for chaining, I guess?)
     */
    public PipelineSubsystem startStreaming(int width, int height) {
        camera.startStreaming(width, height);
        return this;
    }

    /**
     * This begins streaming frames from the camera
     *
     * @param width    The width of the frame (constrained to a specific range, based on the camera device)
     * @param height   The height of the frame (consrained based on the camera device)
     * @param rotation The rotation of the frame to acquire
     * @return this PipelineSubsystem (for chaining, I guess?)
     */
    public PipelineSubsystem startStreaming(int width, int height, OpenCvCameraRotation rotation) {
        camera.startStreaming(width, height, rotation);
        return this;
    }

    /**
     * Stop frame processing
     *
     * @return this PipelineSubsystem (for chaining)
     */
    public PipelineSubsystem stopStreaming() {
        camera.stopStreaming();
        return this;
    }
}
