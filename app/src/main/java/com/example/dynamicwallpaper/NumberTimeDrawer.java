package com.example.dynamicwallpaper;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.SurfaceHolder;

import org.xutils.common.util.DensityUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.SimpleFormatter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NumberTimeDrawer extends AbsTimeDrawer {

    private Disposable drawTimeDisposable;

    private LinearGradient linearGradient;

    private int screenWidth, screenHeight;

    private final int[] colors = new int[]{Color.parseColor("#F2AFD0"),
            Color.parseColor("#D784AC"),
            Color.parseColor("#AAD8F0"),
            Color.parseColor("#89CAEC")
    };

    private final float[] positions = new float[]{0f, 0.5f, 0.5f, 1f};

    public NumberTimeDrawer(SurfaceHolder holder) {
        super(holder);
        paint.setTextSize(80);
        screenWidth = DensityUtil.getScreenWidth();
        screenHeight = DensityUtil.getScreenHeight();
        linearGradient = new LinearGradient(0, 200, screenWidth, screenHeight - 200, colors, positions, Shader.TileMode.CLAMP);
    }

    @Override
    public void startDraw() {
        if (drawTimeDisposable != null) return;
        drawTimeDisposable = Flowable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> draw());
    }

    @Override
    public void stopDraw() {
        if (drawTimeDisposable != null) {
            drawTimeDisposable.dispose();
        }
        drawTimeDisposable = null;
    }

    private void draw() {
        if (drawTimeDisposable == null) return;
        initCanvas();

        drawBg();
        drawTime();
        destroyCanvas();
    }

    private void initCanvas() {
        canvas = holder.lockCanvas();
    }

    private void destroyCanvas() {
        holder.unlockCanvasAndPost(canvas);
    }

    private void drawBg() {
        paint.setShader(linearGradient);
        canvas.drawRect(0, 0, DensityUtil.getScreenWidth(), DensityUtil.getScreenHeight(), paint);
        paint.setShader(null);
    }

    private void drawTime() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        String drawText = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(calendar.getTime());
        float startX = (DensityUtil.getScreenWidth() - paint.measureText(drawText)) / 2f;
        paint.setColor(Color.WHITE);
        canvas.drawText(drawText, startX, DensityUtil.getScreenHeight() / 2f, paint);
    }


}
