package com.example.dynamicwallpaper;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.SurfaceHolder;

import org.xutils.common.util.DensityUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ClockTimeDrawer extends AbsTimeDrawer {

    private Disposable drawTimeDisposable;

    private LinearGradient linearGradient;

    private int screenWidth, screenHeight;

    private final int dialRadius = 400;

    private final int hourPointerLength = 200;

    private final int minPointerLength = 250;

    private final int secPointerLength = 250;

    private Calendar calendar = Calendar.getInstance(Locale.getDefault());

    private final int[] colors = new int[]{Color.parseColor("#F2AFD0"),
            Color.parseColor("#D784AC"),
            Color.parseColor("#AAD8F0"),
            Color.parseColor("#89CAEC")
    };

    private final float[] positions = new float[]{0f, 0.5f, 0.5f, 1f};

    public ClockTimeDrawer(SurfaceHolder holder) {
        super(holder);
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
        drawDial();
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
        if (linearGradient == null) {
            float[] positions = new float[]{0f, 0.5f, 0.5f, 1f};
            linearGradient = new LinearGradient(0, 200f, screenWidth, screenHeight - 200f, colors, positions, Shader.TileMode.CLAMP);
        }
        paint.setShader(linearGradient);
        canvas.drawPaint(paint);
        paint.setShader(null);
    }

    private void drawDial() {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#1C1C1C"));
        canvas.drawCircle(screenWidth / 2f, screenHeight / 2f, dialRadius, paint);

        float strokeWidth = 5f;
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.GRAY);
        canvas.save();
        for (int i = 0; i < 60; i++) {
            float startX = screenWidth / 2f + dialRadius;
            float startY = (screenHeight - strokeWidth) / 2f;
            if ((i % 5) == 0) {
                paint.setColor(Color.parseColor("#AEAEAE"));
                canvas.drawLine(startX, startY, startX - 70f, startY, paint);
            } else {
                paint.setColor(Color.parseColor("#5E5E5E"));
                canvas.drawLine(startX, startY, startX - 50f, startY, paint);
            }
            canvas.rotate(6, screenWidth / 2f, screenHeight / 2f);
        }
        canvas.restore();

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setColor(Color.parseColor("#373737"));
        canvas.drawCircle(screenWidth / 2f, screenHeight / 2f, dialRadius, paint);
    }


    private void drawTime() {
        calendar.setTime(new Date(System.currentTimeMillis()));
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        //时针
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(15);
        canvas.save();
        float pointerRotateDegrees = hour * 30 + minute * (30f / 60f) + second * (30f / 60f / 60f) - 90;
        canvas.rotate(pointerRotateDegrees, screenWidth / 2f, screenHeight / 2f);
        canvas.drawLine(screenWidth / 2f - 20, screenHeight / 2f, screenWidth / 2f + hourPointerLength, (screenHeight) / 2f, paint);
        canvas.restore();

        //分针
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        canvas.save();
        pointerRotateDegrees = minute * 6f + second * (6f / 60f) - 90;
        canvas.rotate(pointerRotateDegrees, screenWidth / 2f, screenHeight / 2f);
        canvas.drawLine(screenWidth / 2f - 20, screenHeight / 2f, screenWidth / 2f + minPointerLength, (screenHeight) / 2f, paint);
        canvas.restore();

        //秒
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        canvas.save();
        pointerRotateDegrees = second * 6f - 90;
        canvas.rotate(pointerRotateDegrees, screenWidth / 2f, screenHeight / 2f);
        canvas.drawLine(screenWidth / 2f - 20, screenHeight / 2f, screenWidth / 2f + secPointerLength, (screenHeight) / 2f, paint);
        canvas.restore();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(screenWidth / 2f, screenHeight / 2f, 10f, paint);

        paint.setColor(Color.RED);
        canvas.drawCircle(screenWidth / 2f, screenHeight / 2f, 5f, paint);
    }

}
