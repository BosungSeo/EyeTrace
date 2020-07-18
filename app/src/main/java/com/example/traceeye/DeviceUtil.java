package com.example.traceeye;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;

public class DeviceUtil {
    private static DeviceUtil INSTANCE = null;
    private final String TAG = DeviceUtil.class.getName();
    private Point mPoint;
    private Point mAdjustPoint;
    private Context mContext;
    private Display mDisplay;
    private boolean mShowPoint = true;
    private boolean mFivePoint = false;
    private int[] mStageValue = new int[4];

    private DeviceUtil() {
    }

    public static DeviceUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DeviceUtil();
        }
        return INSTANCE;
    }

    public void init(Context c) {
        mContext = c;
        mAdjustPoint = new Point();
        SharedPreferences prefs = mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        mAdjustPoint.x = prefs.getInt("x", 0);
        mAdjustPoint.y = prefs.getInt("y", 0);
        mShowPoint = prefs.getBoolean("point", false);
        mFivePoint = prefs.getBoolean("cal_point", false);
        mStageValue[0] = prefs.getInt("stage1", 1);
        mStageValue[1] = prefs.getInt("stage2", 1);
        mStageValue[2] = prefs.getInt("stage3", 1);
        mStageValue[3] = prefs.getInt("stage4", 1);
        Log.d(TAG, "init - Cal X : " + mAdjustPoint.x + "     Y : " + mAdjustPoint.y);
    }

    public void resetAdjustPoint() {
        mAdjustPoint.x = 0;
        mAdjustPoint.y = 0;
    }

    public boolean getShowPoint() {
        return mShowPoint;
    }

    public void setShowPoint(boolean b) {
        SharedPreferences prefs = mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("point", b);
        editor.commit();
        mShowPoint = b;
    }
    public boolean getFiveCalPoint() {
        return mFivePoint;
    }

    public void setFiveCalPoint(boolean b) {
        SharedPreferences prefs = mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("cal_point", b);
        editor.commit();
        mFivePoint = b;
    }
    public int getStageValue(int index) {
        return mStageValue[index];
    }
    public void setStageValue(int index, int value) {
        String key = "stage" + (index+1);
        SharedPreferences prefs = mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
        mStageValue[index] = value;
    }


    public void setDisplay(Display d) {
        mDisplay = d;
        mPoint = new Point();
        mDisplay.getRealSize(mPoint);
    }

    public void changeOrient() {
        mDisplay.getRealSize(mPoint);
        Log.d(TAG, "Width=" + mPoint.x + " Height=" + mPoint.y);
    }

    public void setAdjustPoint(Point adjust) {
        /*SharedPreferences prefs = mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("x", adjust.x);
        editor.putInt("y", adjust.y);
        editor.commit();
        mAdjustPoint.x = adjust.x;
        mAdjustPoint.y = adjust.y;
        Log.d(TAG, "setAdjustPoint - Cal X : " + mAdjustPoint.x + "     Y : " + mAdjustPoint.y);*/
    }

    public int getAdjustX() {
        return 0;//mAdjustPoint.x;
    }

    public int getAdjustY() {
        return 0;//mAdjustPoint.y;
    }

    public int getDisplayWidth() {
        return mPoint.x;
    }

    public int getDisplayHeight() {
        return mPoint.y;
    }
}
