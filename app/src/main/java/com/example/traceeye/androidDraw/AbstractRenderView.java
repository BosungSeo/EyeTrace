package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;


public class AbstractRenderView extends DrawView {
    protected int mTrackerX;
    protected int mTrackerY;

    Animator mAnimator;
    protected ViewCallback mViewCallback;

    public AbstractRenderView(Context context, ViewCallback callback) {
        super(context);
        mViewCallback = callback;
        mTrackerX = 0;
        mTrackerY = 0;
        mAnimator = new Animator(this);
    }

    public void draw(int x, int y) {
        mAnimator.draw(x,y);
    }

    public void setTrackerPoint(int x, int y) {
        this.mTrackerX = x;
        this.mTrackerY = y;
    }

    public interface ViewCallback {
        void onNext();

        void onStartTrackerData();

        void onStopTrackerData();
    }
}
