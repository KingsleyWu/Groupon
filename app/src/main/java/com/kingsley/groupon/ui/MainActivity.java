package com.kingsley.groupon.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kingsley.groupon.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mMainTvAddress;
    private LinearLayout mMainLlSearch;
    private ImageView mMainIvAdd;

    private FrameLayout mMainFrameLayout;
    private View mMainMenuLayout;
    private PullToRefreshListView mMainPrListView;
    private ListView mMainListView;
    private TextView mMainMenuTvComment;
    private TextView mMainMenuTvStore;
    private TextView mMainMenuTvScan;
    private TextView mMainMenuTvPlay;

    private RadioGroup mRgMainFooter;
    private RadioButton mRbMainFooterHome;
    private RadioButton mRbMainFooterGroup;
    private RadioButton mRbMainFooterFind;
    private RadioButton mRbMainFooterMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListeners();
        setAdapter();
    }

    //初始化View
    private void initView() {
        mMainTvAddress = (TextView) findViewById(R.id.main_tv_address);
        mMainLlSearch = (LinearLayout) findViewById(R.id.main_ll_search);
        mMainIvAdd = (ImageView) findViewById(R.id.main_iv_add);

        mMainFrameLayout = (FrameLayout) findViewById(R.id.main_frameLayout);
        mMainPrListView = (PullToRefreshListView) findViewById(R.id.Main_prListView);
        mMainListView = mMainPrListView.getRefreshableView();

        mMainMenuLayout = findViewById(R.id.main_menu_layout);
        mMainMenuTvComment = (TextView) findViewById(R.id.main_menu_tv_comment);
        mMainMenuTvStore = (TextView) findViewById(R.id.main_menu_tv_store);
        mMainMenuTvScan = (TextView) findViewById(R.id.main_menu_tv_scan);
        mMainMenuTvPlay = (TextView) findViewById(R.id.main_menu_tv_play);

        mRgMainFooter = (RadioGroup) findViewById(R.id.rg_main_footer);

        mRbMainFooterHome = (RadioButton) findViewById(R.id.rb_main_footer_home);
        mRbMainFooterGroup = (RadioButton) findViewById(R.id.rb_main_footer_group);
        mRbMainFooterFind = (RadioButton) findViewById(R.id.rb_main_footer_find);
        mRbMainFooterMy = (RadioButton) findViewById(R.id.rb_main_footer_my);
    }

    private void setAdapter() {
       // mMainListView.setAdapter();
    }
    //设置监听
    private void setListeners() {
        mMainTvAddress.setOnClickListener(this);
        mMainLlSearch.setOnClickListener(this);
        mMainIvAdd.setOnClickListener(this);

        mMainPrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        mRgMainFooter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_main_footer_home:
                        break;
                    case R.id.rb_main_footer_group:
                        break;
                    case R.id.rb_main_footer_find:
                        break;
                    case R.id.rb_main_footer_my:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_tv_address:
                break;
            case R.id.main_ll_search:
                break;
            case R.id.main_iv_add:
                break;
        }
    }
}
