package com.same.androidclass.bean;

import com.atermenji.android.iconicdroid.IconicFontDrawable;

import java.io.Serializable;

/**
 * 考试详情的adapter布局bean
 * Created by alic on 16-5-14.
 */
public class ExamDetailListCell implements Serializable{
    private IconicFontDrawable iconicFontDrawable;
    private String name;
    private String value;


    public ExamDetailListCell() {
    }

    public ExamDetailListCell(IconicFontDrawable iconicFontDrawable, String name, String value) {
        this.iconicFontDrawable = iconicFontDrawable;
        this.name = name;
        this.value = value;
    }

    public IconicFontDrawable getIconicFontDrawable() {
        return iconicFontDrawable;
    }

    public void setIconicFontDrawable(IconicFontDrawable iconicFontDrawable) {
        this.iconicFontDrawable = iconicFontDrawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
