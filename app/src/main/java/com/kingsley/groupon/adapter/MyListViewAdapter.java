package com.kingsley.groupon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingsley.groupon.R;
import com.kingsley.groupon.entity.TuanBean;
import com.kingsley.groupon.util.HttpUtil;

import java.util.List;
import java.util.Random;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/16 10:41
 * file change date : on 2017/6/16 10:41
 * version: 1.0
 */

public class MyListViewAdapter extends MyBaseAdapter<TuanBean.Deal> {

    public MyListViewAdapter(Context context, List<TuanBean.Deal> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.main_item_layout, parent, false);
            viewHolder.mItemIvImage = (ImageView) convertView.findViewById(R.id.item_iv_image);
            viewHolder.mItemIvMainyuyue = (ImageView) convertView.findViewById(R.id.item_iv_mainyuyue);
            viewHolder.mItemTvDian = (TextView) convertView.findViewById(R.id.item_tv_dian);
            viewHolder.mItemTvJuLi = (TextView) convertView.findViewById(R.id.item_tv_juli);
            viewHolder.mItemTvDianmiaoshu = (TextView) convertView.findViewById(R.id.item_tv_dianmiaoshu);
            viewHolder.mItemTvJiage = (TextView) convertView.findViewById(R.id.item_tv_jiage);
            viewHolder.mItemIvYishou = (TextView) convertView.findViewById(R.id.item_iv_yishou);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TuanBean.Deal deal = mList.get(position);
        HttpUtil.loadImage(deal.getS_image_url(),viewHolder.mItemIvImage);
        viewHolder.mItemTvDian.setText(deal.getTitle());
        viewHolder.mItemTvDianmiaoshu.setText(deal.getDescription());
        int juLi = new Random().nextInt(1500)+500;
        String juLi1;
        if (juLi >= 1000){
            double d =Math.round(juLi/1000.0*100);
            juLi1 = String.valueOf(d/100)+"km";

        }else {
            juLi1 = String.valueOf(juLi)+"m";
        }
        viewHolder.mItemTvJuLi.setText(juLi1);
        viewHolder.mItemTvJiage.setText("¥ "+deal.getCurrent_price());
        viewHolder.mItemIvYishou.setText("已售 "+(new Random().nextInt(1000)+50));
        return convertView;
    }

    private class ViewHolder{
        private ImageView mItemIvImage;
        private ImageView mItemIvMainyuyue;
        private TextView mItemTvDian;
        private TextView mItemTvJuLi;
        private TextView mItemTvDianmiaoshu;
        private TextView mItemTvJiage;
        private TextView mItemIvYishou;

    }
}
