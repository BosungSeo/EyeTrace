package com.example.traceeye.data;

import java.util.ArrayList;

public class DataObjectList {
    ArrayList<DataObject> mDataList;
    private String mDate;
    private String mTestName;

    public DataObjectList(ArrayList<DataObject> mDataList, String mDate, String mTestName) {
        this.mDataList = mDataList;
        this.mDate = mDate;
        this.mTestName = mTestName;
    }
}
