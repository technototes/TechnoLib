package com.technototes.path.trajectorysequence;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.technototes.path.geometry.ConfigurablePose;
import com.technototes.path.geometry.ConfigurablePoseD;
import java.util.function.Function;

public interface TrajectoryPath extends Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence> {
    /**
     * Create a spline between two poses
     *
     * @param start The beginning of a spline
     * @param end The end of a spline
     * @return A function that takes a 'start' location and returns a TrajectorySequenceBuilder
     */
    static TrajectoryPath splineTo(ConfigurablePose start, ConfigurablePose end) {
        return b -> b.apply(start.toPose()).splineTo(end.toVec(), end.getHeading()).build();
    }

    /**
     * Create a spline between two poses
     *
     * @param start The beginning of a spline
     * @param end The end of a spline
     * @return A function that takes a 'start' location and returns a TrajectorySequenceBuilder
     */
    static TrajectoryPath splineTo(ConfigurablePoseD start, ConfigurablePoseD end) {
        return b -> b.apply(start.toPose()).splineTo(end.toVec(), end.getHeading()).build();
    }

    /**
     * Create a spline between a list of poses
     *
     * @param start The beginning of a spline
     * @param points The remaing points of the spline
     * @return A function that takes a 'start' location and returns a TrajectorySequenceBuilder
     */
    static TrajectoryPath splinesTo(ConfigurablePoseD start, ConfigurablePoseD... points) {
        return b -> {
            TrajectorySequenceBuilder bld = b.apply(start.toPose());
            for (ConfigurablePoseD d : points) {
                bld = bld.splineTo(d.toVec(), d.getHeading());
            }
            return bld.build();
        };
    }

    /**
     * Create a spline between a list of poses
     *
     * @param start The beginning of a spline
     * @param points The remaing points of the spline
     * @return A function that takes a 'start' location and returns a TrajectorySequenceBuilder
     */
    static TrajectoryPath splinesTo(ConfigurablePose start, ConfigurablePose... points) {
        return b -> {
            TrajectorySequenceBuilder bld = b.apply(start.toPose());
            for (ConfigurablePose d : points) {
                bld = bld.splineTo(d.toVec(), d.getHeading());
            }
            return bld.build();
        };
    }

    /**
     * Create a line between two poses
     *
     * @param start The beginning of the line
     * @param end The end of the line
     * @return A function that takes a 'start' location and returns a TrajectorySequenceBuilder
     */
    static TrajectoryPath lineTo(ConfigurablePose start, ConfigurablePose end) {
        return b -> b.apply(start.toPose()).lineTo(end.toPose().vec()).build();
    }

    /**
     * Create a line between two poses
     *
     * @param start The beginning of the line
     * @param end The end of the line
     * @return A function that takes a 'start' location and returns a TrajectorySequenceBuilder
     */
    static TrajectoryPath lineTo(ConfigurablePoseD start, ConfigurablePoseD end) {
        return b -> b.apply(start.toPose()).lineTo(end.toPose().vec()).build();
    }

    /**
     * Create a line between a list of poses
     *
     * @param start The beginning of the line
     * @param points The points of the line
     * @return A function that takes a 'start' location and returns a TrajectorySequenceBuilder
     */
    static TrajectoryPath linesTo(ConfigurablePoseD start, ConfigurablePoseD... points) {
        return b -> {
            TrajectorySequenceBuilder bld = b.apply(start.toPose());
            for (ConfigurablePoseD d : points) {
                bld = bld.lineTo(d.toPose().vec());
            }
            return bld.build();
        };
    }

    /**
     * Create a line between a list of poses
     *
     * @param start The beginning of the line
     * @param points The points of the line
     * @return A function that takes a 'start' location and returns a TrajectorySequenceBuilder
     */
    static TrajectoryPath linesTo(ConfigurablePose start, ConfigurablePose... points) {
        return b -> {
            TrajectorySequenceBuilder bld = b.apply(start.toPose());
            for (ConfigurablePose d : points) {
                bld = bld.lineTo(d.toPose().vec());
            }
            return bld.build();
        };
    }

    /**
     * Create a line between two poses on a specific linear heading
     *
     * @param start The beginning of the line
     * @param end The end of the line
     * @return A function that takes a 'start' location and returns a TrajectorySequenceBuilder
     */
    static TrajectoryPath lineToLinearHeading(ConfigurablePose start, ConfigurablePose end) {
        return b -> b.apply(start.toPose()).lineToLinearHeading(end.toPose()).build();
    }

    /**
     * Create a line between two poses on a specific linear heading
     *
     * @param start The beginning of the line
     * @param end The end of the line
     * @return A function that takes a 'start' location and returns a TrajectorySequenceBuilder
     */
    static TrajectoryPath lineToLinearHeading(ConfigurablePoseD start, ConfigurablePoseD end) {
        return b -> b.apply(start.toPose()).lineToLinearHeading(end.toPose()).build();
    }

    /**
     * Create a set of lines between a list of  poses on a specific linear heading
     *
     * @param start The beginning of the line
     * @param points The list of points in the line
     * @return A function that takes a 'start' location and returns a TrajectorySequenceBuilder
     */
    static TrajectoryPath linesToLinearHeadings(ConfigurablePoseD start, ConfigurablePoseD... points) {
        return b -> {
            TrajectorySequenceBuilder bld = b.apply(start.toPose());
            for (ConfigurablePoseD d : points) {
                bld = bld.lineToLinearHeading(d.toPose());
            }
            return bld.build();
        };
    }

    /**
     * Create a set of lines between a list of  poses on a specific linear heading
     *
     * @param start The beginning of the line
     * @param points The list of points in the line
     * @return A function that takes a 'start' location and returns a TrajectorySequenceBuilder
     */
    static TrajectoryPath linesToLinearHeadings(ConfigurablePose start, ConfigurablePose... points) {
        return b -> {
            TrajectorySequenceBuilder bld = b.apply(start.toPose());
            for (ConfigurablePose d : points) {
                bld = bld.lineToLinearHeading(d.toPose());
            }
            return bld.build();
        };
    }
}
