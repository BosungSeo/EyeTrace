package com.example.traceeye.Gaze;

import android.content.Context;
import android.util.Log;

import com.example.traceeye.DeviceUtil;

import camp.visual.gazetracker.GazeTracker;
import camp.visual.gazetracker.callback.GazeCallback;
import camp.visual.gazetracker.callback.InitializationCallback;
import camp.visual.gazetracker.constant.InitializationErrorType;
import camp.visual.gazetracker.device.GazeDevice;

public class EyesTracker {
    private static final String TAG = EyesTracker.class.getName();
    private GazeTracker mGazeTracker = null;
    Context mContext;
    callback mCallback;
    public EyesTracker(Context c, callback pCallback) {
        mContext = c;
        mCallback = pCallback;
    }
    public void stop() {
        if(mGazeTracker != null) {
            this.mGazeTracker.stopTracking();
            this.mGazeTracker.removeCallbacks();
        }
    }
    public void initGaze() {
        GazeDevice gazeDevice = new GazeDevice();
        gazeDevice.addDeviceInfo("SM-T720", -72f, -4f); // tab s5e
        String licenseKey = "dev_nsxejtconuuwunblh6u6ahcepo3qj3tix528n4xk";
        GazeTracker.initGazeTracker(mContext.getApplicationContext(), gazeDevice, licenseKey, initializationCallback);
    }
    private void initSuccess(GazeTracker gazeTracker) {
        this.mGazeTracker = gazeTracker;
        //if (preview.isAvailable()) {
        //setCameraPreview(preview);
        this.mGazeTracker.setGazeCallback(gazeCallback);
        //}
        this.mGazeTracker.startTracking();
        this.mCallback.onStartTracker();
        // this.gazeTracker.hideProgress();
    }
    private void initFail(int error) {
        String err = "";
        if (error == InitializationErrorType.ERROR_CAMERA_PERMISSION) {
            // When if camera permission doesn not exists
            err = "required permission not granted";
        } else if (error == InitializationErrorType.ERROR_AUTHENTICATE) {
            // Authentication failure (License Key)
            err = "authentication failed";
        } else {
            // Gaze library initialization failure
            // It can ba caused by several reasons(i.e. Out of memory).
            err = "init gaze library fail";
        }
        Log.w(TAG, "error description: " + err);
    }

    private GazeCallback gazeCallback = new GazeCallback() {
        @Override
        public void onGaze(long timestamp, float x, float y, int state) {
            //Log.i(TAG, "gaze coord " + x + "x" + y);

        }

        @Override
        public void onFilteredGaze(long timestamp, float x, float y, int state) {
            Log.i(TAG, "gaze filterd coord " + x + "x" + y);
            mCallback.onChangePosition(Math.round(x), Math.round(y));

        }
    };

    private InitializationCallback initializationCallback = new InitializationCallback() {
        @Override
        public void onInitialized(GazeTracker gazeTracker, int error) {
            if (gazeTracker != null) {
                initSuccess(gazeTracker);
            } else {
                String err = "";
                if (error == InitializationErrorType.ERROR_CAMERA_PERMISSION) {
                    // When if camera permission doesn not exists
                    err = "required permission not granted";
                } else if (error == InitializationErrorType.ERROR_AUTHENTICATE) {
                    // Authentication failure (License Key)
                    err = "authentication failed";
                } else {
                    // Gaze library initialization failure
                    // It can ba caused by several reasons(i.e. Out of memory).
                    err = "init gaze library fail";
                }
                Log.w(TAG, "error description: " + err);
            }
        }
    };
    public interface callback {
        void onChangePosition(int x, int y);
        void onStartTracker();
    }
}
