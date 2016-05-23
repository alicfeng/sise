package com.same.androidclass.model;

import android.content.Context;

import com.same.androidclass.bean.Attendance;
import com.same.androidclass.bean.Course;
import com.same.androidclass.model.listener.OnGetAttendanceListener;
import com.same.androidclass.model.listener.OnGetCourseRemoteListener;

import java.util.List;

/**
 *
 * Created by alic on 16-5-9.
 */
public interface AttendanceModel {
    /**
     * 模拟从服务器获取考勤
     */
    void getAttendanceFromServer(String username, String password,String yearSemester,OnGetAttendanceListener listener);

    /**
     * 模拟本地获取考勤
     * @return 考勤list
     */
    List<Attendance> getAttendanceFromLocal(Context context,String studentId,String term);


    /**
     * 保存考勤到本地数据库
     * @param context  context
     * @param attendances attendances
     * @param studentId studentId
     */
    void saveAttendanceToLocal(Context context,List<Attendance> attendances, String studentId);
}
