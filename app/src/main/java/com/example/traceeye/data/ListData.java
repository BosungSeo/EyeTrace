package com.example.traceeye.data;

public class ListData {
    private String mDate;
    private String mTestName;
    private DataObject mObject;

    public ListData(String mDate, String mTestName, DataObject object) {
        this.mDate = mDate;
        this.mTestName = mTestName;
        this.mObject = object;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmTestName() {
        return mTestName;
    }
    public DataObject getData() {
        return mObject;
    }
    public void setmTestName(String mTestName) {
        this.mTestName = mTestName;
    }
}
