package com.same.androidclass.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.same.androidclass.common.AppConfig;
import com.same.androidclass.model.CourseModel;
import com.same.androidclass.model.CourseModelImpl;
import com.same.androidclass.service.ExtraServiceDo;
import com.same.androidclass.util.AppUtil;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.util.DateUtil;
import com.same.androidclass.util.NetWorkUtils;

import java.util.Date;

/**
 *
 * Created by alic on 16-5-20.
 */
public class ExtraServicePresenter {
    private Context context;
    private CourseModel courseModel;
    private ExtraServiceDo extraServiceDo;


    public ExtraServicePresenter(Context context, ExtraServiceDo extraServiceDo) {
        this.context = context;
        this.extraServiceDo = extraServiceDo;
        courseModel = new CourseModelImpl();
    }

    //获取行政周数
    public void doGetCurrentWeek() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.SHAREPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("isFirst", true)) {
            try {
                Thread.sleep(60000);
                if (NetWorkUtils.isNetworkConnected(context)) {
                    courseModel.getCurrentWeek(
                            context,
                            DataUtil.readSharedPreference(context, "username", ""),
                            DataUtil.readSharedPreference(context, "password", "")
                    );
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //debug
            AppUtil.LOG(this.getClass().getName(), "current-Okay");
        }
    }

    //获得明天的课程节数
    public String doGetCurrentWeekCourseCount() {
        int courseCountOfDay = courseModel.getCourseCountOfDay(
                context,
                DataUtil.readSharedPreference(context, "username", ""),
                (Integer.parseInt(DateUtil.formatDate(new Date(), DateUtil.yyyy)) - 1) + "-"
                        + DateUtil.formatDate(new Date(), DateUtil.yyyy)
                        + "学年 第二学期",
                String.valueOf(DateUtil.getWeekOfDate(new Date())),
                DataUtil.readSharedPreference(context, "currentWeek", "9"));
        return String.valueOf(courseCountOfDay);
    }

    //显示Notification
    public void doShowNotification() {
        extraServiceDo.showNotification();
    }
}
