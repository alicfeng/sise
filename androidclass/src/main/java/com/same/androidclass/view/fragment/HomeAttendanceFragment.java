package com.same.androidclass.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.same.androidclass.R;
import com.same.androidclass.bean.Attendance;
import com.same.androidclass.presenter.AttendancePresenter;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.view.view.AttendanceView;
import com.same.androidclass.view.adapter.AttendanceAdapter;
import com.thuongnh.zprogresshud.ZProgressHUD;

import java.util.List;

/**
 *
 * Created by alic on 16-5-8.
 */
public class HomeAttendanceFragment extends Fragment implements AttendanceView {
    private View viewAttendance;
    private AttendancePresenter attendancePresenter;
    private ListView home_attendance_lv;
    private ZProgressHUD loading;//加载组件
    private TextView attendance;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewAttendance = inflater.inflate(R.layout.home_viewpager_two, container, false);
        attendancePresenter = new AttendancePresenter(this, viewAttendance.getContext());
        initView();
        return viewAttendance;
    }

    public void initView() {
        //loading
        loading = ZProgressHUD.getInstance(viewAttendance.getContext());
        home_attendance_lv=(ListView)viewAttendance.findViewById(R.id.home_attendance_listView);
        attendance = (TextView) viewAttendance.findViewById(R.id.attendance_attendance);
        //执行数据获取
        new Thread(new Runnable() {
            @Override
            public void run() {
                attendancePresenter.doGetAttendance();
            }
        }).start();


    }

    @Override
    public String getYearSemester() {
        return "20152";
    }

    //数据显示处理
    @Override
    public void displayAttendances(List<Attendance> attendances) {
        home_attendance_lv.setAdapter(new AttendanceAdapter(viewAttendance.getContext(),attendances));
        attendance.setText("缺勤次数 : "+attendances.get(0).getAbsenceNum());
    }



    /**
     * 获取成功，没有考勤数据
     * 提示没有考勤信息
     */
    @Override
    public void displayNothing() {

    }


    /**
     * 获取失败
     * 提示错误
     */
    @Override
    public void alertError() {

    }

    @Override
    public void showLoading() {
        loading.show();
    }

    @Override
    public void hideLoading() {
        loading.dismissWithSuccess();
    }

}
