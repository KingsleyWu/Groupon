package com.kingsley.groupon.util.http;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kingsley.groupon.config.Constant;
import com.kingsley.groupon.entity.CityBean;
import com.kingsley.groupon.entity.TuanBean;
import com.kingsley.groupon.util.TimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/20 19:50
 * file change date : on 2017/6/20 19:50
 * version: 1.0
 * 1.
 * 使用Retrofit需要创建Retrofit对象
 * retrofit = new Retrofit.Builder().client(okhttpClient)
 * .baseUrl(Constant.BASEURL)
 * .addConverterFactory(ScalarsConverterFactory.create())
 * .addConverterFactory(GsonConverterFactory.create()).build();
 * 创建对象时两个方法是必须调用的
 * 连接网络服务器时不变的URL地址
 * baseUrl(baseUrl)
 * 把返回的对象经过ScalarsConverterFactory工厂转变为String字符串
 * addConverterFactory(ScalarsConverterFactory.create())
 * 如需把字符串转换成对象则可以在此工厂后面添加GsonConverterFactory或其他工厂
 * addConverterFactory(GsonConverterFactory.create())
 * 2.需创建一个接口来实现方法的调用
 */

class RetrofitClient {
    private static final String TAG = "RetrofitClient";
    private RetrofitService retrofitService;
    private static RetrofitClient INSTANCE;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    static RetrofitClient getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitClient();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 创建okHttpClient,retrofit,retrofitService
     */
    private RetrofitClient() {
        okHttpClient = new OkHttpClient.Builder().addInterceptor(new MyInterceptor()).build();
        retrofit = new Retrofit.Builder().baseUrl(Constant.BASEURL).client(okHttpClient).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    void getDailyDeals(String city, final Callback<TuanBean> callBack){
        //1.创建参数列表,并添加数据
        Map<String,String> params = new HashMap<>();
        params.put("city",city);
        String date = TimeUtil.getCurrentTime("yyyy-MM-dd");
        params.put("date",date);
        //2.通过retrofitService的getDailyIds回调返回Call<String> 的参数获取idList
        Call<String> idsCall = retrofitService.getDailyIds(params);
        //Log.i("TAG", "idsCall="+idsCall);
        idsCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JSONArray id_list = jsonObject.getJSONArray("id_list");
                    //Log.i(TAG, "onResponse: id_list="+id_list);
                    int size = id_list.length();
                    if (size > 40){
                        size = 40;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < size; i++) {
                        sb.append(id_list.get(i)).append(",");
                    }
                    //Log.i(TAG, "onResponse: sb="+sb.toString());
                    if (sb.length() > 0){
                        String idList = sb.substring(0, sb.length() - 1);
                        //3.通过retrofitService的getDailyDeals回调返回Call<tuanBean> 的参数获取团购信息
                        Map<String,String> params = new HashMap<>();
                        params.put("deal_ids",idList);
                        Call<TuanBean> dealCall = retrofitService.getDailyDeals(params);
                        dealCall.enqueue(callBack);
                    }else {
                        callBack.onResponse(null,null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {
                Log.i(TAG, "onFailure: call="+call);
                Log.i(TAG, "onFailure: throwable="+throwable);
            }
        });

    }

    void getCitys(Callback<CityBean> callBack){
        Call<CityBean> cityBeanCall = retrofitService.getCitys();
        cityBeanCall.enqueue(callBack);
    }

}
