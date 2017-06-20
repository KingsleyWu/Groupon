package com.kingsley.groupon.m_interface;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/19 15:46
 * file change date : on 2017/6/19 15:46
 * version: 1.0
 */

public interface RetrofitCallBack {
    @GET("business/find_businesses")
    Call<String> getResponse(@Query("appkey") String appKey,@Query("sign") String sign, @QueryMap Map<String,String> params);

}
