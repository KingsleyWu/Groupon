package com.kingsley.groupon.application;

import android.app.Application;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/20 15:40
 * file change date : on 2017/6/20 15:40
 * version: 1.0
 */

public class MyApplication extends Application {
    public static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }
}
