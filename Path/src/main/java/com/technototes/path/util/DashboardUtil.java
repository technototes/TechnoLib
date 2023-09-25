package com.technototes.path.util;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.roadrunner.CompositePosePath;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.Vector2d;

import java.util.List;


/**
 * Set of helper functions for drawing Road Runner paths and trajectories on dashboard canvases.
 */
public class DashboardUtil {

    private static final double DEFAULT_RESOLUTION = 2.0; // distance units; presumed inches
    private static final double ROBOT_RADIUS = 9; // in

    public static void drawPoseHistory(Canvas canvas, List<Pose2d> poseHistory) {
        double[] xPoints = new double[poseHistory.size()];
        double[] yPoints = new double[poseHistory.size()];
        for (int i = 0; i < poseHistory.size(); i++) {
            Pose2d pose = poseHistory.get(i);
            xPoints[i] = pose.position.x;
            yPoints[i] = pose.position.y;
        }
        canvas.strokePolyline(xPoints, yPoints);
    }

    public static void drawSampledPath(Canvas canvas, CompositePosePath path, double resolution) {
        int samples = (int) Math.ceil(path.length() / resolution);
        double[] xPoints = new double[samples];
        double[] yPoints = new double[samples];
        double dx = path.length() / (samples - 1);
        for (int i = 0; i < samples; i++) {
            double displacement = i * dx;
            Pose2dDual<?> pose = path.get(displacement, i);
            xPoints[i] = pose.position.x.value();
            yPoints[i] = pose.position.y.value();
        }
        canvas.strokePolyline(xPoints, yPoints);
    }

    public static void drawSampledPath(Canvas canvas, CompositePosePath path) {
        drawSampledPath(canvas, path, DEFAULT_RESOLUTION);
    }

    public static void drawRobot(Canvas canvas, Pose2d pose) {
        canvas.strokeCircle(pose.position.x, pose.position.y, ROBOT_RADIUS);
        Vector2d v = pose.heading.times(new Vector2d(ROBOT_RADIUS, ROBOT_RADIUS));
        double x1 = pose.position.x + v.x / 2;
        double y1 = pose.position.y + v.y / 2;
        double x2 = pose.position.x + v.x;
        double y2 = pose.position.y + v.y;
        canvas.strokeLine(x1, y1, x2, y2);
    }
}
