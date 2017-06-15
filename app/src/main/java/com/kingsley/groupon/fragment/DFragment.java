package com.kingsley.groupon.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kingsley.groupon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DFragment extends BaseFragment {
    @BindView(R.id.btn_fragment_skip)
    Button btn;

    public DFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, container, false);
        ButterKnife.bind(this,view);
        skip(btn);
        return view;
    }

}
