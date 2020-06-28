package com.example.traceeye.androidDraw;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.example.traceeye.DeviceUtil;

public class StartView extends AbstractRenderView {
    private float i;
    private Point mCP;
    private final int RED_CIRCLE_SIZE = 200;

    public StartView(Context context, ViewCallback callback) {
        super(context, callback);
        mCP = new Point(DeviceUtil.getInstance().getDisplayWidth() / 2, DeviceUtil.getInstance().getDisplayHeight() / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTrackerX > mCP.x - RED_CIRCLE_SIZE && mTrackerX < mCP.x + RED_CIRCLE_SIZE
                && mTrackerY > mCP.y - RED_CIRCLE_SIZE && mTrackerY < mCP.y + RED_CIRCLE_SIZE) {
            i += 3.6f;
        }
        else i = 0.0f;

        mPaint.setStrokeWidth(6f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#2F9D27"));
        canvas.drawCircle(mCP.x,
                mCP.y, RED_CIRCLE_SIZE, mPaint);
        RectF rect = new RectF();
        mPaint.setColor(Color.parseColor("#FF0000"));
        rect.set(mCP.x - RED_CIRCLE_SIZE, mCP.y - RED_CIRCLE_SIZE
                , mCP.x + RED_CIRCLE_SIZE, mCP.y + RED_CIRCLE_SIZE);
        canvas.drawArc(rect, 270, i, false, mPaint);

        mPaint.setColor(Color.parseColor("#0000FF"));
        canvas.drawCircle(mTrackerX, mTrackerY, 10, mPaint);
        mPaint.setTextSize(50);
        mPaint.setColor(Color.parseColor("#000000"));
        canvas.drawText("중앙에 원을 3초간 응시하시면 테스트 페이지로 넘어갑니다.", 100, 300, mPaint);
        if (i > 360f) {
            mViewCallback.onNext();
        }
    }
}
