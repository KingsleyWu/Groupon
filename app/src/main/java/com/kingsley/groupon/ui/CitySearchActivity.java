package com.kingsley.groupon.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kingsley.groupon.R;
import com.kingsley.groupon.application.MyApplication;
import com.kingsley.groupon.entity.CityBean;

import java.util.ArrayList;
import java.util.List;

public class CitySearchActivity extends AppCompatActivity {
    private static final String TAG = "CitySearchActivity";

    private ImageView mCitySearchIvBack;
    private LinearLayout mCitySearchLlSearch;
    private EditText mCitySearchEtSearch;
    private ListView mLvSearchListView;
    private List<String> cities;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);

        initView();
        setListener();
    }

    private void initView() {
        mCitySearchIvBack = (ImageView) findViewById(R.id.city_search_iv_back);
        mCitySearchLlSearch = (LinearLayout) findViewById(R.id.city_search_ll_search);
        mCitySearchEtSearch = (EditText) findViewById(R.id.city_search_et_search);
        mLvSearchListView = (ListView) findViewById(R.id.lv_search_listView);
        initListView(mLvSearchListView);
    }

    private void initListView(ListView mLvSearchListView) {
        cities = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
        mLvSearchListView.setAdapter(adapter);
    }

    private void setListener(){
        mCitySearchIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCitySearchEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){
                    cities.clear();
                    adapter.notifyDataSetChanged();
                }else {
                    searchCity(s.toString().toUpperCase());
                }
            }
        });
        mLvSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent data = new Intent();
                String city = adapter.getItem(position);
                Log.i(TAG, "onItemClick: city="+city);
                data.putExtra("city",city);
                setResult(RESULT_OK,data);
                finish();
            }
        });
    }

    private void searchCity(String s) {
        List<String> temps = new ArrayList<>();
        if (s.matches("[\u4e00-\u9fff]+")){
            for (CityBean.CityNameBean cityNameBean: MyApplication.cityNameBeanList){
                if (cityNameBean.getCityName().contains(s)){
                    temps.add(cityNameBean.getCityName());
                }
            }
        }else {
            for (CityBean.CityNameBean cityNameBean: MyApplication.cityNameBeanList){
                if (cityNameBean.getCityPinYinName().contains(s)){
                    temps.add(cityNameBean.getCityName());
                }
            }
        }
        if (temps.size() > 0){
            cities.clear();
            cities.addAll(temps);
            adapter.notifyDataSetChanged();
        }
    }

}
