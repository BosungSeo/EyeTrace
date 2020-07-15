package com.example.traceeye;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import com.example.traceeye.Gaze.EyesTracker;
import com.example.traceeye.androidDraw.AbstractRenderView;
import com.example.traceeye.data.DataManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;

import static camp.visual.libgaze.Gaze.initGaze;
import static com.example.traceeye.StageManager.ADJUST;
import static com.example.traceeye.StageManager.STAGE1;
import static com.example.traceeye.StageManager.STAGE2;
import static com.example.traceeye.StageManager.STAGE3;
import static com.example.traceeye.StageManager.STAGE4;
import static com.example.traceeye.StageManager.STAGE_REPORT;


public class MainActivity extends AppCompatActivity implements EyesTracker.callback, AbstractRenderView.ViewCallback,
        Button.OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    private EyesTracker mEyesTracker;
    private DataManager mDataManager;
    private StageManager mStageManager;
    private boolean mDoRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceUtil.getInstance().init(this);
        onHome();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            DeviceUtil.getInstance().setDisplay(this.getDisplay());
        } else {
            DeviceUtil.getInstance().setDisplay(this.getWindowManager().getDefaultDisplay());
        }
        mDataManager = new DataManager(this);
        mDoRecord = false;
        mStageManager = new StageManager(this, this);

        doCheckPermission();
    }

    @Override
    public void onClick(View view) {
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
                setContentView(mStageManager.getStage(STAGE_REPORT));
                break;
            case R.id.viewCalBtn:
                Log.d(TAG, "click adjust");
                DeviceUtil.getInstance().resetAdjustPoint();
                setContentView(mStageManager.getStage(ADJUST));
                break;
            case R.id.checkBox:
                CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
                if (checkBox.isChecked()) {
                    Log.d(TAG, "click isChecked");
                    DeviceUtil.getInstance().setShowPoint(true);
                } else {
                    Log.d(TAG, "click isChecked false");
                    DeviceUtil.getInstance().setShowPoint(false);
                }
        }
    }

    @Override
    public void onStartTracker() {
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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DeviceUtil.getInstance().changeOrient();
        mStageManager.onOrientationChange();
    }

    private static final String[] PERMISSIONS = new String[]
            {Manifest.permission.CAMERA};
    private static final int REQ_PERMISSION = 1000;

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
            mEyesTracker = new EyesTracker(this, this);
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
    public void onChangePosition(int x, int y) {
        mStageManager.draw(x, y);
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
        onStopTrackerData();
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.viewReportBtn)).setOnClickListener(this);
        ((Button) findViewById(R.id.view1Btn)).setOnClickListener(this);
        ((Button) findViewById(R.id.view2Btn)).setOnClickListener(this);
        ((Button) findViewById(R.id.view3Btn)).setOnClickListener(this);
        ((Button) findViewById(R.id.view4Btn)).setOnClickListener(this);
        ((Button) findViewById(R.id.viewCalBtn)).setOnClickListener(this);
        ((CheckBox) findViewById(R.id.checkBox)).setOnClickListener(this);
        ((CheckBox) findViewById(R.id.checkBox)).setChecked(DeviceUtil.getInstance().getShowPoint());
    }

    @Override
    public void onStartTrackerData() {
        mDoRecord = true;
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
    public void onFinishRecode() {
        mDataManager.saveData();
        mDataManager.resetRecordData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Log.d("KeyUP Event", "뒤로 가기 키 down");
                onHome();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}