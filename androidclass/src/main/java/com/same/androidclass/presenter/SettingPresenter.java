package com.same.androidclass.presenter;

import com.same.androidclass.view.view.SettingView;

/**
 * 设置视图辅助类
 * Created by alic on 16-5-3.
 */
public class SettingPresenter {
    private SettingView settingView;

    public SettingPresenter(SettingView settingView) {
        this.settingView = settingView;
    }

    //退出程序
    public void logout(){
        settingView.logout();
    }
    //切换账号
    public void changeUser(){
        settingView.changeUser();
    }
}
