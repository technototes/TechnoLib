package com.technototes.library.odometry;

import com.technototes.library.util.DoubleDifferential;
import com.technototes.library.util.PositionDifferential;

public interface DeadWheelOdometry extends Odometry {

    //FORWARD/BACK
//    @Override
//    default double getX(){
//
//    }
//
//    @Override
//    default double getY(){
//
//    }
//
//    @Override
//    default double getRotation(){
//
//    }

    DoubleDifferential getLeftPod();

    DoubleDifferential getRightPod();

    DoubleDifferential getCenterPod();

    PositionDifferential getPositionDifferential();




}
