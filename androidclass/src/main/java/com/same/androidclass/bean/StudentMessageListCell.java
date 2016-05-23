package com.same.androidclass.bean;

/**
 * 学生信息实体
 * Created by alic on 16-4-30.
 */
public class StudentMessageListCell {
    private int icon;
    private String messageName;
    private String messageValue;


    //构造方法 -- start
    public StudentMessageListCell() {
    }

    public StudentMessageListCell(int icon, String messageName, String messageValue) {
        this.icon = icon;
        this.messageName = messageName;
        this.messageValue = messageValue;
    }

    public StudentMessageListCell(String messageName, String messageValue) {
        this.messageName = messageName;
        this.messageValue = messageValue;
    }
    //构造方法 -- end


    //set and get方法 -- start

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getMessageValue() {
        return messageValue;
    }

    public void setMessageValue(String messageValue) {
        this.messageValue = messageValue;
    }

    //set and get方法 -- end
}
