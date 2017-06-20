package com.kingsley.groupon.util;

import com.kingsley.groupon.entity.TuanBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/20 20:06
 * file change date : on 2017/6/20 20:06
 * version: 1.0
 *
 * 使用OkHttp的Interceptor添加下面的两个参数
 * String appkey
 * String sign
 */

interface RetrofitService {
    /**
     * 用于获取团购idList
     * @param params 参数
     * @return idList
     */
    @GET("deal/get_daily_new_id_list")
    Call<String> getDailyIds(@QueryMap Map<String,String> params);

    /**
     * 用于获取团购信息
     * @param params 参数
     * @return 团购信息
     */
    @GET("deal/get_batch_deals_by_id")
    Call<TuanBean> getDailyDeals(@QueryMap Map<String,String> params);
}
