package com.kingsley.groupon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kingsley.groupon.fragment.AFragment;
import com.kingsley.groupon.fragment.BFragment;
import com.kingsley.groupon.fragment.CFragment;
import com.kingsley.groupon.fragment.DFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/15 14:37
 * file change date : on 2017/6/15 14:37
 * version: 1.0
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new AFragment());
        fragments.add(new BFragment());
        fragments.add(new CFragment());
        fragments.add(new DFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
