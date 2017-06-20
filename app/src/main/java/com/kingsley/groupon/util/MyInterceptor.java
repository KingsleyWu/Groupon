package com.kingsley.groupon.util;

import android.util.Log;

import com.kingsley.groupon.config.Constant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/20 20:17
 * file change date : on 2017/6/20 20:17
 * version: 1.0
 */

public class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //获得请求对象
        Request request = chain.request();
        //获得请求路径
        HttpUrl url = request.url();
        //取出原有请求路径中的参数
        HashMap<String,String> params = new HashMap<>();
        //获得请求参数的名称集合
        Set<String> set = url.queryParameterNames();
        //把参数取出到集合中
        for (String key:set) {
            params.put(key,url.queryParameter(key));
        }
        //获取签名sign
        String sign = HttpUtil.getSign(Constant.APPKEY,Constant.APPSECRET,params);
        //拼接appkey及签名到url上
        Log.i("TAG", "原有请求路径"+url.toString());
        ////追加appkey及sign
        String sb = url.toString() + "&" + "appkey=" + Constant.APPKEY +
                "&" + "sign=" + sign;
        Log.i("TAG", "追加appkey及sign请求路径"+sb);
        //把拼接好的url给到request
        request = new Request.Builder().url(sb).build();
        //返回拼接好的request
        return chain.proceed(request);
    }
}
