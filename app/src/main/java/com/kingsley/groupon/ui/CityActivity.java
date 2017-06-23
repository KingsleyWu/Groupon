package com.kingsley.groupon.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
                if (!lastCityLetter.equals(cityNameBean.getCityLetter())) {
                    lastCityLetter = cityNameBean.getCityLetter();
                    if (startIndex.get(lastCityLetter) == null)
                        startIndex.put(cityNameBean.getCityLetter(),position);
                 }
                if (position == startIndex.get(lastCityLetter)){
                    holder.getView(R.id.item_iv_city_letter).setVisibility(View.VISIBLE);
                }else {
                    holder.getView(R.id.item_iv_city_letter).setVisibility(View.GONE);
                    Log.i(TAG, "convert: position ="+position);
                }
                holder.setText(R.id.item_iv_city_letter,cityNameBean.getCityLetter());
                holder.setText(R.id.item_tv_city_name,cityNameBean.getCityName());
            }
        };
        RecyclerView.LayoutManager  layoutManger = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mCityRecyclerView.setLayoutManager(layoutManger);
        mCityRecyclerView.setAdapter(adapter);
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
       mCityIndexView.setOnIndexChangeListener(new IndexView.OnIndexChangeListener() {
            @Override
            public void onIndexChange(String word) {
                mCityTvWord.setText(word);
                //Log.i(TAG, "onIndexChange: word="+word);
            }
        });
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(CityActivity.this, cityNameList.get(position).getCityName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
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
            Log.d(TAG, "reFresh: cache");
            return;
        }

        cityNameList.addAll(dbUtil.query());
        if (cityNameList != null && cityNameList.size() > 0){
            adapter.notifyDataSetChanged();
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
}
