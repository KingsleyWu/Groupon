package com.kingsley.groupon.util;

import java.text.SimpleDateFormat;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/20 21:02
 * file change date : on 2017/6/20 21:02
 * version: 1.0
 */

public class TimeUtil {
    public static String getCurrentTime(String fromat){
        return new SimpleDateFormat(fromat).format(System.currentTimeMillis());
    }
}
