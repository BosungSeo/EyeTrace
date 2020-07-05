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
    private int mCount = 0;
    private final int MARGIN = 100;
    private final int CHANGE_FRAME = 60;
    private Point mRandomRange;

    Bitmap mBitmap;

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

    protected void drawImpl(Canvas canvas) {
        mCount++;
        if (mCount % CHANGE_FRAME == 0) {
            randomGenerator();
        }

        canvas.drawBitmap(mBitmap, mPoint.x, mPoint.y, mPaint);
        if (mCount > CHANGE_FRAME * 10)
            goHome();
    }
}
