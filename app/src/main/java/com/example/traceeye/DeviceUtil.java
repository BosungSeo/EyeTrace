package com.example.traceeye;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;

public class DeviceUtil {
    private final String TAG = DeviceUtil.class.getName();
    private static DeviceUtil INSTANCE = null;
    private Point mPoint;
    private Point mAdjustPoint;
    private Context mContext;

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
        SharedPreferences prefs = mContext.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        mAdjustPoint.x = prefs.getInt("x",0);
        mAdjustPoint.y = prefs.getInt("y",0);
        Log.d(TAG, "init - Cal X : "+mAdjustPoint.x+ "     Y : "+mAdjustPoint.y);
    }
    public void setDisplay(Display d) {
        mPoint = new Point();
        d.getRealSize(mPoint);
    }

    public void setAdjustPoint(Point adjust) {
        SharedPreferences prefs = mContext.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("x", adjust.x);
        editor.putInt("y", adjust.y);
        editor.commit();
        mAdjustPoint.x = adjust.x;
        mAdjustPoint.y = adjust.y;
        Log.d(TAG, "setAdjustPoint - Cal X : "+mAdjustPoint.x+ "     Y : "+mAdjustPoint.y);
    }

    public int getAdjustX() {
        return mAdjustPoint.x;
    }

    public int getAdjustY() {
        return mAdjustPoint.y;
    }

    public int getDisplayWidth() {
        return mPoint.x;
    }

    public int getDisplayHeight() {
        return mPoint.y;
    }
}
