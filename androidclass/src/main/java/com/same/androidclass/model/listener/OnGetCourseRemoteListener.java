package com.same.androidclass.model.listener;

import android.database.Cursor;

import com.same.androidclass.bean.Course;

import java.util.List;

/**
 *获取课程监听接口
 * Created by alic on 16-5-7.
 */
public interface OnGetCourseRemoteListener {
    /**
     * 成功获取信息
     */
    void success(List<Course> courses);

    /***
     * 获取失败
     */
    void failed();
}
