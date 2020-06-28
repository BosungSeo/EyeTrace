package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import com.example.traceeye.DeviceUtil;
import com.example.traceeye.LogUtil;

import java.util.ArrayList;

public class AdjustView extends AbstractRenderView {
    private Point mCP;
    private int mStep;
    private final int CHECK_COUNT = 300;
    private final int ADJUST_RANGE = 100;
    private int mCurrentFrame;
    private ArrayList<Point> mAdjustDataList = new ArrayList<>();

    public AdjustView(Context context, ViewCallback callback) {
        super(context, callback);
        mCP = new Point(DeviceUtil.getInstance().getDisplayWidth() / 2, DeviceUtil.getInstance().getDisplayHeight() / 2);
        mStep = 0;
        mCurrentFrame = 0;
        // init device adjust.
        DeviceUtil.getInstance().setAdjustPoint(new Point(0, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurrentFrame < CHECK_COUNT) {
            drawFirstStep(canvas);
        } else {
            drawSecondStep(canvas);
        }
    }

    private void calAdjust() {
        int totalX = 0;
        int totalY = 0;
        int avX;
        int avY;
        ArrayList<Point> avList = new ArrayList<>();
        for (Point p : mAdjustDataList) {
            totalX += p.x;
            totalY += p.y;
        }
        avX = totalX / mAdjustDataList.size();
        avY = totalY / mAdjustDataList.size();
        for (Point p : mAdjustDataList) {
            if ((Math.abs(p.x - avX) < ADJUST_RANGE) && (Math.abs(p.y - avY) < ADJUST_RANGE)) {
                avList.add(p);
            }
        }
        if (avList.size() > (CHECK_COUNT / 2)) {
            Log.d("XXXX","Test Complete");
            totalX = 0;
            totalY = 0;
            for (Point p : avList) {
                totalX += p.x;
                totalY += p.y;
            }
            avX = totalX / avList.size();
            avY = totalY / avList.size();
            DeviceUtil.getInstance().setAdjustPoint(new Point(mCP.x-avX, mCP.y-avY));
            Log.d("XXXX","Success count is "+avList.size());
            Log.d("XXXX","Position is X "+DeviceUtil.getInstance().getAdjustX()+" Y "
                    +DeviceUtil.getInstance().getAdjustY());
        } else {
            Log.d("XXXX","Fail"+avList.size());
            mCurrentFrame = 0;
        }
        mAdjustDataList.clear();
    }

    private void drawFirstStep(Canvas canvas) {
        mPaint.setTextSize(50);
        mCurrentFrame++;
        mPaint.setColor(Color.parseColor("#000000"));
        if (mCurrentFrame < 100) {
            canvas.drawText("측정을 다시 합니다..", 100, 400, mPaint);
        }
        else {
            canvas.drawText("카메라와 시야를 조절합니다.", 100, 400, mPaint);
        }
        canvas.drawText("중앙에 원을 보세요.", 100, 300, mPaint);


        mPaint.setColor(Color.parseColor("#FF0000"));
        canvas.drawCircle(mCP.x, mCP.y, 30, mPaint);
        if (mCurrentFrame > 100) {
            mAdjustDataList.add(new Point(mTrackerX, mTrackerY));
        }
        if (mCurrentFrame == CHECK_COUNT) {
            calAdjust();
        }
    }

    private void drawSecondStep(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#000000"));
        canvas.drawText("조절 완료.", 100, 300, mPaint);
        mPaint.setColor(Color.parseColor("#FF0000"));
        canvas.drawCircle(mCP.x, mCP.y, 30, mPaint);

        mPaint.setColor(Color.parseColor("#0000FF"));
        canvas.drawCircle(mTrackerX, mTrackerY, 10, mPaint);
    }
}
