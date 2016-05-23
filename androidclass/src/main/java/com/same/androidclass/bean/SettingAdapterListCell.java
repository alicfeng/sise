package com.same.androidclass.bean;

import com.atermenji.android.iconicdroid.IconicFontDrawable;

/**
 * 设置adapter布局的bean
 * Created by alic on 16-4-30.
 */
public class SettingAdapterListCell {
    private IconicFontDrawable icon;
    private String settingName;


    //构造方法 -- start
    public SettingAdapterListCell() {
    }

    public SettingAdapterListCell(IconicFontDrawable icon, String settingName) {
        this.icon = icon;
        this.settingName = settingName;
    }

    //构造方法 -- end


    //set and get方法 -- start

    public IconicFontDrawable getIcon() {
        return icon;
    }

    public void setIcon(IconicFontDrawable icon) {
        this.icon = icon;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }


    //set and get方法 -- end
}
