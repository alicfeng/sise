package com.same.androidclass.view.view;

import com.same.androidclass.bean.Attendance;

import java.util.List;

/**
 * 考勤视图接口
 * Created by alic on 16-5-9.
 */
public interface AttendanceView  {
    /**
     * 获取学期
     * @return 返回 20152 格式的学期
     */
    String getYearSemester();

    /**
     * 获取考勤集合
     * @param attendances 考勤集合
     */
    void displayAttendances(List<Attendance> attendances);

    /**
     * 获取成功，没有考勤数据
     * 提示没有考勤信息
     */
    void displayNothing();

    /**
     * 获取失败
     * 提示错误
     */
    void alertError();
    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

}
