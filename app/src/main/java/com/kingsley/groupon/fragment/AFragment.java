package com.kingsley.groupon.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingsley.groupon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AFragment extends BaseFragment {
    @BindView(R.id.tv_fragment_skip)
    TextView tv;
    public AFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        ButterKnife.bind(this,view);
        skip(tv);
        return view;
    }

}
