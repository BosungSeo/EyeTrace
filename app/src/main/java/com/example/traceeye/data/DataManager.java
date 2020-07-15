package com.example.traceeye.data;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import com.example.traceeye.DeviceUtil;
import com.example.traceeye.LogUtil;
import com.example.traceeye.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// firebase
// id : eyetracker001@gmail.com
// pass : eye1234!@#$
public class DataManager {
    private static final String TAG = MainActivity.class.getName();

    ArrayList<DataObject> mDataList = new ArrayList<DataObject>();
    // private FirebaseStorage mDatabase;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public DataManager(Context c) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // String value = dataSnapshot.getValue(String.class);
                // Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void recordTracker(int eyeX, int eyeY, int targetX, int targetY) {
        mDataList.add(new DataObject(eyeX, eyeY, targetX, targetY));
    }

    public void saveData() {

        Map<String, Object> childUpdates = new HashMap<>();
        for (DataObject data : mDataList) {
            // out.put(data.getJsonObject());
            String key = databaseReference.child("posts").push().getKey();
            childUpdates.put("/posts/" + key, data.toMap());
            childUpdates.put("/user-posts/" + "Abc" + "/" + key, data.toMap());
        }
        //databaseReference.setValue(out.toString());
        // LogUtil.d(childUpdates.toString());
        databaseReference.updateChildren(childUpdates);
    }

    public void resetRecordData() {
        mDataList.clear();
    }

    public void logAllData() {
        for (DataObject data : mDataList) {
            LogUtil.d("X:$(p.x) Y:$(p.y)");
        }
    }

    public void writeFile() {

    }
}
