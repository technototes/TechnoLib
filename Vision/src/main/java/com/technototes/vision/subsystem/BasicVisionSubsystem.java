package com.technototes.vision.subsystem;

import com.technototes.library.subsystem.Subsystem;
import com.technototes.vision.HSVRange;
import com.technototes.vision.hardware.Camera;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

/**
 * A vision streaming pipeline to enable vision processing during an opmode
 */
@SuppressWarnings("unused")
public abstract class BasicVisionSubsystem extends OpenCvPipeline implements Subsystem {

    /**
     * The Camera object this subsystem is processing frames from
     */
    protected Camera camera;
    /**
     * The width and height of the image requested from the camera
     */
    protected int width, height;
    /**
     * The rotation applied to the image
     */
    protected OpenCvCameraRotation rotation;
    /**
     * The current frame being processed (can be drawn on!)
     */
    protected Mat curFrameRGB;
    /**
     * True iff the pipeline is properly set and running
     */
    protected boolean pipelineSet;

    /**
     * Create the subsystem with the Camera provided
     *
     * @param c The camera to process frames from
     * @param w The width of the camera image
     * @param h The height fo the camera image
     * @param rot The rotation of the camera image
     */
    public BasicVisionSubsystem(Camera c, int w, int h, OpenCvCameraRotation rot) {
        camera = c;
        width = w;
        height = h;
        rotation = rot;
        curFrameRGB = null;
        pipelineSet = false;
    }

    /**
     * Get the Camera device that frames are being processed from
     *
     * @return the Camera device in use
     */
    protected Camera getRawDevice() {
        return camera;
    }

    /**
     * Start the images coming from the camera
     */
    protected void beginStreaming() {
        camera.startStreaming(width, height, rotation);
    }

    /**
     * Turn on the camera and start visual processing
     */
    public void startVisionPipeline() {
        camera.setPipeline(this);
        pipelineSet = true;
        // The i -> lambda appears to be for *error reporting* so this line is silly:
        camera.openCameraDeviceAsync(this::beginStreaming, i -> startVisionPipeline());
    }

    /**
     * Turn off the camera and enable visual processing
     */
    public void stopVisionPipeline() {
        camera.setPipeline(null);
        pipelineSet = false;
        camera.closeCameraDeviceAsync(() -> {
            /* Do we need to do anything to stop the vision pipeline? */
        });
    }

    /**
     * Turn off the camera and stop visual processing
     *
     * @return this for chaining if you really want...
     */
    public BasicVisionSubsystem startStreaming() {
        beginStreaming();
        return this;
    }

    /**
     * Stop frame processing
     *
     * @return this PipelineSubsystem (for chaining)
     */
    public BasicVisionSubsystem stopStreaming() {
        if (pipelineSet) {
            stopVisionPipeline();
        }
        camera.stopStreaming();
        return this;
    }

    // These are the three functions you need to implement.
    // I use this so that you can edit your rectangles in realtime from the FtcDashboard.
    // If you don't use FtcDashboard, just make an array of Rect's and be done with it.
    // But really, you should be using FtcDashboard. It's much faster to get this right.

    /**
     * @return How many rectangles are you checking
     */
    public abstract int numRectangles();

    /**
     * Get the specific rectangle number
     *
     * @param rectNumber Which rectangle to return
     * @return The rectangle that will be processed
     */
    public abstract Rect getRect(int rectNumber);

    /**
     * Process the particular rectangle (you probably want to call countPixelsOfColor ;) )
     * @param inputHSV The HSV rectangle to process
     * @param rectNumber The rectangle this Mat is from within the overall image
     */
    public abstract void runDetection(Mat inputHSV, int rectNumber);

    /**
     * Override this to do something before a frame is processed
     */
    protected void detectionStart() {}

    /**
     * Override this to do something after a frame is processed
     */
    protected void detectionEnd() {}

    /**
     * Run processing on the frame provided.
     * This invokes detectionStart, numRectangles, getRect, runDetection, and detectionEnd.
     * You probably don't want to bother overriding it, unless you can't use the rectangle
     * processing facilty as is.
     *
     * @param frame The RGB frame from the camera
     */
    protected void detectionProcessing(Mat frame) {
        // Put the input matrix in a member variable, so that other functions can draw on it
        curFrameRGB = frame;
        detectionStart();
        int count = numRectangles();
        for (int i = 0; i < count; i++) {
            // First, slice the smaller rectangle out of the overall bitmap:
            Rect r = getRect(i);
            Mat subRectRGB = curFrameRGB.submat(r.y, r.y + r.height, r.x, r.x + r.width);

            // Next, convert the RGB image to HSV, because HUE is much easier to identify colors in
            // The output is in 'customColorSpace'
            Mat subRectHSV = new Mat();
            Imgproc.cvtColor(subRectRGB, subRectHSV, Imgproc.COLOR_RGB2HSV);
            runDetection(subRectHSV, i);
        }
        detectionEnd();
    }

    @Override
    public Mat processFrame(Mat input) {
        detectionProcessing(input);
        return input;
    }

    public void init(Mat firstFrame) {
        detectionProcessing(firstFrame);
    }

    /**
     * Count the number of pixels that are in the given range
     *
     * @param range The HSV range to look for
     * @param imgHSV A sub-rectangle to count pixels in
     * @param telemetryRGB The (optional) output RGB image (to help with debugging)
     * @param xOffset The x-offset fo the subrectangle within the output RGB image
     * @param yOffset The y-offset fo the subrectangle within the output RGB image
     * @return The number of pixels that were found to be within the specified range
     */
    protected int countPixelsOfColor(HSVRange range, Mat imgHSV, Mat telemetryRGB, int xOffset, int yOffset) {
        int totalColorCount = 0;
        // Since we might have a hue range of -15 to 15 to detect red,
        // make the range 165 to 180 and run countColorsInRect with just that range first
        HSVRange handleRedWrap = range.makeWrapAround();
        if (handleRedWrap != null) {
            totalColorCount += countPixelsOfColor(handleRedWrap, imgHSV, telemetryRGB, xOffset, yOffset);
            range = range.truncateRange();
        }
        Scalar low = range.lowEdge();
        Scalar high = range.highEdge();
        // Check to see which pixels are between low and high, output into a boolean matrix Cr
        Mat count = new Mat();
        Core.inRange(imgHSV, low, high, count);
        totalColorCount = Core.countNonZero(count);
        if (telemetryRGB != null) {
            // TODO: It seems like there should be a more optimized way to do this.
            for (int i = 0; i < count.width(); i++) {
                for (int j = 0; j < count.height(); j++) {
                    if (count.get(j, i)[0] > 0) {
                        // Draw a dots on the image at this point - input was put into img
                        // The color choice makes things stripey, which makes it easier to identify
                        double[] colorToDraw = ((j + i) & 2) != 0 ? low.val : high.val;
                        telemetryRGB.put(j + yOffset, i + xOffset, colorToDraw);
                    }
                }
            }
        }
        return totalColorCount;
    }
}
