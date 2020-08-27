package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.traceeye.DeviceUtil;

public class StageView4 extends AbstractRenderView {
    private final int SPEED = 10 * DeviceUtil.getInstance().getStageValue(3);
    private final int PADDING = 100;
    private final int SCENE = 16;
    private final int LAST_FRAME = SPEED * SCENE;
    private Point mPoint;

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
        // Right
        directionX = SPEED;
        directionY = 0;
        if(mPoint.x>mRight.x) {
            directionX = 0;
            mFrameCount++;
        }
    }

    public void calX2() {
        // CENTER
        directionX = SPEED*-1;
        directionY = 0;
        if(mPoint.x<mCenter.x) {
            directionX = 0;
            mFrameCount++;
        }

    }

    public void calX3() {
        // LEFT
        directionX = SPEED*-1;
        directionY = 0;
        if(mPoint.x<mLeft.x) {
            directionX = 0;
            mFrameCount++;
        }

    }

    public void calX4() {
        //CENTER
        directionX = SPEED;
        directionY = 0;
        if(mPoint.x>mCenter.x) {
            directionX = 0;
            mFrameCount++;
        }
    }

    public void calX5() {
        // UP
        directionY = SPEED*-1;
        directionX = 0;
        if(mPoint.y<mUp.y) {
            directionY = 0;
            mFrameCount++;
        }
    }

    public void calX6() {
        // CENTER
        directionY = SPEED;
        directionX = 0;
        if(mPoint.y>mCenter.y) {
            directionY = 0;
            mFrameCount++;
        }
    }

    public void calX7() {
        // DOWN
        directionY = SPEED;
        directionX = 0;
        if(mPoint.y>mDown.y) {
            directionY = 0;
            mFrameCount++;
        }
    }

    public void calX8() {
        // CENTER
        directionY = SPEED*-1;
        directionX = 0;
        if(mPoint.y<mCenter.y) {
            directionY = 0;
            mFrameCount++;
        }
    }
    protected void readyStartDrawImpl(Canvas canvas) {
        int x = DeviceUtil.getInstance().getDisplayWidth() / 2;
        int y = DeviceUtil.getInstance().getDisplayHeight() / 2;
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(80);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Follow the dot as it moves", x, y - 600, mPaint);
        canvas.drawText("around the screen", x, y - 470, mPaint);
    }
    protected void drawImpl(Canvas canvas) {
        switch (mFrameCount) {
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
            case 16:
                finish();
                goHome();
                break;
        }

        mPoint.x = mPoint.x + directionX;
        mPoint.y = mPoint.y + directionY;
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mPoint.x, mPoint.y, 55, mPaint);
        mViewCallback.onRecodeTargetPosition(mTrackerX, mTrackerY, mPoint.x, mPoint.y);
    }
}
