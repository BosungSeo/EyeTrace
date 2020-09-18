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

import static android.graphics.Paint.Align.CENTER;

public class StageReport extends AbstractRenderView {
    private static final String TAG = "StageReport";
    private Point mPoint;
    Path mPath;
    private final Point CENTER_POINT = new Point(DeviceUtil.getInstance().getDisplayWidth() / 2, DeviceUtil.getInstance().getDisplayHeight() / 2);
    private final int WIDTH = DeviceUtil.getInstance().getDisplayWidth();
    private final int HEIGHT = DeviceUtil.getInstance().getDisplayHeight();
    private final int FIRST_SECTION = DeviceUtil.getInstance().getDisplayHeight() / 11;
    private final int SECOND_SECTION = FIRST_SECTION * 4;
    private final int THIRD_SECTION = FIRST_SECTION * 7;
    private final int LAST_SECTION = FIRST_SECTION * 10;
    private final int PADDING = DeviceUtil.getInstance().getDisplayWidth() / 5;
    private final int ZOOM = 17000/DeviceUtil.getInstance().getDisplayHeight();
    private final int SHOW_SEC = 90;
    private final int PADDING2 = PADDING + PADDING;
    private DataObject mDataList = null;

    public StageReport(Context context, ViewCallback callback) {
        super(context, callback);
        if (DataManager.getInstance().isData())
            mDataList = DataManager.getInstance().getCurrentDataList();
        Log.d("StageReport", " "+mDataList.getSize());
        mPath = new Path();
        mPoint = new Point(CENTER_POINT.x, CENTER_POINT.y);
        mReadyStart = true;
    }

    private void calEyeX() {
        float onePoint = (float) (WIDTH - PADDING2) / (float) mDataList.getSize();
        mPath.reset();
        mPath.moveTo((float) (PADDING), (float) (mDataList.eyeX.get(0).intValue() / ZOOM + FIRST_SECTION));
        for (int i = 1; i < mDataList.getSize(); i++) {
            mPath.lineTo((float) (i * onePoint) + PADDING, (float) (mDataList.eyeX.get(i).intValue() / ZOOM + FIRST_SECTION));
        }
    }

    private void calMoveX() {
        float onePoint = (float) (WIDTH - PADDING2) / (float) mDataList.getSize();
        mPath.reset();
        mPath.moveTo((float) (PADDING), (float) (mDataList.targetX.get(0).intValue() / ZOOM + FIRST_SECTION));
        for (int i = 1; i < mDataList.getSize(); i++) {
            mPath.lineTo((float) (i * onePoint) + PADDING, (float) (mDataList.targetX.get(i).intValue() / ZOOM + FIRST_SECTION));
        }
    }

    private void calEyeY() {
        float onePoint = (float) (WIDTH - PADDING2) / (float) mDataList.getSize();
        mPath.reset();
        mPath.moveTo((float) (PADDING), (float) (mDataList.eyeY.get(0).intValue() / ZOOM + SECOND_SECTION));
        for (int i = 1; i < mDataList.getSize(); i++) {
            mPath.lineTo((float) (i * onePoint) + PADDING, (float) (mDataList.eyeY.get(i).intValue() / ZOOM + SECOND_SECTION));
        }
    }

    private void calMoveY() {
        float onePoint = (float) (WIDTH - PADDING2) / (float) mDataList.getSize();
        mPath.reset();
        mPath.moveTo((float) (PADDING), (float) (mDataList.targetY.get(0).intValue() / ZOOM + SECOND_SECTION));
        for (int i = 1; i < mDataList.getSize(); i++) {
            mPath.lineTo((float) (i * onePoint) + PADDING, (float) (mDataList.targetY.get(i).intValue() / ZOOM + SECOND_SECTION));
        }
    }

