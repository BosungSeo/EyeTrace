package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.traceeye.DeviceUtil;

public class StageView1 extends AbstractRenderView {
    private Point[] mObjects = new Point[10];
    private final int CIRCLE_SIZE = 15 * DeviceUtil.getInstance().getStageValue(4);
    private final int LINE_SIZE = 5* DeviceUtil.getInstance().getStageValue(4);
    private final int SPEED = 15 * DeviceUtil.getInstance().getStageValue(0);

    public StageView1(Context context, ViewCallback callback) {
        super(context, callback);
        calPoint();
    }

    private void calPoint() {
        for (int i = 0; i < 10; i++) {
            mObjects[i] = new Point();
        }
        /*mObjects[0].x = 15;
        mObjects[0].y = 10;
        mObjects[1].x = 45;
        mObjects[1].y = 11;
        mObjects[2].x = 20;
        mObjects[2].y = 35;
        mObjects[3].x = 30;
        mObjects[3].y = 80;
        mObjects[4].x = 50;
        mObjects[4].y = 50;
        mObjects[5].x = 47;
        mObjects[5].y = 80;
        mObjects[6].x = 80;
        mObjects[6].y = 80;
        mObjects[7].x = 80;
        mObjects[7].y = 10;
        mObjects[8].x = 65;
        mObjects[8].y = 35;
        mObjects[9].x = 65;
        mObjects[9].y = 20;*/
        for(int x=0;x<10;x++) {
            mObjects[x].x = (int) (Math.random() * 80) + 10;
            mObjects[x].y = (int) (Math.random() * 80) + 10;
            mObjects[x].x = (DeviceUtil.getInstance().getDisplayWidth() / 100) * mObjects[x].x;
            mObjects[x].y = (DeviceUtil.getInstance().getDisplayHeight() / 100) * mObjects[x].y;
        }
        /*for (int j = 0; j < 10; j++) {
            mObjects[j].x = DeviceUtil.getInstance().getDisplayWidth() / 100 * mObjects[j].x;
            mObjects[j].y = DeviceUtil.getInstance().getDisplayHeight() / 100 * mObjects[j].y;
        }*/
    }
    protected void readyStartDrawImpl(Canvas canvas) {
        int x = DeviceUtil.getInstance().getDisplayWidth() / 2;
        int y = DeviceUtil.getInstance().getDisplayHeight() / 2;

        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y - 370, 80, mPaint);

        mPaint.setTextSize(100);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.parseColor("#000000"));
        canvas.drawText("Follow the blue dot as it", x, y - 600, mPaint);
        canvas.drawText("follows the path", x, y - 470, mPaint);
    }
    @Override
    protected void drawImpl(Canvas canvas) {
        mFrameCount++;
        if (mFrameCount >= SPEED * 10) {
            finish();
            goHome();
            return;
        }
        onDrawGuideLine(canvas);



        for (int i = 0; i < 10; i++) {
            mPaint.setColor(Color.YELLOW);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mObjects[i].x, mObjects[i].y, CIRCLE_SIZE, mPaint);

            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(10);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(mObjects[i].x, mObjects[i].y, CIRCLE_SIZE+3, mPaint);
        }

        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mObjects[mFrameCount / SPEED].x, mObjects[mFrameCount / SPEED].y, CIRCLE_SIZE, mPaint);

        mViewCallback.onRecodeTargetPosition(mTrackerX, mTrackerY, mObjects[mFrameCount / SPEED].x, mObjects[mFrameCount / SPEED].y);
    }

    private void onDrawGuideLine(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#000000"));
        mPaint.setStrokeWidth(LINE_SIZE);
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(mObjects[i].x, mObjects[i].y, mObjects[i + 1].x, mObjects[i + 1].y, mPaint);
        }
    }
}
