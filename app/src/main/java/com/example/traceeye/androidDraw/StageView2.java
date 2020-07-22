package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.traceeye.DeviceUtil;

public class StageView2 extends AbstractRenderView {
    class MoveFrame {
        public MoveFrame(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float x;
        public float y;
    }

    private int mCount = 0;
    private final int RECT_SIZE = 300;
    private final int FRAME = 30 * DeviceUtil.getInstance().getStageValue(1);
    private final int FRAME_SCENE = 2;
    Path mPath;
    private MoveFrame[] mRectTargetPoint = new MoveFrame[FRAME_SCENE + 1];
    private MoveFrame[] mRectMove = new MoveFrame[FRAME_SCENE];

    private MoveFrame[] mStarTargetPoint1 = new MoveFrame[FRAME_SCENE + 1];
    private MoveFrame[] mRectStar1 = new MoveFrame[FRAME_SCENE];
    private MoveFrame[] mStarTargetPoint2 = new MoveFrame[FRAME_SCENE + 1];
    private MoveFrame[] mRectStar2 = new MoveFrame[FRAME_SCENE];

    private MoveFrame[] mTriangleTargetPoint = new MoveFrame[FRAME_SCENE + 1];
    private MoveFrame[] mRectTriangle = new MoveFrame[FRAME_SCENE];

    public StageView2(Context context, ViewCallback callback) {
        super(context, callback);
        mPath = new Path();
        calPoint();
    }

    private void calPoint() {
        // rect
        mRectTargetPoint[0] = new MoveFrame(getTransX(75), getTransY(80));
        mRectTargetPoint[1] = new MoveFrame(getTransX(60), getTransY(20));
        mRectTargetPoint[2] = new MoveFrame(getTransX(20), getTransY(70));
        calPoint(mRectTargetPoint, mRectMove);

        mStarTargetPoint1[0] = new MoveFrame(getTransX(60), getTransY(15));
        mStarTargetPoint1[1] = new MoveFrame(getTransX(70), getTransY(75));
        mStarTargetPoint1[2] = new MoveFrame(getTransX(40), getTransY(50));
        calPoint(mStarTargetPoint1, mRectStar1);

        // star2
        mStarTargetPoint2[0] = new MoveFrame(getTransX(20), getTransY(70));
        mStarTargetPoint2[1] = new MoveFrame(getTransX(60), getTransY(65));
        mStarTargetPoint2[2] = new MoveFrame(getTransX(50), getTransY(75));
        calPoint(mStarTargetPoint2, mRectStar2);

        mTriangleTargetPoint[0] = new MoveFrame(getTransX(50), getTransY(50));
        mTriangleTargetPoint[1] = new MoveFrame(getTransX(20), getTransY(30));
        mTriangleTargetPoint[2] = new MoveFrame(getTransX(60), getTransY(80));
        calPoint(mTriangleTargetPoint, mRectTriangle);
    }

    private void calPoint(MoveFrame[] target, MoveFrame[] move) {
        move[0] = new MoveFrame((target[0].x - target[1].x) / FRAME,
                (target[0].y - target[1].y) / FRAME);
        move[1] = new MoveFrame((target[1].x - target[2].x) / FRAME,
                (target[1].y - target[2].y) / FRAME);
    }

    @Override
    protected void drawImpl(Canvas canvas) {
        mCount++;
        int c = mCount / FRAME;
        if (FRAME_SCENE == c) {
            finish();
            goHome();
            return;
        }

        drawRect(canvas, Math.round(mRectTargetPoint[c].x - (mRectMove[c].x * (mCount % FRAME))),
                Math.round(mRectTargetPoint[c].y - (mRectMove[c].y * (mCount % FRAME))));

        drawStar(canvas, Math.round(mStarTargetPoint1[c].x - (mRectStar1[c].x * (mCount % FRAME))),
                Math.round(mStarTargetPoint1[c].y - (mRectStar1[c].y * (mCount % FRAME))));

        drawStar(canvas, Math.round(mStarTargetPoint2[c].x - (mRectStar2[c].x * (mCount % FRAME))),
                Math.round(mStarTargetPoint2[c].y - (mRectStar2[c].y * (mCount % FRAME))));

        drawDoubleTriangle(canvas, Math.round(mTriangleTargetPoint[c].x - (mRectTriangle[c].x * (mCount % FRAME))),
                Math.round(mTriangleTargetPoint[c].y - (mRectTriangle[c].y * (mCount % FRAME))));
    }

    private void drawStar(Canvas canvas, float x, float y) {
        int radius = 200;
        y = y - radius;

        int numOfPt = 5;
        double section = 2.0 * Math.PI / numOfPt;
        int innerRadius = (int) (radius / 2.5);
        mPath.reset();
        mPath.moveTo(
                (float) (x + radius * Math.cos(0)),
                (float) (y + radius * Math.sin(0)));
        mPath.lineTo(
                (float) (x + innerRadius * Math.cos(0 + section / 2.0)),
                (float) (y + innerRadius * Math.sin(0 + section / 2.0)));
        for (int i = 1; i < numOfPt; i++) {
            mPath.lineTo(
                    (float) (x + radius * Math.cos(section * i)),
                    (float) (y + radius * Math.sin(section * i)));
            mPath.lineTo(
                    (float) (x + innerRadius * Math.cos(section * i + section / 2.0)),
                    (float) (y + innerRadius * Math.sin(section * i + section / 2.0)));
        }
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void drawRect(Canvas canvas, int x, int y) {
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(new Rect(x, y, x + RECT_SIZE, y + RECT_SIZE), mPaint);
    }

    private void drawDoubleTriangle(Canvas canvas, int x, int y) {
        int side = 200;
        int height = RECT_SIZE;
        y = y - height;
        mViewCallback.onRecodeTargetPosition(mTrackerX, mTrackerY, x, y);
        mPath.reset();
        y = y - 70;
        Point point1_draw = new Point((int) x, (int) y);        // 왼
        Point point2_draw = new Point((int) x - side - 50, (int) y + height - 50);  // 아래
        Point point3_draw = new Point((int) x + side - 100, (int) y + height + 50); // 오른
        mPath.moveTo(point1_draw.x, point1_draw.y);
        mPath.lineTo(point2_draw.x, point2_draw.y);
        mPath.lineTo(point3_draw.x, point3_draw.y);
        mPath.lineTo(point1_draw.x, point1_draw.y);
        mPath.close();

        canvas.drawPath(mPath, mPaint);
        mPath.reset();
        y = y + 140;
        point1_draw = new Point((int) x, (int) y);        // 왼
        point2_draw = new Point((int) x - side + 100, (int) y - height - 50);  // 아래
        point3_draw = new Point((int) x + side + 50, (int) y - height + 50); // 오른
        mPath.moveTo(point1_draw.x, point1_draw.y);
        mPath.lineTo(point2_draw.x, point2_draw.y);
        mPath.lineTo(point3_draw.x, point3_draw.y);
        mPath.lineTo(point1_draw.x, point1_draw.y);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}
