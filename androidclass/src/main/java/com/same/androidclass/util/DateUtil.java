package com.same.androidclass.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理类
 * Created by alic on 16-5-7.
 */
public class DateUtil {
    /**
     * 日期类型 *
     */
    public static final String yyyy = "yyyy";
    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String HHmmss = "HH:mm:ss";
    public static final String hhmmss = "HH:mm:ss";
    public static final String LOCALE_DATE_FORMAT = "yyyy年M月d日 HH:mm:ss";
    public static final String DB_DATA_FORMAT = "yyyy-MM-DD HH:mm:ss";
    public static final String NEWS_ITEM_DATE_FORMAT = "hh:mm M月d日 yyyy";

    /**
     * 将Date类型转换为日期字符串
     *
     * @param date Date对象
     * @param type 需要的日期格式
     * @return 按照需求格式的日期字符串
     */
    public static String formatDate(Date date, String type) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(type);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取日期的星期
     *
     * @param data 日期
     * @param type 格式
     * @return 星期
     */
    public static String getWeek(String data, String type) {

        try {
            SimpleDateFormat dataFormat = new SimpleDateFormat(type);
            Date date = dataFormat.parse(data);
            dataFormat = new SimpleDateFormat("E");
            return dataFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取星期
     * @param date date
     * @return 1234567
     */
    public static int getWeekOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //注意返回值 -1 sun 1  mon 2 下面正好适合需求 周日提醒周一
        int w = cal.get(Calendar.DAY_OF_WEEK);
        if (w < 0)
            w = 0;
        return w;
    }
}
