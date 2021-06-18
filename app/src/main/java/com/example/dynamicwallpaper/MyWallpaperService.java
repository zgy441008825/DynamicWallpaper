package com.example.dynamicwallpaper;

import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

public class MyWallpaperService extends WallpaperService {

    private static final String TAG = "MyWallpaperService";

    private MyEngine myEngine;

    @Override
    public Engine onCreateEngine() {
        if (myEngine == null) {
            myEngine = new MyEngine();
        }
        return myEngine;
    }


    private class MyEngine extends Engine {

        private AbsTimeDrawer timeDrawer;

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            Log.i(TAG, "onSurfaceCreated: " + timeDrawer);
            if (timeDrawer == null) {
                timeDrawer = new ClockTimeDrawer(holder);
                timeDrawer.startDraw();
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }
    }
}