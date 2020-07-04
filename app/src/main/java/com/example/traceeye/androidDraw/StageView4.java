package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

import com.example.traceeye.DeviceUtil;

public class StageView4 extends AbstractRenderView {
    private Point mPoint;
    private int mRight;
    private final int SPEED = 10;
    private final int MARGIN = 30 + SPEED;
    private boolean direction = false;

    public StageView4(Context context, ViewCallback callback) {
        super(context, callback);
        mPoint = new Point(MARGIN, DeviceUtil.getInstance().getDisplayHeight() / 2);
        mRight = DeviceUtil.getInstance().getDisplayWidth() - MARGIN;
    }

    protected void drawImpl(Canvas canvas) {

        if (direction) {
            mPoint.x = mPoint.x - SPEED;
            if (mPoint.x < MARGIN) direction = false;
        } else {
            mPoint.x = mPoint.x + SPEED;
            if (mPoint.x > mRight) direction = true;
        }

        mPaint.setColor(Color.parseColor("#2F9D27"));
        canvas.drawCircle(mTrackerX, mTrackerY, 10, mPaint);

        mPaint.setColor(Color.parseColor("#FF0000"));
        canvas.drawCircle(mPoint.x, mPoint.y, 40, mPaint);
    }
}
