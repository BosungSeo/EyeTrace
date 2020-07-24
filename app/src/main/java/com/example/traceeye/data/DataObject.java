package com.example.traceeye.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataObject {
    public ArrayList<Long> eyeX;
    public ArrayList<Long> eyeY;
    public ArrayList<Long> targetX;
    public ArrayList<Long> targetY;
    public String mTestName;
    public int mFrameCount;
    public long mTime;
    private int mDataSize = 0;

    public DataObject() {
        mTestName = "unKnown";
        eyeX = new ArrayList<>();
        eyeY = new ArrayList<>();
        targetX = new ArrayList<>();
        targetY = new ArrayList<>();
    }

    public DataObject(long time, Map<String, Object> data) {
        mTime = time;
        mTestName = (String) data.get("testName");

        eyeX = (ArrayList<Long>) data.get("eyeX");
        eyeY = (ArrayList<Long>) data.get("eyeY");
        targetX = (ArrayList<Long>) data.get("targetX");
        targetY = (ArrayList<Long>) data.get("targetY");
        mDataSize = ((ArrayList<Long>) data.get("eyeX")).size();
    }

    public void setData(int eX, int eY, int tX, int tY) {
        eyeX.add((long)eX);
        eyeY.add((long)eY);
        targetX.add((long)tX);
        targetY.add((long)tY);
        mDataSize++;
    }

    public int getSize() {
        return mDataSize;
    }

    public void setTestName(String name) {
        mTestName = name;
    }
    public void setFrameCount(int c) { mFrameCount = c;}
    @Override
    public String toString() {
        return "DataObject{" +
                "testName="+mTestName+
                "eyeX=" + eyeX.toString() +
                ", eyeY=" + eyeY.toString() +
                ", targetX=" + targetX.toString() +
                ", targetY=" + targetY.toString() +
                '}';
    }

    public JSONObject getJsonObject() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("type", mTestName);
        obj.put("frame",mFrameCount);
        obj.put("fps",30);
        obj.put("eyeX", eyeX);
        obj.put("eyeY", eyeY);
        obj.put("targetX", targetX);
        obj.put("targetY", targetY);
        return obj;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("testName", mTestName);
        result.put("frame",mFrameCount);
        result.put("fps",30);
        result.put("eyeX", eyeX);
        result.put("eyeY", eyeY);
        result.put("targetX", targetX);
        result.put("targetY", targetY);
        return result;
    }
}

