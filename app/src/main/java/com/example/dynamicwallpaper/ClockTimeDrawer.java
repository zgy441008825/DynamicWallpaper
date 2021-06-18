package com.example.dynamicwallpaper;

import android.view.SurfaceHolder;

public class ClockTimeDrawer extends AbsTimeDrawer {

    private final ClockDrawer clockDrawer;

    public ClockTimeDrawer(SurfaceHolder holder) {
        super(holder);
        clockDrawer = new ClockDrawer(holder.lockCanvas());
    }

    @Override
    public void startDraw() {
        clockDrawer.startDraw();
    }

    @Override
    public void stopDraw() {
        clockDrawer.stopDraw();
    }

}
