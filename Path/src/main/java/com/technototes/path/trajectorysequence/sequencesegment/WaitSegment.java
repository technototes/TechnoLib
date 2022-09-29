package com.technototes.path.trajectorysequence.sequencesegment;

import java.util.List;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.TrajectoryMarker;

public final class WaitSegment extends SequenceSegment {
    public WaitSegment(Pose2d pose, double seconds, List<TrajectoryMarker> markers) {
        super(seconds, pose, pose, markers);
    }
}
