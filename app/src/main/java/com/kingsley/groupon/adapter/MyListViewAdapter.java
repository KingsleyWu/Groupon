package com.kingsley.groupon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/16 10:41
 * file change date : on 2017/6/16 10:41
 * version: 1.0
 */

public class MyListViewAdapter extends BaseAdapter {
    private List<String> mDatas;
    private Context mContext;
    public MyListViewAdapter (Context context, List<String> datas){
        mContext = context;
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
