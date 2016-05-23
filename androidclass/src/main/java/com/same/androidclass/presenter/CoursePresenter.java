package com.same.androidclass.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.same.androidclass.bean.Course;
import com.same.androidclass.model.CourseModel;
import com.same.androidclass.model.CourseModelImpl;
import com.same.androidclass.model.listener.OnGetCourseRemoteListener;
import com.same.androidclass.util.AppUtil;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.view.view.CourseView;

import java.util.List;

/**
 *
 * Created by alic on 16-5-7.
 */
public class CoursePresenter {
    private CourseView courseView;
    private CourseModel courseModel;
    private Context context;
    private Handler handler;
    private Handler myHandler;
    private List<Course> courseList;//临时数据

    public CoursePresenter(CourseView courseView, Context context) {
        this.courseView = courseView;
        this.context = context;
        courseModel = new CourseModelImpl();
        handler = new Handler(Looper.getMainLooper());
        myHandler = new Handler(Looper.myLooper());
    }

    /**
     * 加载课程数据
     */
    public void doLoadCourses(){

        final List<Course> coursesFromLocal = courseModel.getCoursesFromLocal(
                context,
                DataUtil.readSharedPreference(context, "username", ""),
                courseView.getCourseTerm()
        );
        if (coursesFromLocal!=null){
            //debug
            AppUtil.LOG(this.getClass().getName(), "本地数据有数据哇");
            //临时数据
            courseList = coursesFromLocal;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    courseView.displayCourses(coursesFromLocal);
                }
            });
        }else {
            //debug
            AppUtil.LOG(this.getClass().getName(), "本地数据为空 我要上服务器啦");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    courseView.showLoading();
                }
            });

            courseModel.getCoursesFromServer(
                    DataUtil.readSharedPreference(context, "username", ""),
                    DataUtil.readSharedPreference(context, "password", ""),
                    courseView.getSchoolYear(),
                    courseView.getSemester(), new OnGetCourseRemoteListener() {

                        @Override
                        public void success(final List<Course> courses) {
                            //临时数据
                            courseList = courses;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    courseView.displayCourses(courses);
                                    courseView.hideLoading();
                                }
                            });
                            //异步将数据导入本地数据库
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    courseModel.saveCoursesToLocal(context, courses, DataUtil.readSharedPreference(context, "username", ""));
                                }
                            }).start();
                        }

                        @Override
                        public void failed() {

                        }
                    }
            );
            courseModel.getCurrentWeek(context,
                    DataUtil.readSharedPreference(context, "username", ""),
                    DataUtil.readSharedPreference(context, "password", "")
            );
        }
    }

    /**
     * 控制周数按钮事件
     */
    public void reloadCourse(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                doLoadCourses();
//                courseView.displayCourses(courseList);
            }
        });

    }
}
