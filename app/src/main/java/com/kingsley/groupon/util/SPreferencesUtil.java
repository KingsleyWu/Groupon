package com.kingsley.groupon.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kingsley.groupon.config.Constant;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/15 11:34
 * file change date : on 2017/6/15 11:34
 * version: 1.0
 * 对偏好设置文件的操作
 * //个性化设置使用
 * 1.Context 的 getSharePreferences(文件名,模式(决定存储方式,位置))
 * //获取以Preference_activity命名的偏好设置文件
 * 2.Activity 的 getPreferences(模式)
 * //获取preference_包名 偏好设置文件 固定模式:模式 Context_MODE_PRIVATE
 * 3.PreferenceManager的getDefaultSharedPreferences(Context);
 */

public class SPreferencesUtil {
    private SharedPreferences sp;
    //通过构造器重载,以不同的方式来获取偏好设置
    public SPreferencesUtil (Context context,String name){
        sp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
    }
    public SPreferencesUtil (Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public boolean isFrist(){
        return sp.getBoolean(Constant.FIRST,true);
    }

    public void setFirst(boolean flag){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Constant.FIRST,flag);
        editor.apply();
    }
}
