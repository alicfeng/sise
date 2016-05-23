package com.same.androidclass.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.same.androidclass.R;
import com.same.androidclass.presenter.ExtraServicePresenter;
import com.same.androidclass.util.NetWorkUtils;
import com.same.androidclass.view.activity.MainActivity;

/**
 * 额外服务ExtraService
 * Created by alic on 16-5-19.
 */
public class ExtraService extends Service implements ExtraServiceDo {
    private ExtraServicePresenter presenter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //该方法只执行一次
    @Override
    public void onCreate() {
        super.onCreate();
//        Toast.makeText(this, "ExtraService已经启动", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initService();
//        System.out.println("嘿 我在执行 定时呢");
        return super.onStartCommand(intent, flags, startId);
    }

    public void initService() {
        presenter = new ExtraServicePresenter(this, this);
        //需要定时启动 -----here 时间在user xml 通SP获取
        presenter.doShowNotification();
        //没什么
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (NetWorkUtils.isNetworkConnected(ExtraService.this))
                    presenter.doGetCurrentWeek();
            }
        }).start();
    }


    //定时提醒
    @Override
    public void showNotification() {
        //定义一个PendingIntent点击Notification后启动一个Activity
        Intent it = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, it, 0);

        String tip = "嘿嘿~ 明天还有" + presenter.doGetCurrentWeekCourseCount() + "节课要上( ⊙o⊙ )哇";
        Notification.Builder builder = new Notification.Builder(this);
        //没课就不提示啦
        if (!presenter.doGetCurrentWeekCourseCount().equals("0")) {
        builder.setContentTitle("我是校园助手 Alic派过来的( ⊙o⊙ )哇")
                .setContentText("")
                .setSubText(tip)                    //内容下面的一小段文字
                .setTicker("收到MyScse助手的信息~")             //收到信息后状态栏显示的文字信息
                .setWhen(System.currentTimeMillis())           //设置通知时间
                .setSmallIcon(R.drawable.app_icon)            //设置小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon))                     //设置大图标
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)    //设置默认的三色灯与振动器
                .setAutoCancel(true)                           //设置点击后取消Notification
                .setContentIntent(pendingIntent);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0x11, notification);
        }
    }
}
