package com.example.traceeye;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.traceeye.Gaze.EyesTracker;
import com.example.traceeye.androidDraw.AbstractRenderView;
import com.example.traceeye.data.DataManager;
import com.example.traceeye.data.ReportView;

import static com.example.traceeye.StageManager.ADJUST;
import static com.example.traceeye.StageManager.STAGE1;
import static com.example.traceeye.StageManager.STAGE2;
import static com.example.traceeye.StageManager.STAGE3;
import static com.example.traceeye.StageManager.STAGE4;
import static com.example.traceeye.StageManager.STAGE_REPORT;


public class MainActivity extends AppCompatActivity implements AbstractRenderView.ViewCallback,
        Button.OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    private static final String[] PERMISSIONS = new String[]
            {Manifest.permission.CAMERA};
    private static final int REQ_PERMISSION = 1000;
    private EyesTracker mEyesTracker;
    private DataManager mDataManager;
    private StageManager mStageManager;
    private SettingView mSettingView;
    private ReportView mReportView;
    private boolean mDoRecord;
    private int mStage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceUtil.getInstance().init(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            DeviceUtil.getInstance().setDisplay(this.getDisplay());
        } else {
            DeviceUtil.getInstance().setDisplay(this.getWindowManager().getDefaultDisplay());
        }
        onHome();
        mDataManager = DataManager.getInstance();
        mDoRecord = false;
        mStageManager = new StageManager(this, this);
        mSettingView = new SettingView(this);
        mReportView = new ReportView(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        doCheckPermission();
    }

    @Override
    public void onClick(View view) {
        mStage = view.getId();
        switch (view.getId()) {
            case R.id.view1Btn:
                Log.d(TAG, "click animation");
                setContentView(mStageManager.getStage(STAGE1));
                break;
            case R.id.view2Btn:
                Log.d(TAG, "click animation");
                setContentView(mStageManager.getStage(STAGE2));
                break;
            case R.id.view3Btn:
                Log.d(TAG, "click animation");
                setContentView(mStageManager.getStage(STAGE3));
                break;
            case R.id.view4Btn:
                Log.d(TAG, "click animation");
                setContentView(mStageManager.getStage(STAGE4));
                break;
            case R.id.viewReportBtn:
                Log.d(TAG, "click animation");
                mReportView.init();
                // setContentView(mStageManager.getStage(STAGE_REPORT));
                break;
            case R.id.viewCalBtn:
                Log.d(TAG, "click adjust");
                DeviceUtil.getInstance().resetAdjustPoint();
                setContentView(mStageManager.getStage(ADJUST));

                break;

            case R.id.settingBtn:
                mSettingView.Start();
                break;
        }
    }

    public void showReportView() {
        setContentView(mStageManager.getStage(STAGE_REPORT));
    }

    private void startView() {
        setContentView(mStageManager.getStage(ADJUST));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mEyesTracker.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEyesTracker.start();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DeviceUtil.getInstance().changeOrient();
        if (mStage == 0)
            onHome();
        mStageManager.onOrientationChange();
    }

    private void doCheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check permission status
            if (!hasPermissions(PERMISSIONS)) {
                requestPermissions(PERMISSIONS, REQ_PERMISSION);
            } else {
                checkPermission(true);
            }
        } else {
            checkPermission(true);
        }
    }

    private boolean hasPermissions(String[] permissions) {
        int result;
        // Check permission status in string array
        for (String perms : permissions) {
            if (perms.equals(Manifest.permission.SYSTEM_ALERT_WINDOW)) {
                if (!Settings.canDrawOverlays(this)) {
                    return false;
                }
            }
            result = ContextCompat.checkSelfPermission(this, perms);
            if (result == PackageManager.PERMISSION_DENIED) {
                // When if unauthorized permission found
                return false;
            }
        }
        // When if all permission allowed
        return true;
    }

    private void checkPermission(boolean isGranted) {
        if (isGranted) {
            mEyesTracker = new EyesTracker(this, mStageManager);
            mEyesTracker.initGaze();
        } else {
            // showToast("not granted permissions", true);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION:
                if (grantResults.length > 0) {
                    boolean cameraPermissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraPermissionAccepted) {
                        checkPermission(true);
                    } else {
                        checkPermission(false);
                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //mAnimationView = new FirstGLView(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNext(int i) {
        onStopTrackerData();
        switch (i) {
            case 1:
                setContentView(mStageManager.getStage(STAGE4));
                break;
            case 3:
                onHome();
                break;
        }
    }

    @Override
    public void onHome() {
        mStage = 0;
        onStopTrackerData();
        if (DeviceUtil.getInstance().getOrient()) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_main_land);
        }
        ((Button) findViewById(R.id.viewReportBtn)).setOnClickListener(this);
        ((Button) findViewById(R.id.view1Btn)).setOnClickListener(this);
        ((Button) findViewById(R.id.view2Btn)).setOnClickListener(this);
        ((Button) findViewById(R.id.view3Btn)).setOnClickListener(this);
        ((Button) findViewById(R.id.view4Btn)).setOnClickListener(this);
        ((Button) findViewById(R.id.viewCalBtn)).setOnClickListener(this);
        ((Button) findViewById(R.id.settingBtn)).setOnClickListener(this);
    }

    @Override
    public void onStartTrackerData() {
        if (mStage == R.id.viewCalBtn) {
            mEyesTracker.startCalibration();
        } else {
            mDataManager.initRecord();
            mDoRecord = true;
        }
    }

    @Override
    public void onStopTrackerData() {
        mDoRecord = false;
    }

    @Override
    public void onRecodeTargetPosition(int eyeX, int eyeY, int targetX, int targetY) {
        if (mDoRecord) {
            mDataManager.recordTracker(eyeX, eyeY, targetX, targetY);
        }
    }

    @Override
    public void onFinishRecode(int frame) {
        String testName = "none";
        switch (mStage) {
            case R.id.view1Btn:
                testName = getResources().getString(R.string.test_1_report);
                break;
            case R.id.view2Btn:
                testName = getResources().getString(R.string.test_2_report);
                break;
            case R.id.view3Btn:
                testName = getResources().getString(R.string.test_3_report);
                break;
            case R.id.view4Btn:
                testName = getResources().getString(R.string.test_4_report);
                break;
        }
        mDataManager.saveData(testName, frame);
        // mDataManager.resetRecordData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mStage == 0) {
                    break;
                }
                onHome();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showToast(final String msg, final boolean isShort) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, msg, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
            }
        });
    }
}