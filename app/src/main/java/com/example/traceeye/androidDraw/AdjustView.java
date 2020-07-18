package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.example.traceeye.DeviceUtil;

import java.util.ArrayList;

public class AdjustView extends AbstractRenderView {
    private final String TAG = AdjustView.class.getSimpleName();
    private Point mCP;
    private Point mCalibrationPoint;
    private final int ADJUST_RANGE = 100;
    private int mCurrentFrame;
    private int mCircleCount;
    private boolean mFinish = false;
    private final int RED_CIRCLE_SIZE = 100;
    private final int BLUE_CIRCLE_SIZE = 30;
    private int mCalProgress = 0;
    private ArrayList<Point> mAdjustDataList = new ArrayList<>();

    public AdjustView(Context context, ViewCallback callback) {
        super(context, callback);
        mCP = new Point(DeviceUtil.getInstance().getDisplayWidth() / 2, DeviceUtil.getInstance().getDisplayHeight() / 2);
        mCalibrationPoint = new Point(0, 0);
        mCurrentFrame = 0;
        // init device adjust.
        DeviceUtil.getInstance().setAdjustPoint(new Point(0, 0));
        mCircleCount = mReadCount;
    }

    @Override
    protected void drawImpl(Canvas canvas) {
        if (mFinish == false) {
            drawFirstStep(canvas);
        } else {
            drawSecondStep(canvas);
        }
    }

    private void drawFirstStep(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(50);
        mPaint.setColor(Color.parseColor("#000000"));


        mPaint.setColor(Color.parseColor("#FF0000"));
        canvas.drawCircle(mCalibrationPoint.x, mCalibrationPoint.y, 30, mPaint);

        mPaint.setStrokeWidth(6f);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rect = new RectF();
        mPaint.setColor(Color.parseColor("#0000FF"));
        rect.set(mCalibrationPoint.x - BLUE_CIRCLE_SIZE, mCalibrationPoint.y - BLUE_CIRCLE_SIZE
                , mCalibrationPoint.x + BLUE_CIRCLE_SIZE, mCalibrationPoint.y + BLUE_CIRCLE_SIZE);
        canvas.drawArc(rect, 0, mCalProgress, false, mPaint);
    }

    protected void readyStartDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(150);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.parseColor("#000000"));
        int x = DeviceUtil.getInstance().getDisplayWidth() / 2;
        int y = DeviceUtil.getInstance().getDisplayHeight() / 2;
        canvas.drawText("Ready?", x, y - 150, mPaint);
        canvas.drawText(Integer.toString(mReadCount / FRAME + 1), x, y + 50, mPaint);

        mPaint.setStrokeWidth(6f);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rect = new RectF();
        mPaint.setColor(Color.parseColor("#FF0000"));
        rect.set(mCP.x - RED_CIRCLE_SIZE, mCP.y - RED_CIRCLE_SIZE
                , mCP.x + RED_CIRCLE_SIZE, mCP.y + RED_CIRCLE_SIZE);
        canvas.drawArc(rect, 0, (360 * (((mCircleCount - mReadCount) * 100) / mCircleCount)) / 100, false, mPaint);

    }

    private void drawSecondStep(Canvas canvas) {
        mCurrentFrame++;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#000000"));
        canvas.drawText("조절 완료.", 100, 300, mPaint);
        canvas.drawText("잠시 후 처음 화면으로 넘어갑니다.", 100, 400, mPaint);
        mPaint.setColor(Color.parseColor("#FF0000"));
        canvas.drawCircle(mCP.x, mCP.y, 30, mPaint);

        mPaint.setColor(Color.parseColor("#0000FF"));
        canvas.drawCircle(mTrackerX, mTrackerY, 10, mPaint);
        if (mCurrentFrame > 30)
            mViewCallback.onHome();
    }

    public void onCalibrationProgress(float progress) {
        mCalProgress = Math.round(progress * 360);
    }

    public void onCalibrationNextPoint(float x, float y) {
        mCalibrationPoint.x = Math.round(x);
        mCalibrationPoint.y = Math.round(y);
    }

    public void onCalibrationFinished() {
        mFinish = true;
    }
}
