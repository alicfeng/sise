package com.same.androidclass.presenter;

import android.content.Context;

import com.same.androidclass.bean.Grade;
import com.same.androidclass.model.GradeModel;
import com.same.androidclass.model.GradeModelImpl;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.view.view.GradeDetailView;

import java.util.List;

/**
 *
 * Created by alic on 16-5-18.
 */
public class GradeDetailPresenter {
    private Context context;
    private GradeDetailView gradeDetailView;
    private GradeModel gradeModel;

    public GradeDetailPresenter(Context context, GradeDetailView gradeDetailView) {
        this.context = context;
        this.gradeDetailView = gradeDetailView;
        gradeModel = new GradeModelImpl();
    }

    public void doLoadGrades(){
        List<Grade> grades = gradeModel.getGradeFromLocal(
                context,
                DataUtil.readSharedPreference(context, "username", ""),
                gradeDetailView.getTerm(),
                gradeDetailView.getGenre()
        );
        gradeDetailView.getGrades(grades);
    }
}
