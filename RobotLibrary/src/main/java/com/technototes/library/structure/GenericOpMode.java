package com.technototes.library.structure;

import com.technototes.library.logger.Loggable;

public class GenericOpMode<R, H, C> extends CommandOpMode implements Loggable {
    public R robot;
    public H hardware;
    public C controls;

    @Override
    public final void uponInit() {
        try {
            hardware = (H) hardware.getClass().getConstructor((Class<H>)Object.class).newInstance();
            robot = (R) robot.getClass().getConstructor((Class<R>)Object.class).newInstance(hardware);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
