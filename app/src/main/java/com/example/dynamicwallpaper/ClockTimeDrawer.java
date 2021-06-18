package com.example.dynamicwallpaper;

import android.util.Log;
import android.view.SurfaceHolder;

public class ClockTimeDrawer extends AbsTimeDrawer {

    private static final String TAG = "ClockTimeDrawer";

    private final ClockDrawer clockDrawer;

    public ClockTimeDrawer(SurfaceHolder holder) {
        super(holder);
        clockDrawer = new ClockDrawer(holder.lockCanvas());
    }

    @Override
    public void startDraw() {
        Log.i(TAG, "startDraw: " + clockDrawer);
    }

    @Override
    public void stopDraw() {
    }

}