    private void background(Canvas canvas) {
        int dataSize = 1;
        float sectionSec = 1;
        int smallSection = 0;
        mPaint.setColor(Color.BLACK);
        if (mDataList != null) {
            dataSize = mDataList.getSize();
            sectionSec = dataSize / 5;
        }
        smallSection = Math.round(((float) (WIDTH - PADDING2) / (float) dataSize) * sectionSec);
        mPath.reset();
        mPaint.setStrokeWidth(4);
        mPath.moveTo((float) (PADDING), FIRST_SECTION);
        mPath.lineTo((float) (PADDING), SECOND_SECTION - FIRST_SECTION);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPaint.setStrokeWidth(2);
        mPath.moveTo((float) (PADDING), SECOND_SECTION - FIRST_SECTION);
        mPath.lineTo((float) (WIDTH - PADDING), SECOND_SECTION - FIRST_SECTION);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPaint.setStrokeWidth(4);
        mPath.moveTo((float) (PADDING), SECOND_SECTION);
        mPath.lineTo((float) (PADDING), THIRD_SECTION - FIRST_SECTION);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPaint.setStrokeWidth(2);
        mPath.moveTo((float) (PADDING), THIRD_SECTION - FIRST_SECTION);
        mPath.lineTo((float) (WIDTH - PADDING), THIRD_SECTION - FIRST_SECTION);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPaint.setStrokeWidth(4);
        mPath.moveTo((float) (PADDING), THIRD_SECTION);
        mPath.lineTo((float) (PADDING), LAST_SECTION - FIRST_SECTION);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPaint.setStrokeWidth(2);
        mPath.moveTo((float) (PADDING), LAST_SECTION - FIRST_SECTION);
        mPath.lineTo((float) (WIDTH - PADDING), LAST_SECTION - FIRST_SECTION);
        canvas.drawPath(mPath, mPaint);

        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.GRAY);
        for (int i = 1; i <= (dataSize / sectionSec); i++) {
            String str = String.format("%.1fs", (i * sectionSec) / 30);
            mPath.reset();
            mPath.moveTo((float) (PADDING + (i * smallSection)), FIRST_SECTION);
            mPath.lineTo((float) (PADDING + (i * smallSection)), SECOND_SECTION - FIRST_SECTION);
            canvas.drawPath(mPath, mPaint);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextAlign(CENTER);
            mPaint.setTextSize(30f);
            canvas.drawText(str, (PADDING + (i * smallSection)), SECOND_SECTION - FIRST_SECTION + 25, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);

            mPath.reset();
            mPath.moveTo((float) (PADDING + (i * smallSection)), SECOND_SECTION);
            mPath.lineTo((float) (PADDING + (i * smallSection)), THIRD_SECTION - FIRST_SECTION);
            canvas.drawPath(mPath, mPaint);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextAlign(CENTER);
            mPaint.setTextSize(30f);
            canvas.drawText(str, (PADDING + (i * smallSection)), THIRD_SECTION - FIRST_SECTION + 25, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);

            mPath.reset();
            mPath.moveTo((float) (PADDING + (i * smallSection)), THIRD_SECTION);
            mPath.lineTo((float) (PADDING + (i * smallSection)), LAST_SECTION - FIRST_SECTION);
            canvas.drawPath(mPath, mPaint);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextAlign(CENTER);
            mPaint.setTextSize(30f);
            canvas.drawText(str, (PADDING + (i * smallSection)), LAST_SECTION - FIRST_SECTION + 25, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
        }
    }

    protected void drawImpl(Canvas canvas) {
        canvas.save();
        mPaint.setTextSize(DeviceUtil.getInstance().getDisplayHeight()/20);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#000000"));
        canvas.drawText(mDataList.getTestName(), WIDTH/2, DeviceUtil.getInstance().getDisplayHeight()/13, mPaint);

        mPaint.setTextSize(DeviceUtil.getInstance().getDisplayHeight()/40);
        canvas.translate(0, getHeight());
        canvas.rotate(-90);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#000000"));
        canvas.drawText("Eye X Position", THIRD_SECTION, PADDING-20, mPaint);
        canvas.drawText("Eye Y Position", SECOND_SECTION, PADDING-20, mPaint);

        canvas.restore();
        mPaint.setStyle(Paint.Style.STROKE);
        //----------------------

        background(canvas);

        if (mDataList == null)
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
