package com.same.androidclass.model;

import android.content.Context;

import com.same.androidclass.bean.Student;
import com.same.androidclass.model.listener.OnLoginListener;

/**
 * 学生模型接口
 * Created by alic on 16-4-28.
 */
public interface StudentModel {
    /**
     * 用户登陆
     * @param username 用户名
     * @param password 密码
     * @param onLoginListener 登陆结果监听是否登陆成功
     */
    void login(String username,String password,OnLoginListener onLoginListener);

    /**
     * 保存学生信息到数据库
     * @param student 学生实体
     */
    void saveStudent(Context context,Student student);

    /**
     * 获得当前登陆的学生信息
     * @param context 上下文
     * @param studentId 学生的学号
     * @return 学生实体
     */
    Student getCurrentUser(Context context,String studentId);
}
