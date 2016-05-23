package com.same.androidclass.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.same.androidclass.bean.Attendance;
import com.same.androidclass.model.AttendanceModel;
import com.same.androidclass.model.AttendanceModelImpl;
import com.same.androidclass.model.listener.OnGetAttendanceListener;
import com.same.androidclass.util.AppUtil;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.view.view.AttendanceView;

import java.util.ArrayList;
import java.util.List;

/**
 * 考勤Presenter
 * Created by alic on 16-5-9.
 */
public class AttendancePresenter {
    private Context context;
    private AttendanceModel attendanceModel;
    private AttendanceView attendanceView;
    Handler handler;
    //临时数据
    private List<Attendance> attendanceList = new ArrayList<>();

    public AttendancePresenter(AttendanceView attendanceView, Context context) {
        this.attendanceView = attendanceView;
        this.context = context;
        this.attendanceModel = new AttendanceModelImpl();
        handler = new Handler(Looper.getMainLooper());
    }

    //控制获取数据
    public void doGetAttendance() {
        final List<Attendance> localAttendances = attendanceModel.getAttendanceFromLocal(
                context,
                DataUtil.readSharedPreference(context, "username", ""),
                attendanceView.getYearSemester()
        );
        if (localAttendances != null) {
            //debug
            AppUtil.LOG(this.getClass().getName(), "本地有考勤数据( ⊙o⊙ )哇");
            attendanceList = localAttendances;
            attendanceList = localAttendances;
            //
            handler.post(new Runnable() {
                @Override
                public void run() {

                    attendanceView.displayAttendances(localAttendances);
                }
            });

        } else {
            attendanceModel.getAttendanceFromServer(
                    DataUtil.readSharedPreference(context, "username", ""),
                    DataUtil.readSharedPreference(context, "password", ""),
                    attendanceView.getYearSemester(),
                    new OnGetAttendanceListener() {
                        @Override
                        public void success(final List<Attendance> attendances) {
                            //

                            attendanceList = attendances;
                            doRunUILoad();

                            attendanceList = attendances;
                            //异步将数据导入本地数据库
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    attendanceModel.saveAttendanceToLocal(context, attendances, DataUtil.readSharedPreference(context, "username", ""));
                                }
                            }).start();
                        }

                        @Override
                        public void successNothing() {

                        }

                        @Override
                        public void failed() {

                        }
                    }
            );
        }
    }

    public void doRunUILoad() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                attendanceView.displayAttendances(attendanceList);
            }
        });
    }

}
