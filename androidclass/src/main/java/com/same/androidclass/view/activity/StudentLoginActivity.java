package com.same.androidclass.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.same.androidclass.R;
import com.same.androidclass.bean.Student;
import com.same.androidclass.presenter.StudentLoginPresenter;
import com.same.androidclass.util.ActivityCollector;
import com.same.androidclass.util.Colors;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.util.NetWorkUtils;
import com.same.androidclass.util.StatusBarCompat;
import com.same.androidclass.view.view.StudentLoginView;

/**
 * 学生登陆界面
 * Created by alic on 16-4-28.
 */
public class StudentLoginActivity extends AppCompatActivity implements StudentLoginView, View.OnClickListener{
    private EditText idCard, password;
    private Button loginBtn;
    private ProgressBar progressBar;
    private StudentLoginPresenter studentLoginPresenter;
    private View userIconView, passwordIconView;
    private IconicFontDrawable userIcon, passwordIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //同化状态栏颜色
        StatusBarCompat.setStatusBarColor(this, StatusBarCompat.COLOR_ToolBar_HIGHTBLUE);
        initView();
        initIcons();
        //app工具类应用处理
        ActivityCollector.addActivity(this);
    }

    //初始化组件
    public void initView() {
        studentLoginPresenter = new StudentLoginPresenter(this,this);
        idCard = (EditText) findViewById(R.id.login_idCard);
        password = (EditText) findViewById(R.id.login_password);
        userIconView = findViewById(R.id.login_username_icon);
        passwordIconView = findViewById(R.id.login_password_icon);
        loginBtn = (Button) findViewById(R.id.login_btn);
        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);
        loginBtn.setOnClickListener(this);
        studentLoginPresenter.displayMessage();
    }

    public void initIcons() {
        userIcon = new IconicFontDrawable(this);
        userIcon.setIcon(EntypoIcon.USER);
        userIcon.setIconColor(Colors.color_theme);

        passwordIcon = new IconicFontDrawable(this);
        passwordIcon.setIcon(EntypoIcon.LOCK);
        passwordIcon.setIconColor(Colors.color_theme);

        userIconView.setBackground(userIcon);
        passwordIconView.setBackground(passwordIcon);
    }

    //获得学号
    @Override
    public String getUsername() {
        return idCard.getText().toString().trim();
    }

    //获得密码
    @Override
    public String getPassword() {
        return password.getText().toString().trim();
    }

    //保存用户信息
    @Override
    public void saveUserMessage() {
        DataUtil.saveSharedPreference(this, "username", getUsername());
        DataUtil.saveSharedPreference(this, "password", getPassword());
    }

    //读取用户信息
    @Override
    public void readUserMessage() {
        idCard.setText(DataUtil.readSharedPreference(this, "username", ""));
        password.setText(DataUtil.readSharedPreference(this, "password", ""));
    }


    //显示登陆中
    @Override
    public void showLoading() {
        //显示
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    //隐藏登陆中
    @Override
    public void hideLoading() {
        //隐藏
         progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    //登陆成功跳转
    @Override
    public void toMainActivity(Student student) {
        Intent mainIntent = new Intent();
        mainIntent.setClass(this, MainActivity.class);
        Bundle studentBundle = new Bundle();
        studentBundle.putSerializable("student", student);
        mainIntent.putExtras(studentBundle);
        startActivity(mainIntent);
        finish();
    }

    //显示登录失败
    @Override
    public void showFailed() {
        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                //检测用户名
                if (getUsername().trim().length() != 0 && getPassword().trim().length() != 0) {
                    //检测网络
                    if (NetWorkUtils.isNetworkConnected(StudentLoginActivity.this)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                studentLoginPresenter.login();
                            }
                        }).start();
                    } else {
                        Toast.makeText(this, "当前网络不可用", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "学号或密码不允许为空", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        ActivityCollector.AppExit(this);
    }

}
