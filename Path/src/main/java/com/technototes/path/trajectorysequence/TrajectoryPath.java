package com.technototes.path.trajectorysequence;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.technototes.path.geometry.ConfigurablePose;
import com.technototes.path.geometry.ConfigurablePoseD;
import java.util.function.Function;

public interface TrajectoryPath extends Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence> {
    static TrajectoryPath lineTo(ConfigurablePose start, ConfigurablePose end) {
        return b -> b.apply(start.toPose()).lineTo(end.toPose().vec()).build();
    }

    static TrajectoryPath lineTo(ConfigurablePoseD start, ConfigurablePoseD end) {
        return b -> b.apply(start.toPose()).lineTo(end.toPose().vec()).build();
    }

    static TrajectoryPath linesTo(ConfigurablePoseD start, ConfigurablePoseD... points) {
        return b -> {
            TrajectorySequenceBuilder bld = b.apply(start.toPose());
            for (ConfigurablePoseD d : points) {
                bld = bld.lineTo(d.toPose().vec());
            }
            return bld.build();
        };
    }

    static TrajectoryPath linesTo(ConfigurablePose start, ConfigurablePose... points) {
        return b -> {
            TrajectorySequenceBuilder bld = b.apply(start.toPose());
            for (ConfigurablePose d : points) {
                bld = bld.lineTo(d.toPose().vec());
            }
            return bld.build();
        };
    }

    static TrajectoryPath lineToLinearHeading(ConfigurablePose start, ConfigurablePose end) {
        return b -> b.apply(start.toPose()).lineToLinearHeading(end.toPose()).build();
    }

    static TrajectoryPath lineToLinearHeading(ConfigurablePoseD start, ConfigurablePoseD end) {
        return b -> b.apply(start.toPose()).lineToLinearHeading(end.toPose()).build();
    }

    static TrajectoryPath linesToLinearHeadings(ConfigurablePoseD start, ConfigurablePoseD... points) {
        return b -> {
            TrajectorySequenceBuilder bld = b.apply(start.toPose());
            for (ConfigurablePoseD d : points) {
                bld = bld.lineToLinearHeading(d.toPose());
            }
            return bld.build();
        };
    }

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
