package com.home.rw.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cty on 2016/12/22.
 */

public class DateUtils {


    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
        return format.format(date);
    }


    public static Date getDate(String sTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
        try {
            return format.parse(sTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
