package com.same.androidclass.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.same.androidclass.bean.Exam;
import com.same.androidclass.model.ExamModel;
import com.same.androidclass.model.ExamModelImpl;
import com.same.androidclass.model.StudentModel;
import com.same.androidclass.model.StudentModelImpl;
import com.same.androidclass.model.listener.OnGetExamListener;
import com.same.androidclass.util.AppUtil;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.view.view.ExamView;

import java.util.List;

/**
 * 考试presenter
 * Created by alic on 16-5-13.
 */
public class ExamPresenter {
    private Context context;
    private ExamView examView;
    private ExamModel examModel;
    private StudentModel studentModel;
    private Handler mainHandler;
    private Handler myHandler;


    public ExamPresenter(Context context, ExamView examView) {
        this.context = context;
        this.examView = examView;
        examModel = new ExamModelImpl();
        studentModel = new StudentModelImpl();
        mainHandler = new Handler(Looper.getMainLooper());
        myHandler = new Handler(Looper.myLooper());
    }

    public void doLoadExam() {
        //从本地获取数据
        final List<Exam> localExam = examModel.getExamFromLocal(context, DataUtil.readSharedPreference(context, "username", ""));

        if (localExam != null) {
            //debug
            AppUtil.LOG(this.getClass().getName(), "本地考试有数据--------------------------");
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    examView.displayExam(localExam);
                }
            });
        } else {
            //从服务器获取数据
            //debug
            AppUtil.LOG(this.getClass().getName(), "本地考试没有数据--------------------------");
            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    examView.showLoading();
                }
            });
            examModel.getExamFromServer(DataUtil.readSharedPreference(context, "username", ""),
                    DataUtil.readSharedPreference(context, "password", ""), new OnGetExamListener() {
                        @Override
                        public void success(final List<Exam> exams) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    examView.displayExam(exams);
                                    examView.hideLoading();
                                }
                            });
                            //异步将数据导入本地数据库
                            myHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (exams.size() != 0)
                                        examModel.saveExamToLocal(context, exams);
                                }
                            });
                        }

                        //
                        @Override
                        public void failed() {
                            examView.showError();
                        }
                    });
        }

    }

    public void doDisplayStudentMessage() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                examView.displayStudentMessage(studentModel.getCurrentUser(context, DataUtil.readSharedPreference(context, "username", "")));
            }
        });
    }

    /**
     * 刷新控制
     */
    public void refreshExam() {
        examModel.getExamFromServer(DataUtil.readSharedPreference(context, "username", ""),
                DataUtil.readSharedPreference(context, "password", ""), new OnGetExamListener() {
                    @Override
                    public void success(final List<Exam> exams) {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                examView.displayExam(exams);
                                examView.hideRefresh();
                                examView.showSuccess();
                            }
                        });
                        //异步将数据导入本地数据库
                        myHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (exams.size() != 0) {
                                    //先删除本地相关数据
                                    examModel.deleteExamFromLocal(context, DataUtil.readSharedPreference(context, "username", ""));
                                    //再保存本地数据
                                    examModel.saveExamToLocal(context, exams);
                                }
                            }
                        });
                    }

                    //
                    @Override
                    public void failed() {
                        examView.showError();
                    }
                });
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                examView.hideRefresh();
            }
        }, 10000);
    }

}
