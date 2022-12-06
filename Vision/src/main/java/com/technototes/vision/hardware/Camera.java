package com.technototes.vision.hardware;

import android.graphics.Bitmap;
import com.technototes.library.hardware.HardwareDevice;
import java.util.function.IntConsumer;
import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.PipelineRecordingParameters;

/**
 * This is an OpenCVCamera interface using the FTC SDK camera hardware
 *
 * @param <T> The OpenCvCamera type
 * @param <U> The HardwareDevice type
 */
public abstract class Camera<
    T extends OpenCvCamera, U extends com.qualcomm.robotcore.hardware.HardwareDevice
>
    extends HardwareDevice<U>
    implements OpenCvCamera {

    /**
     * This is the OpenCvCamera object created
     */
    protected T openCvCamera;

    /**
     * Create a Camera device from the HardwareDevice provided
     *
     * @param device The HardwareDevice for the camera (from hardwareMap.get)
     */
    protected Camera(U device) {
        super(device);
        openCvCamera = createCamera();
    }

    /**
     * Create a Camera device from the HardwareDevice provided
     *
     * @param device The name of the HardwareDevice for the camera (probably: TODO)
     */
    protected Camera(String device) {
        super(device);
        openCvCamera = createCamera();
    }

    /**
     * get the camera created
     *
     * @return the OpenCvCamera device created
     */
    public T getOpenCvCamera() {
        return openCvCamera;
    }

    /**
     * Create the camera: Child classes need to implement this!
     *
     * @return the OpenCvCamera device created
     */
    abstract T createCamera();

    @Override
    @Deprecated
    public int openCameraDevice() {
        return getOpenCvCamera().openCameraDevice();
    }

    @Override
    public void openCameraDeviceAsync(AsyncCameraOpenListener cameraOpenListener) {
        getOpenCvCamera().openCameraDeviceAsync(cameraOpenListener);
    }

    /**
     * Invokes the Runnable's run() method when the camera has been opened,
     * and calls the IntConsumer's accept() method if an error occurs
     *
     * @param open  A Runnable to be notified with the camera is opened
     * @param error An IntConsumer error handler if an error occurs
     */
    public void openCameraDeviceAsync(Runnable open, IntConsumer error) {
        getOpenCvCamera()
            .openCameraDeviceAsync(
                new AsyncCameraOpenListener() {
                    @Override
                    public void onOpened() {
                        open.run();
                    }

                    @Override
                    public void onError(int errorCode) {
                        error.accept(errorCode);
                    }
                }
            );
    }

    /**
     * Invokes the Runnable's run() method when the camera has been opened.
     * This has no error handling: Good luck, folks!
     *
     * @param open A Runnable to be notified with the camera is opened
     */
    public void openCameraDeviceAsync(Runnable open) {
        openCameraDeviceAsync(open, i -> {});
    }

    @Override
    public void closeCameraDevice() {
        getOpenCvCamera().closeCameraDevice();
    }

    @Override
    public void closeCameraDeviceAsync(AsyncCameraCloseListener cameraCloseListener) {
        getOpenCvCamera().closeCameraDeviceAsync(cameraCloseListener);
    }

    @Override
    public void showFpsMeterOnViewport(boolean show) {
        getOpenCvCamera().showFpsMeterOnViewport(show);
    }

    @Override
    public void pauseViewport() {
        getOpenCvCamera().pauseViewport();
    }

    @Override
    public void resumeViewport() {
        getOpenCvCamera().resumeViewport();
    }

    @Override
    public void setViewportRenderingPolicy(ViewportRenderingPolicy policy) {
        getOpenCvCamera().setViewportRenderingPolicy(policy);
    }

    @Override
    public void setViewportRenderer(ViewportRenderer renderer) {
        getOpenCvCamera().setViewportRenderer(renderer);
    }

    @Override
    public void startStreaming(int width, int height) {
        getOpenCvCamera().startStreaming(width, height);
    }

    @Override
    public void startStreaming(int width, int height, OpenCvCameraRotation rotation) {
        getOpenCvCamera().startStreaming(width, height, rotation);
    }

    @Override
    public void stopStreaming() {
        getOpenCvCamera().stopStreaming();
    }

    @Override
    public void setPipeline(OpenCvPipeline pipeline) {
        getOpenCvCamera().setPipeline(pipeline);
    }

    @Override
    public int getFrameCount() {
        return getOpenCvCamera().getFrameCount();
    }

    @Override
    public float getFps() {
        return getOpenCvCamera().getFps();
    }

    @Override
    public int getPipelineTimeMs() {
        return getOpenCvCamera().getPipelineTimeMs();
    }

    @Override
    public int getOverheadTimeMs() {
        return getOpenCvCamera().getOverheadTimeMs();
    }

    @Override
    public int getTotalFrameTimeMs() {
        return getOpenCvCamera().getTotalFrameTimeMs();
    }

    @Override
    public int getCurrentPipelineMaxFps() {
        return getOpenCvCamera().getCurrentPipelineMaxFps();
    }

    @Override
    public void startRecordingPipeline(PipelineRecordingParameters parameters) {
        getOpenCvCamera().startRecordingPipeline(parameters);
    }

    @Override
    public void stopRecordingPipeline() {
        getOpenCvCamera().stopRecordingPipeline();
    }

    @Override
    public void getFrameBitmap(Continuation<? extends Consumer<Bitmap>> continuation) {
        getOpenCvCamera().getFrameBitmap(continuation);
    }
}
