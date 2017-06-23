package com.kingsley.groupon.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kingsley.groupon.entity.CityBean;

import java.sql.SQLException;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/22 11:00
 * file change date : on 2017/6/22 11:00
 * version: 1.0
 */

class DBHelper extends OrmLiteSqliteOpenHelper {
    private static DBHelper INSTANCE;
    static DBHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (DBHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new DBHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    private DBHelper(Context context) {
        super(context, "city.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, CityBean.CityNameBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, CityBean.CityNameBean.class,true);
            onCreate(sqLiteDatabase,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
