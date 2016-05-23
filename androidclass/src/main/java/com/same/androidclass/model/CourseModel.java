package com.same.androidclass.model;

import android.content.Context;

import com.same.androidclass.bean.Course;
import com.same.androidclass.model.listener.OnGetCourseRemoteListener;

import java.util.List;

/**
 * 课程接口
 * Created by alic on 16-5-7.
 */
public interface CourseModel {
    /**
     * 模拟从服务器获取课程表
     */
    void getCoursesFromServer(String username,String password,String schoolYear,String semester ,OnGetCourseRemoteListener listener);

    /**
     * 模拟本地获取课程表
     * @return 课程list
     */
    List<Course> getCoursesFromLocal(Context context,String studentId,String courseTerm);

    /**
     * 保存课程到本地数据库
     * @param context context
     * @param courses courses
     */
    void saveCoursesToLocal(Context context,List<Course> courses,String studentId);

    /**
     * 获得每天的课程节数
     * @return int
     */
    int getCourseCountOfDay(Context context, String studentId,String courseTerm,String courseTime,String week);

    /**
     * 获取当前行政周数
     * @param context context
     * @param username username
     * @param password password
     */
    void getCurrentWeek(Context context,String username,String password);
}
