package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.example.traceeye.DeviceUtil;

public class StartView extends AbstractRenderView {
    private float i1;
    private float i2;
    private float i3;
    private Point mCP;
    private final float SELECTION_SPEED = 7.2f;
    private final int RED_CIRCLE_SIZE = 200;

    public StartView(Context context, ViewCallback callback) {
        super(context, callback);
        mCP = new Point(DeviceUtil.getInstance().getDisplayWidth() / 2, DeviceUtil.getInstance().getDisplayHeight() / 2);
    }

    protected void drawImpl(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#0000FF"));
        canvas.drawCircle(mTrackerX, mTrackerY, 10, mPaint);
        mPaint.setTextSize(50);
        mPaint.setColor(Color.parseColor("#000000"));
        canvas.drawText("Test-1, Test-2, Go Home-H", mCP.x - 100, 300, mPaint);
        canvas.drawText("1.", mCP.x - 100, mCP.y - 600, mPaint);
        canvas.drawText("Home", mCP.x - 100, mCP.y + 600, mPaint);
        draw1(canvas, new Point(mCP.x, mCP.y - 600));
        draw2(canvas, new Point(mCP.x, mCP.y));
        draw3(canvas, new Point(mCP.x, mCP.y + 600));

    }

    private void draw1(Canvas canvas, Point cp) {
        if (mTrackerX > cp.x - RED_CIRCLE_SIZE && mTrackerX < cp.x + RED_CIRCLE_SIZE
                && mTrackerY > cp.y - RED_CIRCLE_SIZE && mTrackerY < cp.y + RED_CIRCLE_SIZE) {
            i1 += SELECTION_SPEED;
        } else i1 = 0.0f;

        mPaint.setStrokeWidth(6f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#2F9D27"));
        canvas.drawCircle(cp.x,
                cp.y, RED_CIRCLE_SIZE, mPaint);
        RectF rect = new RectF();
        mPaint.setColor(Color.parseColor("#FF0000"));
        rect.set(cp.x - RED_CIRCLE_SIZE, cp.y - RED_CIRCLE_SIZE
                , cp.x + RED_CIRCLE_SIZE, cp.y + RED_CIRCLE_SIZE);
        canvas.drawArc(rect, 270, i1, false, mPaint);


        if (i1 > 360f) {
            mViewCallback.onNext(1);
        }
    }

    private void draw2(Canvas canvas, Point cp) {
        if (mTrackerX > cp.x - RED_CIRCLE_SIZE && mTrackerX < cp.x + RED_CIRCLE_SIZE
                && mTrackerY > cp.y - RED_CIRCLE_SIZE && mTrackerY < cp.y + RED_CIRCLE_SIZE) {
            i2 += SELECTION_SPEED;
        } else i2 = 0.0f;

        mPaint.setStrokeWidth(6f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#2F9D27"));
        canvas.drawCircle(cp.x,
                cp.y, RED_CIRCLE_SIZE, mPaint);
        RectF rect = new RectF();
        mPaint.setColor(Color.parseColor("#FF0000"));
        rect.set(cp.x - RED_CIRCLE_SIZE, cp.y - RED_CIRCLE_SIZE
                , cp.x + RED_CIRCLE_SIZE, cp.y + RED_CIRCLE_SIZE);
        canvas.drawArc(rect, 270, i2, false, mPaint);
        if (i2 > 360f) {
            mViewCallback.onNext(2);
        }
    }

    private void draw3(Canvas canvas, Point cp) {
        if (mTrackerX > cp.x - RED_CIRCLE_SIZE && mTrackerX < cp.x + RED_CIRCLE_SIZE
                && mTrackerY > cp.y - RED_CIRCLE_SIZE && mTrackerY < cp.y + RED_CIRCLE_SIZE) {
            i3 += SELECTION_SPEED;
        } else i3 = 0.0f;

        mPaint.setStrokeWidth(6f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#2F9D27"));
        canvas.drawCircle(cp.x,
                cp.y, RED_CIRCLE_SIZE, mPaint);
        RectF rect = new RectF();
        mPaint.setColor(Color.parseColor("#FF0000"));
        rect.set(cp.x - RED_CIRCLE_SIZE, cp.y - RED_CIRCLE_SIZE
                , cp.x + RED_CIRCLE_SIZE, cp.y + RED_CIRCLE_SIZE);
        canvas.drawArc(rect, 270, i3, false, mPaint);
        if (i3 > 360f) {
            mViewCallback.onNext(3);
        }
    }
}
