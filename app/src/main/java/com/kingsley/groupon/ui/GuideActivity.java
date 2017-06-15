package com.kingsley.groupon.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.kingsley.groupon.R;
import com.kingsley.groupon.adapter.ViewPagerAdapter;
import com.viewpagerindicator.LinePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kingsley.groupon.R.id.indicator;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.guide_ViewPager)
    ViewPager mGuideViewPager;
    @BindView(indicator)
    LinePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mGuideViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mGuideViewPager);
        //当前运行程序所使用的设备的屏幕密度
        final float density = getResources().getDisplayMetrics().density;
        mIndicator.setSelectedColor(0x88FF0000);
        mIndicator.setUnselectedColor(0xFF888888);
        //在当前设备上所对应的像素值(px)
        mIndicator.setStrokeWidth(4 * density);
        mIndicator.setLineWidth(30 * density);
    }
}
