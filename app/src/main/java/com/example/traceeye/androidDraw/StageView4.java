package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.traceeye.DeviceUtil;

public class StageView4 extends AbstractRenderView {
    private final int SPEED = 15 * DeviceUtil.getInstance().getStageValue(3);
    private final int PADDING = 100;
    private final int SCENE = 16;
    private final int LAST_FRAME = SPEED * SCENE;
    private Point mPoint;
    private int mFrameCount = 0;
    private Point mCenter = new Point(DeviceUtil.getInstance().getDisplayWidth() / 2, DeviceUtil.getInstance().getDisplayHeight() / 2);
    private Point mLeft = new Point(PADDING, mCenter.y);
    private Point mRight = new Point(DeviceUtil.getInstance().getDisplayWidth() - PADDING, mCenter.y);
    private Point mUp = new Point(mCenter.x, PADDING);
    private Point mDown = new Point(mCenter.x, DeviceUtil.getInstance().getDisplayHeight() - PADDING);
    private int directionX = 0;
    private int directionY = 0;

    public StageView4(Context context, ViewCallback callback) {
        super(context, callback);
        mPoint = new Point(mCenter.x, mCenter.y);
    }

    public void calX1() {
        directionX = Math.round((float) ((mRight.x - mCenter.x) / SPEED));
        directionY = 0;

    }

    public void calX2() {
        directionX = Math.round((float) ((mCenter.x - mRight.x) / SPEED));
        directionY = 0;

    }

    public void calX3() {
        directionX = Math.round((float) ((mLeft.x - mCenter.x) / SPEED));
        directionY = 0;

    }

    public void calX4() {
        directionX = Math.round((float) ((mCenter.x - mLeft.x) / SPEED));
        directionY = 0;
    }

    public void calX5() {
        directionY = Math.round((float) ((mUp.y - mCenter.y) / SPEED));
        directionX = 0;
    }

    public void calX6() {
        directionY = Math.round((float) ((mCenter.y - mUp.y) / SPEED));
        directionX = 0;
    }

    public void calX7() {
        directionY = Math.round((float) ((mDown.y - mCenter.y) / SPEED));
        directionX = 0;
    }

    public void calX8() {
        directionY = Math.round((float) ((mCenter.y - mDown.y) / SPEED));
        directionX = 0;
    }

    protected void drawImpl(Canvas canvas) {
        if (mFrameCount > LAST_FRAME) {
            finish();
            goHome();
        }
        if (mFrameCount % SPEED == 0) {
            switch (mFrameCount / SPEED) {
                case 0:
                case 4:
                    calX1();
                    break;
                case 1:
                case 5:
                    calX2();
                    break;
                case 2:
                case 6:
                    calX3();
                    break;
                case 3:
                case 7:
                    calX4();
                    break;
                case 8:
                case 12:
                    calX5();
                    break;
                case 9:
                case 13:
                    calX6();
                    break;
                case 10:
                case 14:
                    calX7();
                    break;
                case 11:
                case 15:
                    calX8();
                    break;
            }
        }
        mPoint.x = mPoint.x + directionX;
        mPoint.y = mPoint.y + directionY;
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mPoint.x, mPoint.y, 80, mPaint);
        mViewCallback.onRecodeTargetPosition(mTrackerX, mTrackerY, mPoint.x, mPoint.y);
        mFrameCount++;
    }
}
