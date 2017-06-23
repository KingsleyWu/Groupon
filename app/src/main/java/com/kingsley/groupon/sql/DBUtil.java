package com.kingsley.groupon.sql;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.kingsley.groupon.entity.CityBean;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/22 11:05
 * file change date : on 2017/6/22 11:05
 * version: 1.0
 */

public class DBUtil  {
    private Dao<CityBean.CityNameBean, String> dbHelperDao;
    private DBHelper dbHelper;

    public DBUtil(Context context) {
        dbHelper = DBHelper.getInstance(context);
        try {
            dbHelperDao = dbHelper.getDao(CityBean.CityNameBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void insert(CityBean.CityNameBean cityNameBean){
        try {
            dbHelperDao.createIfNotExists(cityNameBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAll(List<CityBean.CityNameBean> cityNameBeanList){
        for (CityBean.CityNameBean cityNameBean:cityNameBeanList) {
            insert(cityNameBean);
        }
    }
    public void insertBatch(final List<CityBean.CityNameBean> cityList){
        //建立连接后，一次性将数据全部写入后，再断开连接
        try {
            dbHelperDao.callBatchTasks(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    for (CityBean.CityNameBean cityNameBean:cityList) {
                        insert(cityNameBean);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CityBean.CityNameBean> query(){
        try {
            return dbHelperDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据库时出现异常");
        }
    }
}
