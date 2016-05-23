package com.same.androidclass.model;

import android.content.Context;

import com.same.androidclass.bean.Credit;

/**
 *
 * Created by alic on 16-5-14.
 */
public interface CreditModel {
    /**
     * 获取本地课程绩点信息
     * @param context context
     * @param studentId 学生号
     * @return Credit
     */
    Credit getCourseMessage(Context context, String studentId);

    /**
     * 保存本地课程绩点信息到本地数据库
     * @param context context
     * @param credit credit
     */
    void saveCourseMessageToLocal(Context context,Credit credit);


    void deleteCourseMessageFromLocal(Context context, String studentId);
}
