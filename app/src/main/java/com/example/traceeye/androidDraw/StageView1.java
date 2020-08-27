package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.example.traceeye.DeviceUtil;

public class StageView1 extends AbstractRenderView {
    private final int CIRCLE_SIZE = 15 * DeviceUtil.getInstance().getStageValue(4);
    private final int LINE_SIZE = 5 * DeviceUtil.getInstance().getStageValue(4);
    private final int SPEED = 15 * DeviceUtil.getInstance().getStageValue(0);
    private final int OBJECT_NUM = 10;
    private Point[] mObjects = new Point[OBJECT_NUM];

    public StageView1(Context context, ViewCallback callback) {
        super(context, callback);
        calPoint();
    }

    private void calPoint() {
        for (int i = 0; i < OBJECT_NUM; i++) {
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
        int destX;
        int destY;
        mObjects[0].x = 50;
        mObjects[0].y = 50;
        while (true) {
            destX = (int) (Math.random() * 80) + 10;
            destY = (int) (Math.random() * 80) + 10;
            int l = ((mObjects[0].x - destX) * (mObjects[0].x - destX)) + ((mObjects[0].y - destY) * (mObjects[0].y - destY));
            if (l < 1500 && l>800)
                break;
        }
        mObjects[1].x = destX;
        mObjects[1].y = destY;
        while (true) {
            destX = (int) (Math.random() * 80) + 10;
            destY = (int) (Math.random() * 80) + 10;
            int l = ((mObjects[1].x - destX) * (mObjects[1].x - destX)) + ((mObjects[1].y - destY) * (mObjects[1].y - destY));
            if (l < 1500 && l>800)
                break;
        }
        mObjects[2].x = destX;
        mObjects[2].y = destY;

        for (int x = 3; x < OBJECT_NUM; x++) {
            boolean result = false;
            while (true) {
                destX = (int) (Math.random() * 80) + 10;
                destY = (int) (Math.random() * 80) + 10;
                int l = ((mObjects[x - 1].x - destX) * (mObjects[x - 1].x - destX)) + ((mObjects[x - 1].y - destY) * (mObjects[x - 1].y - destY));
                if (l < 1500 && l>800) {
                    break;
                }
            }
            mObjects[x].x = destX;
            mObjects[x].y = destY;
            for (int y = 0; y < x-2; y++) {
                if (isIntersect(mObjects[y], mObjects[y+1], mObjects[x-1], mObjects[x])) {
                    result = true;

                }
            }
            if(result) {
                x--;
                continue;
            }

            Log.d("xxxxxx","Count = "+x);
        }
        for (int j = 0; j < OBJECT_NUM; j++) {
            mObjects[j].x = DeviceUtil.getInstance().getDisplayWidth() / 100 * mObjects[j].x;
            mObjects[j].y = DeviceUtil.getInstance().getDisplayHeight() / 100 * mObjects[j].y;
        }
    }

    int ccw(Point a, Point b, Point c) {
        int ans = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (ans < 0) return 1;
        else if (ans > 0) return -1;
        else return 0;
    }

    boolean isIntersect(Point line1S, Point line1E, Point line2S, Point line2E) {
        int ab = ccw(line1S, line1E, line2S) * ccw(line1S, line1E, line2E);
        int cd = ccw(line2S, line2E, line1S) * ccw(line2S, line2E, line1E);
        return ab <= 0 && cd <= 0;
    }

    protected void readyStartDrawImpl(Canvas canvas) {
        int x = DeviceUtil.getInstance().getDisplayWidth() / 2;
        int y = DeviceUtil.getInstance().getDisplayHeight() / 2;

        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y - 370, 80, mPaint);

        mPaint.setTextSize(80);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.parseColor("#000000"));
        canvas.drawText("Follow the blue dot as it", x, y - 600, mPaint);
        canvas.drawText("follows the path", x, y - 470, mPaint);
    }

    @Override
    protected void drawImpl(Canvas canvas) {
        mFrameCount++;
        if (mFrameCount >= SPEED * OBJECT_NUM) {
            finish();
            goHome();
            return;
        }
        onDrawGuideLine(canvas);


        for (int i = 0; i < OBJECT_NUM; i++) {
            mPaint.setColor(Color.YELLOW);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mObjects[i].x, mObjects[i].y, CIRCLE_SIZE, mPaint);

            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(10);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(mObjects[i].x, mObjects[i].y, CIRCLE_SIZE + 3, mPaint);
        }

        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mObjects[mFrameCount / SPEED].x, mObjects[mFrameCount / SPEED].y, CIRCLE_SIZE, mPaint);

        mViewCallback.onRecodeTargetPosition(mTrackerX, mTrackerY, mObjects[mFrameCount / SPEED].x, mObjects[mFrameCount / SPEED].y);
    }

    private void onDrawGuideLine(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#000000"));
        mPaint.setStrokeWidth(LINE_SIZE);
        for (int i = 0; i < OBJECT_NUM-1; i++) {
            canvas.drawLine(mObjects[i].x, mObjects[i].y, mObjects[i + 1].x, mObjects[i + 1].y, mPaint);
        }
    }
}
