package com.technototes.vision.subsystem;

import com.technototes.library.subsystem.Subsystem;
import com.technototes.vision.hardware.Camera;

import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
@SuppressWarnings("unused")
public class PipelineSubsystem implements Subsystem {

    protected Camera camera;

    public PipelineSubsystem(Camera c){
        camera = c;
    }

    public Camera getDevice() {
        return camera;
    }


    public PipelineSubsystem startStreaming(int width, int height) {
         camera.startStreaming(width, height);
         return this;
    }

    public PipelineSubsystem startStreaming(int width, int height, OpenCvCameraRotation rotation) {
        camera.startStreaming(width, height, rotation);
        return this;
    }

    public PipelineSubsystem stopStreaming(){
        camera.stopStreaming();
        return this;
    }

}
