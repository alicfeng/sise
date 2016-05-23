package com.same.androidclass.view.view;

import com.same.androidclass.bean.Student;

/**
 *学生登陆界面方法接口
 * Created by alic on 16-4-28.
 */
public interface StudentLoginView {
    //获取用户名 密码
    String getUsername();
    String getPassword();

    //保存 读取用户信息
    void saveUserMessage();
    void readUserMessage();

    //显示 隐藏加载
    void showLoading();
    void hideLoading();

    //跳转主界面
    void toMainActivity(Student student);

    //显示错误
    void showFailed();
}
