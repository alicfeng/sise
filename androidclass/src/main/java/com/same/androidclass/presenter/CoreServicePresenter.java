package com.same.androidclass.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.same.androidclass.common.AppConfig;
import com.same.androidclass.model.CourseModel;
import com.same.androidclass.model.CourseModelImpl;
import com.same.androidclass.service.CoreServiceDo;
import com.same.androidclass.service.ExtraServiceDo;
import com.same.androidclass.util.AppUtil;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.util.NetWorkUtils;

/**
 *
 * Created by alic on 16-5-22.
 */
public class CoreServicePresenter {
    private Context context;
    private CourseModel courseModel;
    private CoreServiceDo coreServiceDo;

    public CoreServicePresenter(Context context, CoreServiceDo coreServiceDo) {
        this.context = context;
        this.coreServiceDo = coreServiceDo;
        this.courseModel = new CourseModelImpl();
    }

    //获取行政周数
    public void getCurrentWeek() {
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
}
