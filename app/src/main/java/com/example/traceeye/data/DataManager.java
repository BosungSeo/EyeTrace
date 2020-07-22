package com.example.traceeye.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.traceeye.DeviceUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// firebase
// id : eyetracker001@gmail.com
// pass : eye1234!@#$
public class DataManager {
    private static final String TAG = DataManager.class.getSimpleName();
    private static DataManager INSTANCE = null;
    DataObject mRecordDataObject = new DataObject();
    DataObject mCurrentDataObject;
    ArrayList<ListData> mOutputList;
    private String mUUID;
    private long[] mItemTimeTable;
    // private FirebaseStorage mDatabase;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private DataManager() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        mUUID = DeviceUtil.getInstance().getUUID();
    }

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public void recordTracker(int eyeX, int eyeY, int targetX, int targetY) {
        mRecordDataObject.setData(eyeX, eyeY, targetX, targetY);
    }

    public void saveData(String testName) {
        Log.d(TAG, "saveData : " + testName);
        mRecordDataObject.setTestName(testName);
        long now = System.currentTimeMillis();
        Map<String, Object> childUpdates = new HashMap<>();
        // out.put(data.getJsonObject());
        String key = databaseReference.child("posts").push().getKey();
        // childUpdates.put("/posts/" + key, data.toMap());


        childUpdates.put("/user-posts/" + mUUID + "/" + now, mRecordDataObject.toMap());
        childUpdates.put("/user-times/" + mUUID + "/" + now, now);
        databaseReference.updateChildren(childUpdates);
    }

    public void setItemList(ArrayList<ListData> outputList) {
        mOutputList = outputList;
    }
    private Callback mCallback;
    public void setCallback(Callback c) {
        mCallback = c;
    }
    public void loadData() {
        for (int i = 0; i < mItemTimeTable.length; i++) {
            Query ref = firebaseDatabase.getReference("/user-posts/" + mUUID + "/" + mItemTimeTable[i]);
            ref.addListenerForSingleValueEvent(new CustomValueEventListener(mItemTimeTable[i]));
        }
    }
    class CustomValueEventListener implements ValueEventListener {
        long mTime;
        CustomValueEventListener(long time) {
            mTime = time;
        }
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            DataObject a = new DataObject(mTime, (Map<String, Object>) snapshot.getValue());
            Log.d(TAG, a.toString());
            mCallback.databaseResult(a);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }
    public void loadDataTime() {
        Query ref = firebaseDatabase.getReference("/user-times/" + mUUID).limitToLast(3).orderByPriority();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // dataSnapshot.child()
                Map<String, Object> ooo = (Map<String, Object>) snapshot.getValue();
                mItemTimeTable = new long[ooo.size()];
                int i = 0;
                for (String key : ooo.keySet()) {
                    Log.d(TAG, key);
                    mItemTimeTable[i++] = Long.parseLong(key);
                }
                loadData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void resetRecordData() {
        // mDataList.clear();
    }

    public void logAllData() {
    }

    public void writeFile() {

    }

    public DataObject getCurrentDataList() {
        return mCurrentDataObject;
    }
    public void setCurrentDataList(DataObject setData) {
        mCurrentDataObject = setData;
    }
    public boolean isData() {
        return !mCurrentDataObject.targetX.isEmpty();
    }
    interface Callback {
        void databaseResult(DataObject r);
    }
}
