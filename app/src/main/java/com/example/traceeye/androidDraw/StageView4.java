package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.example.traceeye.DeviceUtil;

public class StageView4 extends AbstractRenderView {
    private Point mPoint;
    private final int SPEED = 30;
    private final int MARGIN = 200;
    private int mGoSpeedX;
    private int mGoSpeedY;
    private Rect mBound;
    private Point[] mGoToPoint = new Point[5];
    private int direction = 0;
    private int mFrameCount = 0;

    public StageView4(Context context, ViewCallback callback) {
        super(context, callback);

        mPoint = new Point(DeviceUtil.getInstance().getDisplayWidth() / 2, DeviceUtil.getInstance().getDisplayHeight() / 2);

        mBound = new Rect(MARGIN, MARGIN,
                DeviceUtil.getInstance().getDisplayWidth() - MARGIN, DeviceUtil.getInstance().getDisplayHeight() - MARGIN);
        mGoToPoint[0] = new Point(DeviceUtil.getInstance().getDisplayWidth() / 2, DeviceUtil.getInstance().getDisplayHeight() / 2);
        mGoToPoint[1] = new Point(DeviceUtil.getInstance().getDisplayWidth()-MARGIN, DeviceUtil.getInstance().getDisplayHeight() / 2);
        mGoToPoint[2] = new Point(MARGIN, DeviceUtil.getInstance().getDisplayHeight() / 2);
        mGoToPoint[3] = new Point(DeviceUtil.getInstance().getDisplayWidth() / 2, MARGIN);
        mGoToPoint[4] = new Point(DeviceUtil.getInstance().getDisplayWidth() / 2, DeviceUtil.getInstance().getDisplayHeight()-MARGIN);
    }
    private boolean calSpeed() {
        if(direction>3) {
            return false;
        }
        mPoint.x = mGoToPoint[direction].x;
        mPoint.y = mGoToPoint[direction++].y;
        mGoSpeedX = mGoToPoint[direction].x - mPoint.x;
        mGoSpeedY = mGoToPoint[direction].y - mPoint.y;
        mGoSpeedX = mGoSpeedX/SPEED;
        mGoSpeedY = mGoSpeedY/SPEED;
        return true;
    }

    protected void drawImpl(Canvas canvas) {
        if(mFrameCount%SPEED == 0) {
            if (!calSpeed()) {
                finish();
                goHome();
                return;
            }
        }
        mPoint.x += mGoSpeedX;
        mPoint.y += mGoSpeedY;

        mPaint.setColor(Color.parseColor("#FF0000"));
        canvas.drawCircle(mPoint.x, mPoint.y, 40, mPaint);
        mViewCallback.onRecodeTargetPosition(mTrackerX, mTrackerY, mPoint.x, mPoint.y);
        mFrameCount++;
    }
}
