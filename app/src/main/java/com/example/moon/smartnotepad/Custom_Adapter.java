package com.example.moon.smartnotepad;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class Custom_Adapter extends BaseAdapter {
    LayoutInflater inflater;
    Context mContext;
    ArrayList<ObjectForNotes> mArrayList;
    TextView tvdate;
    TextView tvtitle;
    TextView tvdetails;
    CheckBox checkBox;

    public Custom_Adapter(Context mContext, ArrayList<ObjectForNotes> mArrayList) {
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = inflater.inflate(R.layout.custom_adapter,null);
       tvdate = (TextView)view.findViewById(R.id.tv_date);
       tvtitle = (TextView)view.findViewById(R.id.tv_title);
        checkBox = (CheckBox)view.findViewById(R.id.check);
       checkBox.setVisibility(View.INVISIBLE);

       tvdate.setText(mArrayList.get(position).getDate());
       tvtitle.setText(mArrayList.get(position).getTitle());


        return view;
    }
}
