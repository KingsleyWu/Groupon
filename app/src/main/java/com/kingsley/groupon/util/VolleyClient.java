package com.kingsley.groupon.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kingsley.groupon.R;
import com.kingsley.groupon.application.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/20 09:43
 * file change date : on 2017/6/20 09:43
 * version: 1.0
 */

public class VolleyClient {
    private RequestQueue queue;
    private ImageLoader imageLoader;
    //单例
    private static VolleyClient INSTANCE;

    static VolleyClient getInstance() {
        if (INSTANCE == null) {
            synchronized (VolleyClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VolleyClient();
                }
            }
        }
        return INSTANCE;
    }

    private VolleyClient() {
        queue = Volley.newRequestQueue(MyApplication.myApplication);
        imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            LruCache<String,Bitmap> cache = new LruCache<>((int) (Runtime.getRuntime().maxMemory()/8));
            @Override
            public Bitmap getBitmap(String s) {
                return cache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                cache.put(s,bitmap);
            }
        });
    }

    //利用Volley获取城市今日新增的团购信息
    void getDailyDeals(String city, final Response.Listener<String> listener, final Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        params.put("city", city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        params.put("date", date);
        String url = HttpUtil.getURL("http://api.dianping.com/v1/deal/get_daily_new_id_list", params);
        final StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("id_list");
                    int size = jsonArray.length();
                    if (size > 40) {
                        size = 40;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < size; i++) {
                        String id = jsonArray.getString(i);
                        if (i < size-1)
                            sb.append(id).append(",");
                        else
                            sb.append(id);
                    }
                    String idList = sb.toString();
                    Log.i("TAG", "onResponse: idList="+idList);
                    //获取团购详情

                    Map<String,String> params = new HashMap<>();
                    params.put("deal_ids",idList);
                    String url = HttpUtil.getURL("http://api.dianping.com/v1/deal/get_batch_deals_by_id",params);
                    StringRequest request = new StringRequest(url,listener,errorListener);
                    queue.add(request);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }

    public void loadImage(String url, ImageView iv){
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(iv, R.drawable.ic_home_xianhua, R.drawable.ic_home_xianhua);
        imageLoader.get(url,imageListener);
    }
}
