package com.kingsley.groupon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingsley.groupon.R;

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
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_layout, parent, false);
            viewHolder.mItemIvImage = (ImageView) convertView.findViewById(R.id.item_iv_image);
            viewHolder.mItemIvMainyuyue = (ImageView) convertView.findViewById(R.id.item_iv_mainyuyue);
            viewHolder.mItemTvDian = (TextView) convertView.findViewById(R.id.item_tv_dian);
            viewHolder.mItemTvDianmiaoshu = (TextView) convertView.findViewById(R.id.item_tv_dianmiaoshu);
            viewHolder.mItemTvJiage = (TextView) convertView.findViewById(R.id.item_tv_jiage);
            viewHolder.mItemIvYishou = (TextView) convertView.findViewById(R.id.item_iv_yishou);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mItemTvJiage.setText(mDatas.get(position));
        return convertView;
    }

    private class ViewHolder{
        private ImageView mItemIvImage;
        private ImageView mItemIvMainyuyue;
        private TextView mItemTvDian;
        private TextView mItemTvDianmiaoshu;
        private TextView mItemTvJiage;
        private TextView mItemIvYishou;

    }
}
