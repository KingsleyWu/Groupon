package com.kingsley.groupon.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.kingsley.groupon.ui.MainActivity;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/15 15:32
 * file change date : on 2017/6/15 15:32
 * version: 1.0
 */

public abstract class BaseFragment extends Fragment {
    public void skip(View skip){
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
    //abstract TextView layoutId();
}
