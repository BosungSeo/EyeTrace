package com.example.traceeye.Gaze;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.example.traceeye.DeviceUtil;
import com.example.traceeye.MainActivity;

import camp.visual.gazetracker.GazeTracker;
import camp.visual.gazetracker.callback.CalibrationCallback;
import camp.visual.gazetracker.callback.EyeMovementCallback;
import camp.visual.gazetracker.callback.GazeCallback;
import camp.visual.gazetracker.callback.InitializationCallback;
import camp.visual.gazetracker.callback.StatusCallback;
import camp.visual.gazetracker.constant.CalibrationModeType;
import camp.visual.gazetracker.constant.InitializationErrorType;
import camp.visual.gazetracker.constant.StatusErrorType;
import camp.visual.gazetracker.device.GazeDevice;
import camp.visual.gazetracker.state.EyeMovementState;

public class EyesTracker {
    private static final String TAG = EyesTracker.class.getName();
    private GazeTracker mGazeTracker = null;
    MainActivity mContext;
    Callback mCallback;
    private HandlerThread backgroundThread = new HandlerThread("background");
    private Handler backgroundHandler;

    public EyesTracker(MainActivity c, Callback pCallback) {
        mContext = c;
        mCallback = pCallback;
    }

    public void stop() {
        if (mGazeTracker != null) {
            this.mGazeTracker.stopTracking();
            this.mGazeTracker.removeCallbacks();
        }
        backgroundThread.quitSafely();
    }
    public void start() {
        if (mGazeTracker != null) {
            this.mGazeTracker.setCallbacks(gazeCallback, calibrationCallback, eyeMovementCallback, statusCallback);
            //}
            this.mGazeTracker.setTrackingFPS(30);
            this.mGazeTracker.startTracking();
            this.mCallback.onStartTracker();
        }
    }

    public void initGaze() {
        GazeDevice gazeDevice = new GazeDevice();
        gazeDevice.getCurrentDeviceInfo();
        gazeDevice.addDeviceInfo(Build.MODEL, 0.0f, 0.0f);
        String licenseKey = "dev_nsxejtconuuwunblh6u6ahcepo3qj3tix528n4xk";
        GazeTracker.initGazeTracker(mContext.getApplicationContext(), gazeDevice, licenseKey, initializationCallback);
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    private void initSuccess(GazeTracker gazeTracker) {
        this.mGazeTracker = gazeTracker;
        start();
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
            // Log.i(TAG, "gaze filterd coord " + x + "x" + y);
            mCallback.onChangePosition(Math.round(x), Math.round(y));

        }
    };
    public CalibrationCallback calibrationCallback = new CalibrationCallback() {
        @Override
        public void onCalibrationProgress(float progress) {
            //setCalibrationProgress(progress);
            mCallback.onCalibrationProgress(progress);
        }

        @Override
        public void onCalibrationNextPoint(final float x, final float y) {
            Log.d(TAG, "onCalibrationNextPoint: " + x+ " "+y);
            mCallback.onCalibrationNextPoint(x,y);

            backgroundHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startCollectSamples();
                }
            }, 5000);
        }

        @Override
        public void onCalibrationFinished() {
            mCallback.onCalibrationFinished();
        }
    };
    public boolean startCalibration() {
        boolean isSuccess = false;
        if (mGazeTracker != null) {
            if(DeviceUtil.getInstance().getFiveCalPoint() == true) {
                isSuccess = mGazeTracker.startCalibration(CalibrationModeType.FIVE_POINT);
            } else {
                isSuccess = mGazeTracker.startCalibration(CalibrationModeType.ONE_POINT);
            }
        }
        return isSuccess;
    }
    private boolean startCollectSamples() {
        boolean isSuccess = false;
        if (mGazeTracker != null) {
            isSuccess = mGazeTracker.startCollectSamples();
        }
        return isSuccess;
    }
    private EyeMovementCallback eyeMovementCallback = new EyeMovementCallback() {
        @Override
        public void onEyeMovement(long timestamp, long duration, float x, float y, int state) {
            String type = "UNKNOWN";
            if (state == EyeMovementState.FIXATION) {
                type = "FIXATION";
            } else if (state == EyeMovementState.SACCADE) {
                type = "SACCADE";
            } else {
                type = "UNKNOWN";
            }
        }
    };
    private StatusCallback statusCallback = new StatusCallback() {
        @Override
        public void onStarted() {
            // isTracking true
            mContext.showToast("Start", false);
        }

        @Override
        public void onStopped(int error) {
            // isTracking false
            if (error != StatusErrorType.ERROR_NONE) {
                switch (error) {
                    case StatusErrorType.ERROR_CAMERA_START:
                        mContext.showToast("ERROR_CAMERA_START ", false);
                        break;
                    case StatusErrorType.ERROR_CAMERA_INTERRUPT:
                        mContext.showToast("ERROR_CAMERA_INTERRUPT ", false);
                        break;
                }
            }
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
                mContext.showToast(err, false);
            }
        }
    };

    public interface Callback {
        void onChangePosition(int x, int y);

        void onStartTracker();

        void onCalibrationProgress(float progress);

        void onCalibrationNextPoint(final float x, final float y);

        void onCalibrationFinished();
    }
}
