package com.same.androidclass.model.listener;

import com.same.androidclass.bean.Student;

/**
 *
 * Created by alic on 16-4-28.
 */
public interface OnLoginListener {
    /**
     * 用户登陆成功
     * @param cookie 学生Bean
     */
    void loginSuccess(String cookie);

    /**
     * 用户登陆失败
     */
    void loginFailed();
}
