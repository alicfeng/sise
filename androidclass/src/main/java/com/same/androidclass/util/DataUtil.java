package com.same.androidclass.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.same.androidclass.common.AppConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 数据工具类
 * Created by alic on 16-5-5.
 */
public class DataUtil {
    /**
     * 保存haredPreference
     * @param key 键名
     * @param value 键值
     */
    public static void saveSharedPreference(Context context,String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.SHAREPREFERENCES_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    /**
     * 读取haredPreference
     * @param context context
     * @param key 键名
     * @param defaultValue 键值
     * @return String
     */
    public static String readSharedPreference(Context context,String key,String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.SHAREPREFERENCES_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,defaultValue);
    }


    /**
     * 判断字符串数组是否包含某个字符
     * @param array 字符串数组
     * @param reg 某个字符
     * @return true包含 false不包含
     */
    public static boolean isContainer(String[] array,String reg){
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(reg))
                return true;
        }
        return false;
    }

    /**
     * 截取字符串
     * @param content 检验的字符串
     * @param length  检验最长长度
     * @return 截取String
     */
    public static String subString(String content,int length,String extra){
        if (content.length()>=length)
            return content.substring(0,length-1)+extra;
        else return content;
    }


    /**
     *
     * @param content
     * @param reg
     * @return
     */
    public static String getReg(String content,String reg){
        Matcher matcher = Pattern.compile(reg).matcher(content);
        while (matcher.find()){
            return matcher.group();
        }
        return null;
    }

    /**
     * 隐藏字符串的某段字符 用特定字符替代
     * @param content
     * @param start
     * @param end
     * @return
     */
    public static String hiddenCode(String content,int start,int end,String code){
        String replaceCode = "";
        for (int i = 0; i < end - start; i++) {
            replaceCode+=code;
        }
        return content.substring(0, start) + replaceCode + content.substring(end, content.length());
    }
}
