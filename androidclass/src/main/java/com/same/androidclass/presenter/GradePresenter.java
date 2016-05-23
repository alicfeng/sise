package com.same.androidclass.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.same.androidclass.bean.Credit;
import com.same.androidclass.bean.Grade;
import com.same.androidclass.model.CreditModel;
import com.same.androidclass.model.CreditModelImpl;
import com.same.androidclass.model.GradeModel;
import com.same.androidclass.model.GradeModelImpl;
import com.same.androidclass.model.listener.OnGetGradeListener;
import com.same.androidclass.util.AppUtil;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.view.view.GradeView;

import java.util.List;

/**
 * 成绩presenter
 * Created by alic on 16-5-14.
 */
public class GradePresenter {
    private Context context;
    private GradeView gradeView;
    private GradeModel gradeModel;
    private CreditModel creditModel;
    private Handler mainHandler;
    private Handler myHandler;

    public GradePresenter(Context context, GradeView gradeView) {
        this.context = context;
        this.gradeView = gradeView;
        gradeModel = new GradeModelImpl();
        creditModel = new CreditModelImpl();
        mainHandler = new Handler(Looper.getMainLooper());
        myHandler = new Handler(Looper.myLooper());
    }

    public void doDisplayGrade(){

        List<Grade> gradesLocal = gradeModel.getGradeFromLocal(
                context,
                DataUtil.readSharedPreference(context, "username", ""),
//                gradeView.getSchoolYear()
                "all","1"
        );
        Credit credit = creditModel.getCourseMessage(
                context,
                DataUtil.readSharedPreference(context, "username", "")
        );


        //判断本地
        if (gradesLocal==null|| credit ==null){
            //
            //debug
            AppUtil.LOG(this.getClass().getName(), "在这种情况呢 我要上天");
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    gradeView.showLoading();
                }
            });
            gradeModel.getGradeAndCourseMessageFromServer(
                    DataUtil.readSharedPreference(context, "username", ""),
                    DataUtil.readSharedPreference(context, "password", ""),
                    new OnGetGradeListener() {
                        @Override
                        public void success(final List<Grade> grades, Credit credit) {
                            gradeModel.saveGradeToLocal(context,grades);
                            creditModel.saveCourseMessageToLocal(context, credit);
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
//                                    gradeView.displayGrade(grades);
                                    gradeView.hideLoading();
                                }
                            });
                        }

                        @Override
                        public void failed() {

                        }
                    }
            );
        }else {
            //debug
            AppUtil.LOG(this.getClass().getName(), "在这种情况呢 我要在Alic本地逛逛");
//            mainHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    gradeView.displayGrade(gradeModel.getGradeFromLocal(
//                            context,
//                            DataUtil.readSharedPreference(context, "username", ""),
////                            gradeView.getSchoolYear()
//                                    "all"
//                            )
//                    );
//                }
//            });
        }

    }
}
