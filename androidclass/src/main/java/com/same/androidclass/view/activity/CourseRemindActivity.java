package com.same.androidclass.view.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.same.androidclass.R;
import com.same.androidclass.common.AppConfig;
import com.same.androidclass.presenter.CourseRemindPresenter;
import com.same.androidclass.service.ExtraService;
import com.same.androidclass.util.Colors;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.view.view.CourseRemindView;
import com.zcw.togglebutton.ToggleButton;

import java.util.Calendar;
import java.util.prefs.PreferenceChangeEvent;

import cn.qqtheme.framework.picker.TimePicker;

/**
 * 课程提醒功能activity
 * Created by alic on 16-5-19.
 */
public class CourseRemindActivity extends Activity implements CourseRemindView, View.OnClickListener, ToggleButton.OnToggleChanged {
    private View backIcon;
    private ToggleButton remindButton;
    private CourseRemindPresenter presenter;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_remind);
        initView();
    }

    public void initView() {
        //AlarmManager实例化
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //指定服务
        service = new Intent(CourseRemindActivity.this, ExtraService.class);
        //创建PendingIntent对象
        pendingIntent = PendingIntent.getService(CourseRemindActivity.this, 0, service, 0);

        presenter = new CourseRemindPresenter(this, this);
        //处理返回icon
        backIcon = findViewById(R.id.course_remind_back_icon);
        IconicFontDrawable back = new IconicFontDrawable(this, EntypoIcon.CHEVRON_THIN_LEFT);
        back.setIconColor(Colors.color_pure_white);
        backIcon.setBackground(back);
        backIcon.setOnClickListener(this);

        //
        remindButton = (ToggleButton) findViewById(R.id.remind_toggle_button);
        remindButton.setOnToggleChanged(this);
        SharedPreferences sharedPreferences = getSharedPreferences(AppConfig.SHAREPREFERENCES_NAME, MODE_PRIVATE);

        boolean courseRemind = sharedPreferences.getBoolean("courseRemind", false);
        if (courseRemind)
            remindButton.setToggleOn();
        else
            remindButton.setToggleOff();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.course_remind_back_icon:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onToggle(boolean on) {
        if (on)
            presenter.doRemindOn();
        else
            presenter.doRemindOff();
    }

    @Override
    public void remindOn() {
        SharedPreferences sharedPreferences = getSharedPreferences(AppConfig.SHAREPREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("courseRemind", true);
        edit.apply();
        Toast.makeText(this, "alic-on", Toast.LENGTH_SHORT).show();
        //默认选中当前时间
        TimePicker picker = new TimePicker(this, TimePicker.HOUR_OF_DAY);
//        String courseRemind = DataUtil.readSharedPreference(CourseRemindActivity.this, "courseRemind", "");
//        if (!courseRemind.equals("")){
//            picker.setSelectedItem(Integer.parseInt(courseRemind.split(":")[0]),Integer.parseInt(courseRemind.split(":")[1]));
//        }
        picker.setTopLineVisible(true);
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                DataUtil.saveSharedPreference(CourseRemindActivity.this, "remindTime", hour + ":" + minute);
                //指定时间
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                // 根据用户选择时间来设置Calendar对象
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
                calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
                calendar.set(Calendar.SECOND, 5);
                //配置oka啦 那就执行啦
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);

                Toast.makeText(CourseRemindActivity.this, "嘿 设置成功 " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
            }
        });
        picker.show();
    }

    @Override
    public void remindOff() {
        SharedPreferences sharedPreferences = getSharedPreferences(AppConfig.SHAREPREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("courseRemind", false);
        edit.apply();
//        Toast.makeText(this, "alic-off", Toast.LENGTH_SHORT).show();
        alarmManager.cancel(pendingIntent);
    }
}
