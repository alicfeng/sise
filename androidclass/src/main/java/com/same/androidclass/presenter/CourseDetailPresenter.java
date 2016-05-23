package com.same.androidclass.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.same.androidclass.view.view.CourseDetailView;

/**
 * 显示课程具体信息的presenter
 * Created by alic on 16-5-12.
 */
public class CourseDetailPresenter {
    private CourseDetailView courseDetailView;
    private Context context;
    private Handler handler;

    //construct
    public CourseDetailPresenter(CourseDetailView courseDetailView,Context context) {
        this.courseDetailView = courseDetailView;
        this.context = context;
        handler = new Handler(Looper.getMainLooper());
    }

    //do-数据显示
    public void doDisplayCourseDetail(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                courseDetailView.displayCourse(courseDetailView.getCourseData());
            }
        });
    }
}
