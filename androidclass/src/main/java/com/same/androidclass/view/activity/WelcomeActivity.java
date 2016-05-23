package com.same.androidclass.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.same.androidclass.R;
import com.same.androidclass.common.AppConfig;
import com.same.androidclass.presenter.WelcomePresenter;
import com.same.androidclass.util.ActivityCollector;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.util.StatusBarCompat;
import com.same.androidclass.view.fragment.HomeFragment;
import com.same.androidclass.view.view.WelcomeView;

/**
 * 欢迎界面
 * Created by alic on 16-5-12.
 */
public class WelcomeActivity extends Activity implements WelcomeView {
    private WelcomePresenter presenter;
    private long lastClickTime = 0;//点击返回键当前的时间戳
    private String TAG = "com.same.androidclass.view.activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        //工具栏处理
        StatusBarCompat.hideStatusBar(this, false);
        init();
        //app工具类应用处理
        ActivityCollector.addActivity(this);

    }

    public void init() {
        presenter = new WelcomePresenter(this, this);
        //开启核心服务
        presenter.DoStartCoreService();
        SharedPreferences preferences = getSharedPreferences(AppConfig.SHAREPREFERENCES_NAME, MODE_PRIVATE);
        boolean isFirst = preferences.getBoolean("isFirst", true);

        //进入activity逻辑处理
        if (!isFirst) {
            if (DataUtil.readSharedPreference(this, "username", null) != null)
                presenter.doGoHome();
            else
                presenter.doGoLogin();
            Log.e(TAG, "我进来不止一次啦");
        } else {
            Log.e(TAG, "第一次进入程序");
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirst", false);
            editor.apply();
            presenter.doGoGuide();
        }
    }

    //进入主页
    @Override
    public void goHome() {
        StatusBarCompat.displayStatusBar(this);
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
        ActivityCollector.addActivity(this);
        finish();
    }

    //进入引导界面
    @Override
    public void goGuide() {
        Intent guideIntent = new Intent(this, GuideActivity.class);
        startActivity(guideIntent);
        ActivityCollector.addActivity(this);
        finish();
    }

    //进入登陆界面
    @Override
    public void goLogin() {
        StatusBarCompat.displayStatusBar(this);
        Intent loginIntent = new Intent(this, StudentLoginActivity.class);
        startActivity(loginIntent);
        //管理activity
        ActivityCollector.addActivity(this);
        finish();
    }

    @Override
    public void startCoreService() {

    }

    @Override
    public void onBackPressed() {
        //退出应用程序处理

        if (lastClickTime <= 0) {
            Toast.makeText(WelcomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            lastClickTime = System.currentTimeMillis();
        } else {
            long currentClickTime = System.currentTimeMillis();
            if (currentClickTime - lastClickTime < 1000) {
                finish();
                System.exit(1);
                ActivityCollector.AppExit(this);
            } else {
                Toast.makeText(WelcomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                lastClickTime = currentClickTime;
            }
        }
    }
}
