package com.kingsley.groupon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/20 10:37
 * file change date : on 2017/6/20 10:37
 * version: 1.0
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private Context mContext;
    LayoutInflater inflater;
    List<T> mList;

    public MyBaseAdapter(Context context, List<T> list) {
        mList = list;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(T t){
        mList.add(t);
        notifyDataSetChanged();
    }

    public void addAll(List<T> list,boolean isClear){
        if (isClear)mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(T t){
        mList.remove(t);
        notifyDataSetChanged();
    }

    public void removeAll(){
        mList.clear();
        notifyDataSetChanged();
    }
}
