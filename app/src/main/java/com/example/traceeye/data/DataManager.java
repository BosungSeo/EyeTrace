package com.example.traceeye.data;

import android.content.Context;
import android.graphics.Point;

import com.example.traceeye.DeviceUtil;
import com.example.traceeye.LogUtil;

import java.util.ArrayList;

public class DataManager {
    ArrayList<Point> mDataList = new ArrayList<>();
    public DataManager(Context c) {
    }
    public void recordTracker(int x, int y) {
        LogUtil.d("X:$(x) Y:$(y)");
        mDataList.add(new Point(x+ DeviceUtil.getInstance().getAdjustX(),
                y+DeviceUtil.getInstance().getAdjustY()));
    }
    public void resetRecordData() {
        mDataList.clear();
    }
    public void logAllData() {
        for(Point p : mDataList) {
            LogUtil.d("X:$(p.x) Y:$(p.y)");
        }
    }
    public void writeFile() {

    }
}
