package com.same.androidclass.model;

import android.content.Context;

import com.same.androidclass.bean.Grade;
import com.same.androidclass.model.listener.OnGetGradeListener;

import java.util.List;

/**
 * 成绩接口
 * Created by alic on 16-5-13.
 */
public interface GradeModel {
    /**
     * 模拟从服务器获取成绩表
     */
    void getGradeAndCourseMessageFromServer(String username, String password, OnGetGradeListener listener);

    /**
     * 模拟本地获取成绩
     * @return 课程list
     */
    List<Grade> getGradeFromLocal(Context context, String studentId, String term,String genre);


    /**
     * 保存成绩到本地数据库
     * @param context context
     * @param exams exams
     */
    void saveGradeToLocal(Context context, List<Grade> exams);

    /**
     * 删除本地成绩数据
     * @param context content
     */
    boolean deleteGradeFromLocal(Context context, String studentId);
}
