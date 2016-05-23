package com.same.androidclass.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.LinkedList;

/**
 * app工具类
 * Created by alic on 16-5-3.
 */
public class ActivityCollector {
    private static ActivityCollector instance;


    //承载activity的容器
    public static LinkedList<Activity> activities = new LinkedList<Activity>();

    //定义一个变量，来标识是否退出
    public static boolean isExit = false;

    //单例模式中获取唯一的ActivityCollector实例
    public static ActivityCollector getInstance(){
        if (null == instance)
            instance = new ActivityCollector();
        return instance;
    }
    /**
     * 向容器里面添加activity
     *
     * @param activity activity
     */

    public static void addActivity(Activity activity) {
        //单例模式中获取唯一的ActivityCollector实例
        if (null ==instance)
            instance = new ActivityCollector();

        activities.add(activity);

    }

    /**
     * 向容器里面删除activity
     *
     * @param activity activity
     */

    public static void removeActivity(Activity activity) {

        activities.remove(activity);

    }

    /**
     * 遍历finish所有的activity
     */

    public static void finishAll() {

        for (Activity activity : activities) {

            if (!activity.isFinishing()) {

                activity.finish();

            }

        }

    }

    /**
     * 连后台任务都杀死 杀得一干二净
     *
     * 退出应用程序
     *
     * 完全退出App的方法
     */

    public static void AppExit(Context context) {

        try {

            ActivityCollector.finishAll();

            ActivityManager activityMgr = (ActivityManager) context

                    .getSystemService(Context.ACTIVITY_SERVICE);

            activityMgr.killBackgroundProcesses(context.getPackageName());

            System.exit(0);

        } catch (Exception ignored) {

        }

    }


}
