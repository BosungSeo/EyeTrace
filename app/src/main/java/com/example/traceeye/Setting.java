package com.example.traceeye;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class Setting implements SeekBar.OnSeekBarChangeListener, Button.OnClickListener {
    private final String TAG = Setting.class.getSimpleName();
    MainActivity mUIView;
    SeekBar[] mSeeker = new SeekBar[4];
    TextView[] mTextView = new TextView[4];
    CheckBox checkBox;

    public Setting(MainActivity view) {
        mUIView = view;
    }

    public void Start() {
        mUIView.setContentView(R.layout.activity_setting);
        mSeeker[0] = (SeekBar) mUIView.findViewById(R.id.seekBar1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mSeeker[0].setMin(1);
        }
        mSeeker[0].setMax(6);
        mTextView[0] = (TextView)mUIView.findViewById(R.id.txt1);
        mSeeker[0].setOnSeekBarChangeListener(this);
        mSeeker[0].setProgress(DeviceUtil.getInstance().getStageValue(0));

        mSeeker[1] = (SeekBar) mUIView.findViewById(R.id.seekBar2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mSeeker[1].setMin(1);
        }
        mSeeker[1].setMax(6);
        mTextView[1] = (TextView)mUIView.findViewById(R.id.txt2);
        mSeeker[1].setOnSeekBarChangeListener(this);
        mSeeker[1].setProgress(DeviceUtil.getInstance().getStageValue(1));

        mSeeker[2] = (SeekBar) mUIView.findViewById(R.id.seekBar3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mSeeker[2].setMin(1);
        }
        mSeeker[2].setMax(6);
        mTextView[2] = (TextView)mUIView.findViewById(R.id.txt3);
        mSeeker[2].setOnSeekBarChangeListener(this);
        mSeeker[2].setProgress(DeviceUtil.getInstance().getStageValue(2));

        mSeeker[3] = (SeekBar) mUIView.findViewById(R.id.seekBar4);
        mTextView[3] = (TextView)mUIView.findViewById(R.id.txt4);
        mSeeker[3].setOnSeekBarChangeListener(this);
        mSeeker[3].setProgress(DeviceUtil.getInstance().getStageValue(3));

        checkBox = (CheckBox) mUIView.findViewById(R.id.checkBox);
        checkBox.setOnClickListener(this);
        checkBox.setChecked(DeviceUtil.getInstance().getShowPoint());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        Log.d(TAG, "onProgressChanged" + i);
        switch(seekBar.getId()) {
            case R.id.seekBar1:
                mTextView[0].setText(Integer.toString(i));
                DeviceUtil.getInstance().setStageValue(0,i);
                break;
            case R.id.seekBar2:
                mTextView[1].setText(Integer.toString(i));
                DeviceUtil.getInstance().setStageValue(1,i);
                break;
            case R.id.seekBar3:
                mTextView[2].setText(Integer.toString(i));
                DeviceUtil.getInstance().setStageValue(2,i);
                break;
            case R.id.seekBar4:
                mTextView[3].setText(Integer.toString(i));
                DeviceUtil.getInstance().setStageValue(3,i);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d("Setting", "onStartTrackingTouch");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d("Setting", "onStopTrackingTouch");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkBox:
                if (checkBox.isChecked()) {
                    Log.d(TAG, "click isChecked");
                    DeviceUtil.getInstance().setShowPoint(true);
                } else {
                    Log.d(TAG, "click isChecked false");
                    DeviceUtil.getInstance().setShowPoint(false);
                }
                break;
        }
    }
}
