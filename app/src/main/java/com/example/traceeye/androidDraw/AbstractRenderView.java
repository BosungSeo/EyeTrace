package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.traceeye.DeviceUtil;


abstract public class AbstractRenderView extends DrawView {
    private final String TAG = AbstractRenderView.class.getSimpleName();
    protected int mTrackerX;
    protected int mTrackerY;
    protected int mXGuidLineLength = DeviceUtil.getInstance().getDisplayWidth() / 100;
    protected int mYGuidLineLength = DeviceUtil.getInstance().getDisplayHeight() / 100;
    protected int[] mXGuideLine = new int[mXGuidLineLength];
    protected int[] mYGuideLine = new int[mYGuidLineLength];
    private boolean mReadyStart = false;
    Animator mAnimator;
    protected ViewCallback mViewCallback;
    protected final int FRAME = 30;
    protected int mReadCount = FRAME * 4;

    public AbstractRenderView(Context context, ViewCallback callback) {
        super(context);
        mViewCallback = callback;
        mTrackerX = 0;
        mTrackerY = 0;
        //Log.d(TAG, "GuidLine X");
        for (int x = 0; x < mXGuidLineLength; x++) {
            mXGuideLine[x] = x * 100;
            //Log.d(TAG, " " + mXGuideLine[x]);
        }
        //Log.d(TAG, "GuidLine Y");
        for (int y = 0; y < mYGuidLineLength; y++) {
            mYGuideLine[y] = y * 100;
            //Log.d(TAG, " " + mXGuideLine[y]);
        }

        mAnimator = new Animator(this);
    }

    protected int getTransX(int inputX) {
        return DeviceUtil.getInstance().getDisplayWidth() / 100 * inputX;
    }

    protected int getTransY(int inputY) {
        return DeviceUtil.getInstance().getDisplayHeight() / 100 * inputY;
    }

    public void orientChange() {

    }

    public void draw(int x, int y) {
        mAnimator.draw(x, y);
    }

    public void setTrackerPoint(int x, int y) {
        this.mTrackerX = x;
        this.mTrackerY = y;
    }

    public void goHome() {
        mViewCallback.onHome();
    }

    public void goNext(int stage) {
        mViewCallback.onNext(stage);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mReadyStart) {
            readyStartDraw(canvas);
            mReadCount--;
            if (mReadCount < 0) {
                mReadyStart = true;
                mViewCallback.onStartTrackerData();
            }
        } else {
            drawImpl(canvas);
            if (DeviceUtil.getInstance().getShowPoint())
                drawPoint(canvas);
        }
    }

    protected abstract void drawImpl(Canvas canvas);

    private void drawPoint(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#2F9D27"));
        canvas.drawCircle(mTrackerX, mTrackerY, 10, mPaint);
    }

    protected void readyStartDraw(Canvas canvas) {
        mPaint.setTextSize(150);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.parseColor("#000000"));
        int x = DeviceUtil.getInstance().getDisplayWidth() / 2;
        int y = DeviceUtil.getInstance().getDisplayHeight() / 2;
        canvas.drawText("Ready?", x, y - 150, mPaint);
        canvas.drawText(Integer.toString(mReadCount / FRAME + 1), x, y + 50, mPaint);
    }

    public interface ViewCallback {
        void onNext(int stage);

        void onHome();

        void onStartTrackerData();

        void onStopTrackerData();

        void onRecodeTargetPosition(int eyeX, int eyeY, int targetX, int targetY);
    }
}
