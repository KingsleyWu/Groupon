package com.kingsley.groupon.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.kingsley.groupon.R;
import com.kingsley.groupon.util.SPreferencesUtil;

public class SplashActivity extends AppCompatActivity {
    SPreferencesUtil spUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        spUtil = new SPreferencesUtil(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //根据
                Intent intent;
                if (spUtil.isFrist()){
                    intent = new Intent(SplashActivity.this,GuideActivity.class);
                    spUtil.setFirst(false);
                }else {
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },1500);
    }
}
