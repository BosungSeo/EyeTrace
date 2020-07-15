package com.example.traceeye.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DataObject {
    public int eyeX;
    public int eyeY;
    public int targetX;
    public int targetY;

    public DataObject(int eyeX, int eyeY, int targetX, int targetY) {
        this.eyeX = eyeX;
        this.eyeY = eyeY;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "eyeX=" + eyeX +
                ", eyeY=" + eyeY +
                ", targetX=" + targetX +
                ", targetY=" + targetY +
                '}';
    }

    public JSONObject getJsonObject() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("eyeX", eyeX);
        obj.put("eyeY", eyeY);
        obj.put("targetX", targetX);
        obj.put("targetY", targetY);
        return obj;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("eyeX", eyeX);
        result.put("eyeY", eyeY);
        result.put("targetX", targetX);
        result.put("targetY", targetY);
        return result;
    }
}
