package com.example.traceeye.androidDraw;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {
    protected Paint mPaint;
    public DrawView(Context context) {
        super(context);
        mPaint = new Paint();
    }
}
