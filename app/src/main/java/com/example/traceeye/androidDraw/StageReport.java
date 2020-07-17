package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

import com.example.traceeye.DeviceUtil;
import com.example.traceeye.data.DataManager;
import com.example.traceeye.data.DataObject;

import java.util.ArrayList;

public class StageReport extends AbstractRenderView {
    private static final String TAG = "StageReport";
    private Point mPoint;
    Path mPath;
    private final Point CENTER_POINT = new Point(DeviceUtil.getInstance().getDisplayWidth() / 2, DeviceUtil.getInstance().getDisplayHeight() / 2);
    private final int WIDTH = DeviceUtil.getInstance().getDisplayWidth();
    private final int HEIGHT = DeviceUtil.getInstance().getDisplayHeight();
    private final int FIRST_SECTION = DeviceUtil.getInstance().getDisplayHeight()/11;
    private final int SECOND_SECTION = FIRST_SECTION*4;
    private final int THIRD_SECTION = FIRST_SECTION*7;
    private final int LAST_SECTION = FIRST_SECTION*10;
    private final int PADDING = DeviceUtil.getInstance().getDisplayWidth()/5;
    private final int ZOOM = 5;
    private final int PADDING2 = PADDING+PADDING;

    private ArrayList<DataObject> mDataList = null;
    public StageReport(Context context, ViewCallback callback) {
        super(context, callback);
        if(DataManager.getInstance().isData())
            mDataList = DataManager.getInstance().getDataList();
        mPath = new Path();
        mPoint = new Point(CENTER_POINT.x, CENTER_POINT.y);
        mReadyStart = true;
    }

    private void calEyeX() {
        float onePoint = (float)(WIDTH-PADDING2) / (float)mDataList.size();
        mPath.reset();
        mPath.moveTo((float) (PADDING), (float) (mDataList.get(0).eyeX/ZOOM+FIRST_SECTION));
        for(int i=1;i<mDataList.size();i++) {
            mPath.lineTo((float)(i*onePoint)+PADDING, (float) (mDataList.get(i).eyeX/ZOOM+ FIRST_SECTION));
            Log.d("ABC", " "+mDataList.get(i).eyeX);
        }
    }
    private void calMoveX() {
        float onePoint = (float)(WIDTH-PADDING2) / (float)mDataList.size();
        Log.d(TAG, "onePoint is"+FIRST_SECTION+"   "+SECOND_SECTION);
        mPath.reset();
        mPath.moveTo((float) (PADDING), (float) (mDataList.get(0).targetX/ZOOM+FIRST_SECTION));
        for(int i=1;i<mDataList.size();i++) {
            mPath.lineTo((float)(i*onePoint)+PADDING, (float) (mDataList.get(i).targetX/ZOOM+ FIRST_SECTION));
        }
    }
    private void calEyeY() {
        float onePoint = (float)(WIDTH-PADDING2) / (float)mDataList.size();
        mPath.reset();
        mPath.moveTo((float) (PADDING), (float) ((mDataList.get(0).eyeY/ZOOM)+SECOND_SECTION));
        for(int i=1;i<mDataList.size();i++) {
            mPath.lineTo((float)(i*onePoint)+PADDING, (float) ((mDataList.get(i).eyeY/ZOOM)+SECOND_SECTION));
        }
    }
    private void calMoveY() {
        float onePoint = (float)(WIDTH-PADDING2) / (float)mDataList.size();
        mPath.reset();
        mPath.moveTo((float) (PADDING), (float) ((mDataList.get(0).targetY/ZOOM)+SECOND_SECTION));
        for(int i=1;i<mDataList.size();i++) {
            mPath.lineTo((float)(i*onePoint)+PADDING, (float) ((mDataList.get(i).targetY/ZOOM)+SECOND_SECTION));
        }
    }
    private void background() {
        mPath.reset();
        mPath.moveTo((float) (PADDING), FIRST_SECTION);
        mPath.lineTo((float) (PADDING), SECOND_SECTION-FIRST_SECTION);
        mPath.lineTo((float) (WIDTH-PADDING), SECOND_SECTION-FIRST_SECTION);

        mPath.moveTo((float) (PADDING), SECOND_SECTION);
        mPath.lineTo((float) (PADDING), THIRD_SECTION-FIRST_SECTION);
        mPath.lineTo((float) (WIDTH-PADDING), THIRD_SECTION-FIRST_SECTION);

        mPath.moveTo((float) (PADDING), THIRD_SECTION);
        mPath.lineTo((float) (PADDING), LAST_SECTION-FIRST_SECTION);
        mPath.lineTo((float) (WIDTH-PADDING), LAST_SECTION-FIRST_SECTION);
    }
    protected void drawImpl(Canvas canvas) {
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        //----------------------
        mPaint.setColor(Color.BLACK);
        background();
        canvas.drawPath(mPath, mPaint);

        if(mDataList == null)
            return;

        //----------------------
        mPaint.setColor(Color.RED);
        calEyeX();
        canvas.drawPath(mPath, mPaint);
        //----------------------
        mPaint.setColor(Color.BLUE);
        calMoveX();
        canvas.drawPath(mPath, mPaint);
        //----------------------
        mPaint.setColor(Color.RED);
        calEyeY();
        canvas.drawPath(mPath, mPaint);
        //----------------------
        mPaint.setColor(Color.BLUE);
        calMoveY();
        canvas.drawPath(mPath, mPaint);
    }
}
