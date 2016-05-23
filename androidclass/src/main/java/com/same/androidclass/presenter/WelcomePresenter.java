package com.same.androidclass.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.same.androidclass.view.view.WelcomeView;

/**
 * 欢迎界面presenter
 * Created by alic on 16-5-12.
 */
public class WelcomePresenter {
    private WelcomeView welcomeView;
    private Context context;
    private Handler handler;
    //欢迎界面的睡眠时间
    private static final int SLEEP_TIME = 3000;

    public WelcomePresenter(WelcomeView welcomeView, Context context) {
        this.welcomeView = welcomeView;
        this.context = context;
        handler = new Handler(Looper.myLooper());
    }

    public void doGoHome() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                welcomeView.goHome();
            }
        }, SLEEP_TIME);
    }

    public void doGoGuide() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                welcomeView.goGuide();
            }
        }, SLEEP_TIME);
    }

    public void doGoLogin() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                welcomeView.goLogin();
            }
        }, SLEEP_TIME);
    }
    public void DoStartCoreService(){
        welcomeView.startCoreService();
    }
}
