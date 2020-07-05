package com.example.traceeye;

import android.content.Context;

import androidx.recyclerview.widget.AsyncListUtil;

import com.example.traceeye.androidDraw.AbstractRenderView;
import com.example.traceeye.androidDraw.AbstractRenderView.ViewCallback;
import com.example.traceeye.androidDraw.AdjustView;
import com.example.traceeye.androidDraw.StageReport;
import com.example.traceeye.androidDraw.StageView1;
import com.example.traceeye.androidDraw.StageView2;
import com.example.traceeye.androidDraw.StageView3;
import com.example.traceeye.androidDraw.StageView4;

public class StageManager {
    public static final int STAGE1 = 0;
    public static final int STAGE2 = 1;
    public static final int STAGE3 = 2;
    public static final int STAGE4 = 3;
    public static final int ADJUST = 4;
    public static final int STAGE_START_COUNT = 5;
    public static final int STAGE_REPORT = 6;
    private Context mContext;
    private ViewCallback mCallback;
    private AbstractRenderView mCurrentView = null;

    public StageManager(Context context, ViewCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    public AbstractRenderView getStage(int index) {
        mCurrentView = getStageImpl(index);
        return mCurrentView;
    }

    public void draw(int x, int y) {
        if (mCurrentView != null) {
            mCurrentView.draw(x, y);
        }
    }

    public void onOrientationChange() {
        mCurrentView.orientChange();
    }

    public AbstractRenderView getStageImpl(int index) {
        switch (index) {
            case STAGE1:
                return new StageView1(mContext, mCallback);
            case STAGE2:
                return new StageView2(mContext, mCallback);
            case STAGE3:
                return new StageView3(mContext, mCallback);
            case STAGE4:
                return new StageView4(mContext, mCallback);
            case STAGE_REPORT:
                return new StageReport(mContext, mCallback);
            case ADJUST:
                return new AdjustView(mContext, mCallback);
        }
        return null;
    }

}
