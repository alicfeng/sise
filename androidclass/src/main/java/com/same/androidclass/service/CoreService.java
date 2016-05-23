package com.same.androidclass.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.same.androidclass.presenter.CoreServicePresenter;
import com.same.androidclass.util.NetWorkUtils;

/**
 * 核心服务
 * Created by alic on 16-5-22.
 */
public class CoreService extends Service implements CoreServiceDo {
    private CoreServicePresenter presenter;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Toast.makeText(this, "coreService已经启动", Toast.LENGTH_LONG).show();
    }

    public void init(){
        presenter = new CoreServicePresenter(this,this);
        //第一件事就是获取行政周数
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (NetWorkUtils.isNetworkConnected(CoreService.this))
                    presenter.getCurrentWeek();
            }
        }).start();
        //第二件事就是待续~~~

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void getCurrentWeek() {
        //真好 好心人做啦
    }
}
