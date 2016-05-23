package com.same.androidclass.model;

import android.content.Context;

import com.same.androidclass.bean.Exam;
import com.same.androidclass.model.listener.OnGetExamListener;

import java.util.List;

/**
 * 考试接口
 * Created by alic on 16-5-13.
 */
public interface ExamModel {
    /**
     * 模拟从服务器获取考试表
     */
    void getExamFromServer(String username,String password,OnGetExamListener listener);

    /**
     * 模拟本地获取考试
     * @return 课程list
     */
    List<Exam> getExamFromLocal(Context context,String studentId);

    /**
     * 保存考试到本地数据库
     * @param context context
     * @param exams exams
     */
    void saveExamToLocal(Context context,List<Exam> exams);

    /**
     * 删除本地考试数据
     * @param context content
     */
    boolean deleteExamFromLocal(Context context,String studentId);
}
