package com.example.traceeye.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ListData implements Comparable {
    private String mDate;
    private long mLongDate;
    private String mTestName;
    private DataObject mObject;

    public ListData(DataObject object) {
        this.mLongDate = object.mTime;
        this.mTestName = object.mTestName;
        this.mObject = object;
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        this.mDate = simpleDate.format(new Date(mLongDate));
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

    @Override
    public int compareTo(Object o) {
        long i = ((ListData)o).mLongDate;
        if (mLongDate == i)
            return 0;
        return mLongDate < i ? 1 : -1;
    }
}
