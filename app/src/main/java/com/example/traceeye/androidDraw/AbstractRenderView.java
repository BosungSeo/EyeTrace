package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.example.traceeye.DeviceUtil;


abstract public class AbstractRenderView extends DrawView {
    private final String TAG = AbstractRenderView.class.getSimpleName();
    protected int mTrackerX;
    protected int mTrackerY;
    protected int mXGuidLineLength = DeviceUtil.getInstance().getDisplayWidth() / 100;
    protected int mYGuidLineLength = DeviceUtil.getInstance().getDisplayHeight() / 100;
    protected int[] mXGuideLine = new int[mXGuidLineLength];
    protected int[] mYGuideLine = new int[mYGuidLineLength];
    Animator mAnimator;
    protected ViewCallback mViewCallback;

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
        DeviceUtil.getInstance().setShowPoint(true);
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
        drawImpl(canvas);
        if (DeviceUtil.getInstance().getShowPoint())
            drawPoint(canvas);
    }

    protected abstract void drawImpl(Canvas canvas);

    private void drawPoint(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#2F9D27"));
        canvas.drawCircle(mTrackerX, mTrackerY, 10, mPaint);
    }

    private void readyStartDraw(Canvas canvas) {

    }

    public interface ViewCallback {
        void onNext(int stage);

        void onHome();

        void onStartTrackerData();

        void onStopTrackerData();
    }
}
