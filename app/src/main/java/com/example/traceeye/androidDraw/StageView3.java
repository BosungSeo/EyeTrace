package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;

import com.example.traceeye.DeviceUtil;
import com.example.traceeye.R;

public class StageView3 extends AbstractRenderView {
    private final int MARGIN = 100;
    private final int CHANGE_FRAME = 15 * DeviceUtil.getInstance().getStageValue(2);
    Bitmap mBitmap;
    private Point mPoint;
    private Point mRandomRange;

    public StageView3(Context context, ViewCallback callback) {
        super(context, callback);
        BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.ho);
        mBitmap = bd.getBitmap();
        mPoint = new Point();
        mRandomRange = new Point(DeviceUtil.getInstance().getDisplayWidth() - MARGIN,
                DeviceUtil.getInstance().getDisplayHeight() - MARGIN);
        randomGenerator();
    }

    private void randomGenerator() {
        mPoint.x = (int) (Math.random() * mRandomRange.x) + (MARGIN / 2);
        mPoint.y = (int) (Math.random() * mRandomRange.y) + (MARGIN / 2);
    }
    protected void readyStartDrawImpl(Canvas canvas) {
        int x = DeviceUtil.getInstance().getDisplayWidth() / 2;
        int y = DeviceUtil.getInstance().getDisplayHeight() / 2;
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(80);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Follow the image as it blinks", x, y - 600, mPaint);
        canvas.drawText("around the screen", x, y - 470, mPaint);
    }
    protected void drawImpl(Canvas canvas) {
        mFrameCount++;
        if (mFrameCount % CHANGE_FRAME == 0) {
            randomGenerator();
        }

        canvas.drawBitmap(mBitmap, mPoint.x, mPoint.y, mPaint);
        mViewCallback.onRecodeTargetPosition(mTrackerX, mTrackerY, mPoint.x, mPoint.y);
        if (mFrameCount > CHANGE_FRAME * 10) {
            finish();
            goHome();
        }
    }
}
