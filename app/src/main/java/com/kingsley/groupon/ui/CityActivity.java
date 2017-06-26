package com.kingsley.groupon.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kingsley.groupon.R;
import com.kingsley.groupon.entity.CityBean;
import com.kingsley.groupon.sql.DBUtil;
import com.kingsley.groupon.util.PinYinUtil;
import com.kingsley.groupon.util.http.HttpUtil;
import com.kingsley.groupon.view.IndexView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.kingsley.groupon.application.MyApplication.cityNameBeanList;

public class CityActivity extends AppCompatActivity {
    private static final String TAG = "CityActivity";
    private RelativeLayout mCityLlToolbar;
    private ImageView mCityIvBack;
    private RadioGroup mCityRg;
    private RadioButton mCityRbAll;
    private RadioButton mCityRbOverseas;
    private LinearLayout mCityLlSearch;
    private RecyclerView mCityRecyclerView;
    private TextView mCityTvWord;
    private IndexView mCityIndexView;
    private CommonAdapter adapter;
    private List<CityBean.CityNameBean> cityNameList;
    private DBUtil dbUtil;
    private String lastCityLetter = "";
    private Map<String,Integer> startIndex;
    private HeaderAndFooterWrapper headerAndFooterWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        dbUtil = new DBUtil(this);
        startIndex = new HashMap<>();
        initView();
        setAdapter();
        setListeners();
    }

    private void setAdapter() {
        cityNameList =new ArrayList<>();
        //adapter = new MyRecyclerViewAdapter(this,cityNameList);
        adapter = new CommonAdapter<CityBean.CityNameBean>(this,R.layout.city_item_layout,cityNameList) {
            @Override
            protected void convert(ViewHolder holder, CityBean.CityNameBean cityNameBean, int position) {

                if (position == startIndex.get(cityNameBean.getCityLetter()) + getHeadersCount()) {
                    holder.getView(R.id.item_iv_city_letter).setVisibility(View.VISIBLE);
                    //Log.i(TAG, "convert: position ="+position);
                }else {
                    holder.getView(R.id.item_iv_city_letter).setVisibility(View.GONE);
                    //Log.i(TAG, "convert: position ="+position+"  CityLetter"+cityNameBean.getCityLetter());
                }
                holder.setText(R.id.item_iv_city_letter,cityNameBean.getCityLetter());
                holder.setText(R.id.item_tv_city_name,cityNameBean.getCityName());
            }
        };
        RecyclerView.LayoutManager  layoutManger = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mCityRecyclerView.setLayoutManager(layoutManger);
        initHeaderView();
        LoadMoreWrapper loadMoreWrapper = new LoadMoreWrapper(headerAndFooterWrapper);
        mCityRecyclerView.setAdapter(loadMoreWrapper);
    }

    private int getHeadersCount() {
        return headerAndFooterWrapper.getHeadersCount();
    }

    private void initHeaderView() {
        headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        View locationCityView = LayoutInflater.from(this).inflate(R.layout.item_city_location, mCityRecyclerView, false);
        TextView cityitemlocation = (TextView) locationCityView.findViewById(R.id.city_item_location);

        View historyCityView = LayoutInflater.from(this).inflate(R.layout.item_city_history, mCityRecyclerView, false);
        Button itembtnhistorycity3 = (Button) historyCityView.findViewById(R.id.item_btn_history_city3);
        Button itembtnhistorycity2 = (Button) historyCityView.findViewById(R.id.item_btn_history_city2);
        Button itembtnhistorycity1 = (Button) historyCityView.findViewById(R.id.item_btn_history_city1);
        TextView itemtvhistorycityname = (TextView) historyCityView.findViewById(R.id.item_tv_history_city_name);

        View zhouBianHotCitySlotView = LayoutInflater.from(this).inflate(R.layout.item_zhou_bian_hot_city_slot, mCityRecyclerView, false);
        Button itembtnzhoubiancity6 = (Button) zhouBianHotCitySlotView.findViewById(R.id.item_btn_zhou_bian_city6);
        Button itembtnzhoubiancity5 = (Button) zhouBianHotCitySlotView.findViewById(R.id.item_btn_zhou_bian_city5);
        Button itembtnzhoubiancity4 = (Button) zhouBianHotCitySlotView.findViewById(R.id.item_btn_zhou_bian_city4);
        Button itembtnzhoubiancity3 = (Button) zhouBianHotCitySlotView.findViewById(R.id.item_btn_zhou_bian_city3);
        Button itembtnzhoubiancity2 = (Button) zhouBianHotCitySlotView.findViewById(R.id.item_btn_zhou_bian_city2);
        Button itembtnzhoubiancity1 = (Button) zhouBianHotCitySlotView.findViewById(R.id.item_btn_zhou_bian_city1);
        TextView itemtvzhoubiancity = (TextView) zhouBianHotCitySlotView.findViewById(R.id.item_tv_zhou_bian_city);
        itemtvzhoubiancity.setText("周边热门城市");


        View guoNeiHotCityView = LayoutInflater.from(this).inflate(R.layout.item_guo_nei_hot_city_slot, mCityRecyclerView, false);
        Button itembtnguoneicity6 = (Button) guoNeiHotCityView.findViewById(R.id.item_btn_guo_nei_city6);
        Button itembtnguoneicity5 = (Button) guoNeiHotCityView.findViewById(R.id.item_btn_guo_nei_city5);
        Button itembtnguoneicity4 = (Button) guoNeiHotCityView.findViewById(R.id.item_btn_guo_nei_city4);
        Button itembtnguoneicity3 = (Button) guoNeiHotCityView.findViewById(R.id.item_btn_guo_nei_city3);
        Button itembtnguoneicity2 = (Button) guoNeiHotCityView.findViewById(R.id.item_btn_guo_nei_city2);
        Button itembtnguoneicity1 = (Button) guoNeiHotCityView.findViewById(R.id.item_btn_guo_nei_city1);
        TextView itemtvguoneicity = (TextView) guoNeiHotCityView.findViewById(R.id.item_tv_guo_nei_city);

        itemtvguoneicity.setText("国内热门城市");


        View haiWaiHotCityView = LayoutInflater.from(this).inflate(R.layout.item_hai_wai_hot_city_slot, mCityRecyclerView, false);
        Button itembtnhaiwaicity6 = (Button) haiWaiHotCityView.findViewById(R.id.item_btn_hai_wai_city6);
        Button itembtnhaiwaicity5 = (Button) haiWaiHotCityView.findViewById(R.id.item_btn_hai_wai_city5);
        Button itembtnhaiwaicity4 = (Button) haiWaiHotCityView.findViewById(R.id.item_btn_hai_wai_city4);
        Button itembtnhaiwaicity3 = (Button) haiWaiHotCityView.findViewById(R.id.item_btn_hai_wai_city3);
        Button itembtnhaiwaicity2 = (Button) haiWaiHotCityView.findViewById(R.id.item_btn_hai_wai_city2);
        Button itembtnhaiwaicity1 = (Button) haiWaiHotCityView.findViewById(R.id.item_btn_hai_wai_city1);
        TextView itemtvhaiwaicity = (TextView) haiWaiHotCityView.findViewById(R.id.item_tv_hai_wai_city);

        itemtvhaiwaicity.setText("海外热门目的地");

        headerAndFooterWrapper.addHeaderView(locationCityView);
        headerAndFooterWrapper.addHeaderView(historyCityView);
        headerAndFooterWrapper.addHeaderView(zhouBianHotCitySlotView);
        headerAndFooterWrapper.addHeaderView(guoNeiHotCityView);
        headerAndFooterWrapper.addHeaderView(haiWaiHotCityView);
    }
    private void setListeners() {
        mCityIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCityRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.city_rb_all:
                        Toast.makeText(CityActivity.this, "全部", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.city_rb_overseas:
                        Toast.makeText(CityActivity.this, "海外", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                position -= getHeadersCount();
                Toast.makeText(CityActivity.this, cityNameList.get(position).getCityName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        mCityIndexView.setOnIndexChangeListener(new IndexView.OnIndexChangeListener() {
            @Override
            public void onIndexChange(String word) {
                mCityTvWord.setText(word);
                int index = 0;
                if ("热门".equals(word)) {
                    index = 0;
                } else {
                    if (startIndex.get(word) != null) {
                        index = startIndex.get(word);
                    } else {
                        if (word.equals("I")) {
                            if (startIndex.get(word) != null) {
                                index = startIndex.get("J");
                                Toast.makeText(CityActivity.this, "没有以当前字母开头的城市,已跳转到下一个字母", Toast.LENGTH_SHORT).show();
                            }
                        } else if (word.equals("U") || word.equals("V")) {
                            if (startIndex.get(word) != null) {
                                index = startIndex.get("W");
                                Toast.makeText(CityActivity.this, "没有以当前字母开头的城市,已跳转到下一个字母", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    index += getHeadersCount();
                }
                LinearLayoutManager layoutManager = (LinearLayoutManager) mCityRecyclerView.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(index, 0);
            }
        });
        mCityLlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityActivity.this,CitySearchActivity.class);
                startActivityForResult(intent,101);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reFresh();
    }

    private void reFresh() {
        if (cityNameBeanList != null){
            cityNameList.addAll(cityNameBeanList);
            adapter.notifyDataSetChanged();
            setIndex(cityNameBeanList);
            Log.d(TAG, "reFresh: cache");
            return;
        }

        cityNameList.addAll(dbUtil.query());
        if (cityNameList != null && cityNameList.size() > 0){
            adapter.notifyDataSetChanged();
            setIndex(cityNameList);
            cityNameBeanList = cityNameList;
            Log.d(TAG, "reFresh: db");
            return;
        }

        HttpUtil.getCities(new Callback<CityBean>() {
            @Override
            public void onResponse(@NonNull Call<CityBean> call, @NonNull retrofit2.Response<CityBean> response) {
                CityBean cityBean = response.body();
                assert cityBean != null;
                List<String> cityList = cityBean.getCities();
                for (String name:cityList) {
                    if (!name.equals("全国") && !name.equals("其它城市") && !name.equals("点评实验室")){
                        CityBean.CityNameBean cityNameBean = new CityBean.CityNameBean();
                        cityNameBean.setCityName(name);
                        cityNameBean.setCityPinYinName(PinYinUtil.getPinYin(name));
                        cityNameBean.setCityLetter(cityNameBean.getCityPinYinName().charAt(0)+"");
                        cityNameList.add(cityNameBean);
                    }
                }
                Collections.sort(cityNameList, new Comparator<CityBean.CityNameBean>() {
                    @Override
                    public int compare(CityBean.CityNameBean o1, CityBean.CityNameBean o2) {
                        return o1.getCityPinYinName().compareTo(o2.getCityPinYinName());
                    }
                });
                Log.d(TAG, "reFresh: net");
                cityNameBeanList = cityNameList;
                adapter.notifyDataSetChanged();
                setIndex(cityNameList);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        long start = System.currentTimeMillis();
                        dbUtil.insertBatch(cityNameList);
                        Log.d("TAG", "写入数据库完毕，耗时："+(System.currentTimeMillis()-start));
                    }
                }.start();
            }

            @Override
            public void onFailure(@NonNull Call<CityBean> call, @NonNull Throwable throwable) {

            }
        });
    }

    private void initView() {
        mCityLlToolbar = (RelativeLayout) findViewById(R.id.city_ll_toolbar);
        mCityIvBack = (ImageView) findViewById(R.id.city_iv_back);
        mCityRg = (RadioGroup) findViewById(R.id.city_rg);
        mCityRbAll = (RadioButton) findViewById(R.id.city_rb_all);
        mCityRbOverseas = (RadioButton) findViewById(R.id.city_rb_overseas);
        mCityLlSearch = (LinearLayout) findViewById(R.id.city_ll_search);
        mCityRecyclerView = (RecyclerView) findViewById(R.id.city_recyclerView);
        mCityRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mCityTvWord = (TextView) findViewById(R.id.city_tv_word);
        mCityIndexView = (IndexView) findViewById(R.id.city_indexView);
    }

    private void setIndex(List<CityBean.CityNameBean> cityNameBeanList) {
        //long startTime = System.currentTimeMillis();
        int position = -1;
        for (CityBean.CityNameBean cityNameBean : cityNameBeanList) {
            position++;
            if (!lastCityLetter.equals(cityNameBean.getCityLetter())) {
                lastCityLetter = cityNameBean.getCityLetter();
                if (startIndex.get(lastCityLetter) == null)
                    startIndex.put(cityNameBean.getCityLetter(), position);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==101){
            Log.i(TAG, "onActivityResult: data="+data.getStringExtra("city"));
            setResult(RESULT_OK,data);
            finish();
        }
    }
}
