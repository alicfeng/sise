package com.same.androidclass.view.view;

import com.same.androidclass.bean.Course;

/**
 * 课程详细界面接口
 * Created by alic on 16-5-12.
 */
public interface CourseDetailView {
    /**
     * 获取课程信息
     * @return 课程信息
     */
    Course getCourseData();


    /**
     * 显示课程信息
     * @param course 课程信息
     */
    void displayCourse(Course course);
}
