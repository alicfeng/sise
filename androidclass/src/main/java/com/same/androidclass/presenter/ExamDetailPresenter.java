package com.same.androidclass.presenter;

import android.content.Context;

import com.same.androidclass.view.view.ExamDetailView;

/**
 *
 * Created by alic on 16-5-14.
 */
public class ExamDetailPresenter {
    private ExamDetailView examDetailView;
    private Context context;

    public ExamDetailPresenter(ExamDetailView examDetailView, Context context) {
        this.examDetailView = examDetailView;
        this.context = context;
    }
    //控制获取考试参数
    public void doGetExam(){
        examDetailView.getIntentExam();
    }
}
