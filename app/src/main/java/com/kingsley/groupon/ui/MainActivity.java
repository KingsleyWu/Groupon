package com.kingsley.groupon.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kingsley.groupon.R;
import com.kingsley.groupon.adapter.MyListViewAdapter;
import com.kingsley.groupon.entity.TuanBean;
import com.kingsley.groupon.util.http.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView mMainTvAddress;
    private LinearLayout mMainLlSearch;
    private ImageView mMainIvAdd;

    private FrameLayout mMainFrameLayout;
    private View mMainMenuLayout;
    private PullToRefreshListView mMainPrListView;
    private ListView mMainListView;
    private List<TuanBean.Deal> datas;
    private MyListViewAdapter adapter;
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

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
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
        adapter = new MyListViewAdapter(this, datas);
        addHeaderView();
        mMainListView.setAdapter(adapter);
    }

    private void addHeaderView() {
        inflater = LayoutInflater.from(this);
        mIconsView = inflater.inflate(R.layout.header_list_icons, mMainListView, false);
        mSquareView = inflater.inflate(R.layout.header_list_square, mMainListView, false);
        mAdsView = inflater.inflate(R.layout.header_list_ads, mMainListView, false);
        mCategoriesView = inflater.inflate(R.layout.header_list_categories, mMainListView, false);
        mRecommendView = inflater.inflate(R.layout.header_list_recommend, mMainListView, false);
        initIconsView();
        mMainListView.addHeaderView(mIconsView);
        mMainListView.addHeaderView(mSquareView);
        mMainListView.addHeaderView(mAdsView);
        mMainListView.addHeaderView(mCategoriesView);
        mMainListView.addHeaderView(mRecommendView);
    }

    private void initIconsView() {
        final ViewPager viewpager = (ViewPager) mIconsView.findViewById(R.id.vp_header_list_icons);
        final ImageView mIvIconsIndicator1 = (ImageView) mIconsView.findViewById(R.id.iv_icons_indicator1);
        final ImageView mIvIconsIndicator2 = (ImageView) mIconsView.findViewById(R.id.iv_icons_indicator2);
        final ImageView mIvIconsIndicator3 = (ImageView) mIconsView.findViewById(R.id.iv_icons_indicator3);
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
                int layoutId = resIDs[position % resIDs.length];
                View view = inflater.inflate(layoutId, viewpager, false);
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
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIvIconsIndicator1.setImageResource(R.drawable.banner_dot);
                mIvIconsIndicator2.setImageResource(R.drawable.banner_dot);
                mIvIconsIndicator3.setImageResource(R.drawable.banner_dot);
                switch (position % 3) {
                    case 0:
                        mIvIconsIndicator1.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                    case 1:
                        mIvIconsIndicator2.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                    case 2:
                        mIvIconsIndicator3.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //设置监听
    private void setListeners() {
        mMainTvAddress.setOnClickListener(this);
        mMainLlSearch.setOnClickListener(this);
        mMainIvAdd.setOnClickListener(this);

        mMainPrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh();
            }
        });

        mMainPrListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //Log.i(TAG, "onScroll: 第一个参数: "+view+"第二个参数: "+firstVisibleItem+"第三个参数: "+visibleItemCount+"第四个参数: "+totalItemCount);
                //当显示的第一个itemView被往外(上)移动时改变上面两个mMainIvAdd与mMainTvAddress的可见性
                if (firstVisibleItem == 0){
                    mMainIvAdd.setVisibility(View.VISIBLE);
                    mMainTvAddress.setVisibility(View.VISIBLE);
                }else {
                    mMainIvAdd.setVisibility(View.GONE);
                    mMainTvAddress.setVisibility(View.GONE);
                }
            }
        });
        mRgMainFooter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
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
        switch (v.getId()) {
            case R.id.main_tv_address:
                Intent intent = new Intent(this,CityActivity.class);
                startActivityForResult(intent,101);
                break;
            case R.id.main_ll_search:
                break;
            case R.id.main_iv_add:
                if (mMainMenuLayout.getVisibility() == View.VISIBLE)
                mMainMenuLayout.setVisibility(View.INVISIBLE);
                else mMainMenuLayout.setVisibility(View.VISIBLE);
                break;
        }
    }


    public void refresh(){
        /*HttpUtil.getDailyDeals(mMainTvAddress.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s != null){
                    Gson gson = new Gson();
                    TuanBean tuanBean = gson.fromJson(s,TuanBean.class);
                    List<TuanBean.Deal> deals = tuanBean.getDeals();
                    adapter.addAll(deals,true);
                }else {
                    Toast.makeText(MainActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                }
                mMainPrListView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
                mMainPrListView.onRefreshComplete();
            }
        });*/
        HttpUtil.getDailyDeals(mMainTvAddress.getText().toString(), new Callback<TuanBean>() {
            @Override
            public void onResponse(@NonNull Call<TuanBean> call, @NonNull Response<TuanBean> response) {
                if (response != null){
                TuanBean tuanBean = response.body();
                //Log.i(TAG, "onResponse: tuanBean="+tuanBean);
                    assert tuanBean != null;
                    List<TuanBean.Deal> deals = tuanBean.getDeals();
                   // Log.i(TAG, "onResponse: deals="+deals);
                    adapter.addAll(deals,true);
                }else {
                    Toast.makeText(MainActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                }
                mMainPrListView.onRefreshComplete();
            }

            @Override
            public void onFailure(@NonNull Call<TuanBean> call, @NonNull Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                mMainPrListView.onRefreshComplete();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: "+data.getStringExtra("city"));
        if (resultCode == RESULT_OK && requestCode == 101){
            mMainTvAddress.setText(data.getStringExtra("city"));
        }
    }
}
