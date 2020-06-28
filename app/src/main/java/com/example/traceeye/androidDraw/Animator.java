package com.example.traceeye.androidDraw;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.traceeye.DeviceUtil;

public class Animator implements Runnable {
    private Handler mainHandler = new AnimationHandler();
    AbstractRenderView myView;
    Thread myThread;

    public Animator(AbstractRenderView v) {
        myView = v;
        //myThread = new Thread(this);
        //myThread.setDaemon(true);
        //myThread.start();
    }

    public void draw(int x, int y) {
        Message msg = Message.obtain();
        msg.what = 0;
        msg.arg1 = x;
        msg.arg2 = y;
        mainHandler.sendMessage(msg);
    }

    class AnimationHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                myView.setTrackerPoint(msg.arg1 + DeviceUtil.getInstance().getAdjustX(),
                        msg.arg2 + DeviceUtil.getInstance().getAdjustY());
                myView.invalidate();
            }
        }
    }

    @Override
    public void run() {
        while (mainHandler != null) {
            try {
                Message msg = Message.obtain();
                msg.what = 0;
                mainHandler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
