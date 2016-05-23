package com.same.androidclass.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.same.androidclass.bean.Student;
import com.same.androidclass.common.SchoolConfig;
import com.same.androidclass.model.listener.OnLoginListener;
import com.same.androidclass.model.StudentModel;
import com.same.androidclass.model.StudentModelImpl;
import com.same.androidclass.util.AppUtil;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.util.OkHttpUtil;
import com.same.androidclass.view.view.StudentLoginView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 学生登陆Presenter
 * Created by alic on 16-4-28.
 */
public class StudentLoginPresenter {
    private StudentModel studentModel;
    private StudentLoginView studentLoginView;
    private Handler handler;
    private Context context;


    public StudentLoginPresenter(StudentLoginView studentLoginView,Context context) {
        this.studentLoginView = studentLoginView;
        this.studentModel = new StudentModelImpl();
        this.context = context;
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 学生登陆
     */
    public void login() {
        //显示登陆loading
        handler.post(new Runnable() {
            @Override
            public void run() {
                studentLoginView.showLoading();
            }
        });
        studentModel.login(studentLoginView.getUsername(), studentLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final String cookie) {

//                studentLoginView.hideLoading();
                try {
                    //获取studentId
                    Request requestStudentMenu = new Request.Builder()
                            .url(SchoolConfig.MYSCSE_MENU_URL)
                            .get()
                            .header("Cookie", cookie)
                            .header("Host", SchoolConfig.BROWSER_HOST)
                            .header("User-Agent", SchoolConfig.BROWSER_USER_AGENT)
                            .build();
                    Response responseStudentMenu = OkHttpUtil.execute(requestStudentMenu);
                    String studentId = "";
                    if (responseStudentMenu.isSuccessful()) {
                        String messageHtml = responseStudentMenu.body().string();
                        Document document = Jsoup.parse(messageHtml);
                        Element td = document.getElementsByTag("td").get(10);

                        studentId = td.attr("onclick").split("&")[1];
                        System.out.println(studentId);
                    }

                    //获取个人信息
                    Request requestMessage = new Request.Builder()
                            .url(SchoolConfig.STUDENT_MESSAGE_URL + studentId)
                            .get()
                            .header("Cookie", cookie)
                            .header("Host", SchoolConfig.BROWSER_HOST)
                            .header("Referer", "http://my.scse.com.cn/service_menu.asp")
                            .build();
                    Response responseStudentMessage = OkHttpUtil.execute(requestMessage);
                    Student student = new Student();
                    if (responseStudentMessage.isSuccessful()) {
                        String back = responseStudentMessage.body().string().trim();

                        if (back.equals("<script>top.location.href='login.jsp';</script>")) {
                            System.out.println("用户名或密码错误");
                        } else {
                            //debug
                            AppUtil.LOG(this.getClass().getName(), "登陆成功");
                            studentLoginView.saveUserMessage();
                            Document document = Jsoup.parse(back);

                            student.setStudentId(document.getElementsByTag("div").get(2).text());
                            student.setUsername(document.getElementsByTag("div").get(3).text());
                            student.setGradeYear(document.getElementsByTag("div").get(4).text());
                            student.setProfession(document.getElementsByTag("div").get(5).text());
                            student.setIdCard(document.getElementsByTag("div").get(6).text());
                            student.setEmail(document.getElementsByTag("div").get(7).text());
                            student.setGradeAdmin(document.getElementsByTag("td").get(21).text());
                            student.setHeadTeacher(document.getElementsByTag("div").get(8).text());
                            student.setAssistant(document.getElementsByTag("div").get(9).text());

                            //保存Cookie
                            DataUtil.saveSharedPreference(context, "cookie", cookie);
                            DataUtil.saveSharedPreference(context,"gradeYear",student.getGradeYear());

                            studentLoginView.toMainActivity(student);
                            studentModel.saveStudent(context,student);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    studentLoginView.hideLoading();
                                }
                            });
                            //test - start
//                            System.out.println(
//                                    "学号:" + student.getStudentId() + "\n"
//                                            + "姓名:" + student.getUsername() + "\n"
//                                            + "年级:" + student.getGradeYear() + "\n"
//                                            + "专业:" + student.getProfession() + "\n"
//                                            + "身份证:" + student.getIdCard() + "\n"
//                                            + "邮箱:" + student.getEmail() + "\n"
//                                            + "行政班:" + student.getGradeAdmin() + "\n"
//                                            + "班主任:" + student.getHeadTeacher() + "\n"
//                                            + "辅导员:" + student.getAssistant() + "\n"
//                            );
                            //test - end
                        }
                    } else {
                        //debug
                        AppUtil.LOG(this.getClass().getName(), "error");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void loginFailed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        studentLoginView.hideLoading();
                        studentLoginView.showFailed();
                    }
                });
            }
        });
    }

    public void displayMessage() {
        studentLoginView.readUserMessage();
    }
}
