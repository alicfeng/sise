package com.same.androidclass.model.listener;

import com.same.androidclass.bean.Attendance;

import java.util.List;

/**
 * 获取考勤监听接口
 * Created by alic on 16-5-9.
 */
public interface OnGetAttendanceListener {

    /**
     * 成功获取信息
     */
    void success(List<Attendance> attendances);

    /**
     * 成功连接 没有获取信息
     */
    void successNothing();

    /***
     * 获取失败
     */
    void failed();
}
