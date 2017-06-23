package com.kingsley.groupon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingsley.groupon.R;
import com.kingsley.groupon.entity.CityBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/21 18:51
 * file change date : on 2017/6/21 18:51
 * version: 1.0
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{
    private List<CityBean.CityNameBean> cityNameList;
    private LayoutInflater inflater;
    private String lastCityLetter = "";
    private Map<String,Integer> startIndex;

    public MyRecyclerViewAdapter(Context context, List<CityBean.CityNameBean> list) {
        cityNameList = list;
        inflater = LayoutInflater.from(context);
        startIndex = new HashMap<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflater.inflate(R.layout.city_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CityBean.CityNameBean cityNameBean = cityNameList.get(position);
        //Log.i("TAG", "onBindViewHolder: "+cityNameBean);
        if (!lastCityLetter.equals(cityNameBean.getCityLetter())) {
            lastCityLetter = cityNameBean.getCityLetter();
            if (startIndex.get(lastCityLetter) == null)
            startIndex.put(cityNameBean.getCityLetter(),position);
         }
        if (position == startIndex.get(lastCityLetter)){
            holder.tvCityLetter.setVisibility(View.VISIBLE);
        }else {
            holder.tvCityLetter.setVisibility(View.GONE);
        }
        holder.tvCityLetter.setText(cityNameBean.getCityLetter());
        holder.tvCityName.setText(cityNameBean.getCityName());
        if (onItemClickListener != null){
            View view = holder.itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //Log.i("TAG", "getItemCount: " + cityNameList.size());
        return cityNameList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_iv_city_letter)
        TextView tvCityLetter;
        @BindView(R.id.item_tv_city_name)
        TextView tvCityName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void addAll(List<CityBean.CityNameBean> list, boolean isClear) {
        if (isClear) {
            cityNameList.clear();
        }
        cityNameList.addAll(list);
        //Log.i("TAG", "addAll: " + list);
        notifyDataSetChanged();
    }

    public void removeAll() {
        cityNameList.clear();
        notifyDataSetChanged();
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
