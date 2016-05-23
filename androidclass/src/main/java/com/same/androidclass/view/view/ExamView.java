package com.same.androidclass.view.view;

import com.same.androidclass.bean.Exam;
import com.same.androidclass.bean.Student;

import java.util.List;

/**
 * 考试视图接口
 * Created by alic on 16-5-13.
 */
public interface ExamView  {
    /**
     * 显示考试信息
     */
    void displayExam(List<Exam> exams);

    void displayStudentMessage(Student student);

    /**
     * 隐藏刷新
     */
    void hideRefresh();

    /**
     * 显示成功
     */
    void showSuccess();
    /**
     * 显示失败
     */
    void showError();

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();
}
