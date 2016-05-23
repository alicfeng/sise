package com.same.androidclass.view.view;

import com.same.androidclass.bean.Course;

import java.util.List;

/**
 * 课程视图接口
 * Created by alic on 16-5-7.
 */
public interface CourseView  {
    void displayCourses(List<Course> courses);
    String getSchoolYear();
    String getSemester();
    String getCourseTerm();
    void showLoading();
    void hideLoading();
}
