package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.traceeye.DeviceUtil;

public class StageView4 extends AbstractRenderView {
    private Point mPoint;
    private final int SPEED = 10;
    private final int MARGIN = 200;
    private int mGoSpeedX;
    private int mGoSpeedY;
    private Rect mBound;

    public StageView4(Context context, ViewCallback callback) {
        super(context, callback);

        mPoint = new Point(DeviceUtil.getInstance().getDisplayWidth() / 2, DeviceUtil.getInstance().getDisplayHeight() / 2);

        mBound = new Rect(MARGIN, MARGIN,
                DeviceUtil.getInstance().getDisplayWidth() - MARGIN, DeviceUtil.getInstance().getDisplayHeight() - MARGIN);
        mGoSpeedX = SPEED * -1;
        mGoSpeedY = SPEED;
    }


    protected void drawImpl(Canvas canvas) {
        mPoint.x += mGoSpeedX;
        mPoint.y += mGoSpeedY;
        if (mPoint.x < mBound.left) {
            mGoSpeedX = SPEED;
        }
        if (mPoint.x > mBound.right) {
            mGoSpeedX = SPEED * -1;
        }
        if (mPoint.y < mBound.top)
            mGoSpeedY = SPEED;
        if (mPoint.y > mBound.bottom)
            mGoSpeedY = SPEED * -1;

        mPaint.setColor(Color.parseColor("#FF0000"));
        canvas.drawCircle(mPoint.x, mPoint.y, 40, mPaint);
        mViewCallback.onRecodeTargetPosition(mTrackerX, mTrackerY, mPoint.x, mPoint.y);
    }
}
