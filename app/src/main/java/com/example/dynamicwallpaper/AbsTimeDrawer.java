package com.example.dynamicwallpaper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public abstract class AbsTimeDrawer {

    protected SurfaceHolder holder;

    protected Canvas canvas;

    protected Paint paint;

    public AbsTimeDrawer(SurfaceHolder holder) {
        this.holder = holder;
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public abstract void startDraw();

    public abstract void stopDraw();
}
