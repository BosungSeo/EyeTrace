package com.example.traceeye.androidDraw;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;

import com.example.traceeye.DeviceUtil;
import com.example.traceeye.R;

public class StageView3 extends AbstractRenderView {
    private Point mPoint;
    private int mRight;
    private final int SPEED = 10;
    private final int MARGIN = 30 + SPEED;
    private boolean direction = false;
    Bitmap mBitmap;

    public StageView3(Context context, ViewCallback callback) {
        super(context, callback);
        BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.ho);
        mBitmap = bd.getBitmap();
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

        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }
}
