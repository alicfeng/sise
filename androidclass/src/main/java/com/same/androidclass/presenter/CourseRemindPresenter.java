package com.same.androidclass.presenter;

import android.content.Context;

import com.same.androidclass.view.view.CourseRemindView;

/**
 * 提醒Presenter
 * Created by alic on 16-5-19.
 */
public class CourseRemindPresenter {
    private Context context;
    private CourseRemindView courseRemindView;

    public CourseRemindPresenter(Context context, CourseRemindView courseRemindView) {
        this.context = context;
        this.courseRemindView = courseRemindView;
    }
    public void doRemindOn(){
        courseRemindView.remindOn();
    }

    public void doRemindOff(){
        courseRemindView.remindOff();
    }
}
