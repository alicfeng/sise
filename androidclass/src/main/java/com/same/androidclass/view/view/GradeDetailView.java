package com.same.androidclass.view.view;

import com.same.androidclass.bean.Grade;

import java.util.List;

/**
 * 成绩详细视图接口
 * Created by alic on 16-5-18.
 */
public interface GradeDetailView {
    /**
     * 获取成绩list
     * @param grades 成绩结果集
     */
    void getGrades(List<Grade> grades);

    /**
     * 获得学期
     * @return ex 第一学期
     */
    String getTerm();

    /***
     *  获得选修或必修
     * @return 0 1
     */
    String getGenre();
}
