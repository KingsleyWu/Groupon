package com.kingsley.appbar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**1,应藏actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        View decorView = getWindow().getDecorView();
        //int flag= View.SYSTEM_UI_FLAG_FULLSCREEN;
        //2,选择模式
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        //3,设置颜色
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //关联
        decorView.setSystemUiVisibility(flag);*/
        /**ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        View decorView=getWindow().getDecorView();
        //int flag=View.SYSTEM_UI_FLAG_FULLSCREEN;
        int flag=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        decorView.setSystemUiVisibility(flag);*/

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ActionBar actionBar=getSupportActionBar();
         actionBar.hide();
         View decorView=getWindow().getDecorView();
         //int flag=View.SYSTEM_UI_FLAG_FULLSCREEN;
         int flag=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
         View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
         View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                 View.SYSTEM_UI_FLAG_FULLSCREEN|
                 View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

         getWindow().setStatusBarColor(Color.TRANSPARENT);
         getWindow().setNavigationBarColor(Color.TRANSPARENT);
         decorView.setSystemUiVisibility(flag);

    }

}
