package com.example.traceeye.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.traceeye.R;

import java.util.ArrayList;

public class DataAdeptor extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ListData> sample;

    public DataAdeptor(Context context, ArrayList<ListData> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ListData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.report_item_view, null);

        TextView dateText = (TextView) view.findViewById(R.id.test_date);
        TextView nameText = (TextView) view.findViewById(R.id.test_name);

        dateText.setText(sample.get(position).getmTestName());
        nameText.setText(sample.get(position).getmDate());
        return view;
    }
}
