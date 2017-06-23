package com.kingsley.groupon.util.http;

import android.util.Log;
import android.widget.ImageView;

import com.kingsley.groupon.R;
import com.kingsley.groupon.application.MyApplication;
import com.kingsley.groupon.entity.CityBean;
import com.kingsley.groupon.entity.TuanBean;
import com.kingsley.groupon.m_interface.RetrofitCallBack;
import com.squareup.picasso.Picasso;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.kingsley.groupon.config.Constant.APPKEY;
import static com.kingsley.groupon.config.Constant.APPSECRET;
import static com.kingsley.groupon.config.Constant.BASEURL;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/19 10:44
 * file change date : on 2017/6/19 10:44
 * version: 1.0
 */

public class HttpUtil {
    /**
     * 符合大众点评服务器要求的地址：
     * http://网址部分?参数1=值1&参数2=值2....
     * <p>
     * http://api.dianping.com/v1/business/find_businesses?appkey=49814079&sign=生成的签名&city=%xx%xx%xx%xx%xx%xx&category=%xx%xx%xx%xx%xx%xx
     * <p>
     * 请求地址中签名的生成：
     * 利用Appkey,AppSecret,以及其他访问参数(例如city,category)
     * 首先将Appkey,AppSecret以及其他访问参数拼接成一个字符串
     * 例:49814079category美食city上海90e3438a41d646848033b6b9d461ed54
     * 将拼接好的字符串进行转码(转码算法为SHA1算法)
     * 转码后就得到了签名
     */

    static String getURL(String url, Map<String, String> params){
        String result ;
        String sign = getSign(params);
        String query = getQuery(APPKEY,sign,params);
        result = url + "?" + query;
        return result;
    }

    static String getSign(Map<String, String> params){
        return getSign(APPKEY,APPSECRET,params);
    }
    static String getSign(String appKey, String appSecret, Map<String, String> params) {
        //创建一个StringBuilder
        StringBuilder sb = new StringBuilder();
        //对集合进行字典排序
        String[] keyArray = params.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        //将AppKey,AppSecret以及其他访问参数拼接成一个字符串
        sb.append(appKey);
        for (String key:keyArray) {
            sb.append(key).append(params.get(key));
        }
        String codes = sb.append(appSecret).toString();
        //将拼接好的字符串进行转码(转码算法为SHA1算法)
        return new String(Hex.encodeHex(DigestUtils.sha1(codes))).toUpperCase();
    }

    private static String getQuery(String appKey, String sign, Map<String, String> params) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("appkey=").append(appKey).append("&sign=").append(sign);
            for (Map.Entry<String,String > entry :params.entrySet()){
                sb.append('&').append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(),"utf-8"));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("使用了不正确的字符集名称");
        }
    }

    public static void testRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(ScalarsConverterFactory.create()).build();
        RetrofitCallBack retrofitCallBack = retrofit.create(RetrofitCallBack.class);
        Map<String, String> params = new HashMap<>();
        params.put("city","北京");
        params.put("category","美食");
        Call<String> response = retrofitCallBack.getResponse(APPKEY, getSign(APPKEY, APPSECRET, params), params);
        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TAG", "onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

    public static void getDailyDeals(String city, com.android.volley.Response.Listener<String> listener, com.android.volley.Response.ErrorListener errorListener){
        VolleyClient.getInstance().getDailyDeals(city,listener,errorListener);
    }

    public static void getDailyDeals(String city, Callback<TuanBean> callBack){
        RetrofitClient.getInstance().getDailyDeals(city,callBack);
    }
    public static void loadImageByVolley(String url, ImageView iv){
        VolleyClient.getInstance().loadImage(url,iv);
    }

    public static void loadImage(String url,ImageView iv){
        Picasso.with(MyApplication.myApplication).load(url).placeholder(R.drawable.ic_home_xianhua).into(iv);
    }
    public static void getCities( com.android.volley.Response.Listener<String> listener,com.android.volley.Response.ErrorListener errorListener){
        VolleyClient.getInstance().getCities(listener,errorListener);
    }
    public static void getCities(Callback<CityBean> callBack){
        RetrofitClient.getInstance().getCitys(callBack);
    }
}
