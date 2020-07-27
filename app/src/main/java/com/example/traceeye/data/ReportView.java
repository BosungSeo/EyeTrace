package com.example.traceeye.data;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.traceeye.MainActivity;
import com.example.traceeye.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportView implements AdapterView.OnItemClickListener, DataManager.Callback{
    private MainActivity mainActivity;
    private ArrayList<ListData> movieDataList;
    private DataAdeptor myAdapter;
    public ReportView(MainActivity activity) {
        mainActivity = activity;
    }
    public void init() {
        movieDataList = new ArrayList<ListData>();
        mainActivity.setContentView(R.layout.activity_report);

        ListView listView = (ListView)mainActivity.findViewById(R.id.listView);
        myAdapter = new DataAdeptor(mainActivity,movieDataList);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);

        DataManager.getInstance().setCallback(this);
        DataManager.getInstance().loadDataTime();
    }

    @Override
    public void databaseResult(DataObject r) {
        if(r != null) {
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
            String getTime = simpleDate.format(new Date(r.mTime));
            movieDataList.add(new ListData(r.mTestName, getTime, r));
            myAdapter.notifyDataSetChanged();
        }
        else {
            showToast("No Data in Cloud or Not connect Internet", false);
        }
    }
    public void showToast(final String msg, final boolean isShort) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mainActivity, msg, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(mainActivity.getApplicationContext(),
                myAdapter.getItem(i).getmTestName(),
                Toast.LENGTH_LONG).show();
        DataManager.getInstance().setCurrentDataList(myAdapter.getItem(i).getData());
        Log.d("DataManager", myAdapter.getItem(i).getData().toString());
        mainActivity.showReportView();
    }
}
