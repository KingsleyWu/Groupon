package com.kingsley.groupon.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mMainTvAddress;
    private LinearLayout mMainLlSearch;
    private ImageView mMainIvAdd;

    private FrameLayout mMainFrameLayout;
    private View mMainMenuLayout;
    private PullToRefreshListView mMainPrListView;
    private ListView mMainListView;
    private List<String> datas;
    private ArrayAdapter<String> adapter;
    private TextView mMainMenuTvComment;
    private TextView mMainMenuTvStore;
    private TextView mMainMenuTvScan;
    private TextView mMainMenuTvPlay;

    private RadioGroup mRgMainFooter;
    private RadioButton mRbMainFooterHome;
    private RadioButton mRbMainFooterGroup;
    private RadioButton mRbMainFooterFind;
    private RadioButton mRbMainFooterMy;
    private View mIconsView;
    private View mSquareView;
    private View mAdsView;
    private View mCategoriesView;
    private View mRecommendView;
    private LayoutInflater inflater;

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

        mRgMainFooter = (RadioGroup) findViewById(R.id.main_rg_rooter);

        mRbMainFooterHome = (RadioButton) findViewById(R.id.rb_main_footer_home);
        mRbMainFooterGroup = (RadioButton) findViewById(R.id.rb_main_footer_group);
        mRbMainFooterFind = (RadioButton) findViewById(R.id.rb_main_footer_find);
        mRbMainFooterMy = (RadioButton) findViewById(R.id.rb_main_footer_my);
    }

    private void setAdapter() {
        datas = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.main_item_layout,datas);
        addHeaderView();
        mMainListView.setAdapter(adapter);
    }

    private void addHeaderView() {
        inflater = LayoutInflater.from(this);
        mIconsView = inflater.inflate(R.layout.header_list_icons,mMainListView,false);
        mSquareView = inflater.inflate(R.layout.header_list_square,mMainListView,false);
        mAdsView = inflater.inflate(R.layout.header_list_ads,mMainListView,false);
        mCategoriesView = inflater.inflate(R.layout.header_list_categories,mMainListView,false);
        mRecommendView = inflater.inflate(R.layout.header_list_recommend,mMainListView,false);
        initIconsView();
        mMainListView.addHeaderView(mIconsView);
        mMainListView.addHeaderView(mSquareView);
        mMainListView.addHeaderView(mAdsView);
        mMainListView.addHeaderView(mCategoriesView);
        mMainListView.addHeaderView(mRecommendView);
    }


    private void initIconsView() {
        ViewPager viewpager = (ViewPager) mIconsView.findViewById(R.id.vp_header_list_icons);
        PagerAdapter pagerAdapter = new PagerAdapter() {
            int[] resIDs = new int[]{
                    R.layout.icons_list_1,
                    R.layout.icons_list_2,
                    R.layout.icons_list_3
            };
            @Override
            public int getCount() {
                return 60000;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                int layoutId = resIDs[position%resIDs.length];
                View view =inflater.inflate(layoutId,container,false);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(30000);
    }
    //设置监听
    private void setListeners() {
        mMainTvAddress.setOnClickListener(this);
        mMainLlSearch.setOnClickListener(this);
        mMainIvAdd.setOnClickListener(this);

        mMainPrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                mMainPrListView.onRefreshComplete();
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
