package com.same.androidclass.model.listener;

import com.same.androidclass.bean.Exam;

import java.util.List;

/**
 *
 * Created by alic on 16-5-13.
 */
public interface OnGetExamListener {

    void success(List<Exam> exams);
    void failed();
}
