package com.same.androidclass.view.view;

/**
 * 欢迎界面接口
 * Created by alic on 16-5-12.
 */
public interface WelcomeView {
    /**
     * 进入主页
     */
    void goHome();

    /**
     * 进入引导界面
     */
    void goGuide();

    /**
     * 登陆界面
     */
    void goLogin();

    /**
     * 开启核心服务
     */
    void startCoreService();
}
