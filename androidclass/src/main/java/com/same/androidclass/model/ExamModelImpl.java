package com.same.androidclass.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.same.androidclass.bean.Course;
import com.same.androidclass.bean.Exam;
import com.same.androidclass.common.AppConfig;
import com.same.androidclass.common.SchoolConfig;
import com.same.androidclass.model.listener.OnGetCourseRemoteListener;
import com.same.androidclass.model.listener.OnGetExamListener;
import com.same.androidclass.util.AppUtil;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.util.OkHttpUtil;
import com.same.androidclass.util.SchoolUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 考试model
 * Created by alic on 16-5-13.
 */
public class ExamModelImpl implements ExamModel {
    @Override
    public void getExamFromServer(String username, String password, OnGetExamListener listener) {
        try {
            String[] hidden = SchoolConfig.getHiddenMap();
            //post传过去的参数
            FormBody body = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .add(hidden[0], hidden[1])
                    .build();

            Request request = new Request.Builder()
                    .url(SchoolConfig.LOGIN_URL)
                    .post(body)
                    .header("Host", SchoolConfig.BROWSER_HOST)
                    .header("User-Agent", SchoolConfig.BROWSER_USER_AGENT)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Referer", "http://my.scse.com.cn/service_menu.asp")
                    .build();
            //post提交
            Response response = OkHttpUtil.execute(request);

            if (response.isSuccessful()) {

                if (!response.body().string().trim().equals("<script>top.location.href='/sise/index.jsp'</script>")) {
                    listener.failed();
                    return;
                } else {
                    System.out.println("登陆成功");
                    String cookie = response.headers().values("Set-Cookie").toString().trim().split(";")[0].substring(1);


                    //获取studentId的参数
                    Request requestPara = new Request.Builder()
                            .url("http://class.sise.com.cn:7001/sise/module/student_states/student_select_class/main.jsp")
                            .header("Cookie", cookie)
                            .header("Host", "class.sise.com.cn:7001")
                            .header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:45.0) Gecko/20100101 Firefox/45.0")
                            .build();
                    Response responsePara = null;
                    String studentID = "";
                    responsePara = OkHttpUtil.execute(requestPara);

                    if (responsePara.isSuccessful()) {
                        String messageHtml = responsePara.body().string();
                        Document document = Jsoup.parse(messageHtml);
                        Element td = document.getElementsByTag("td").get(14);
                        studentID = td.attr("onclick").split("studentid=")[1].split("'")[0];


                        Request requestExam = new Request.Builder()
                                .get()
                                .url(SchoolConfig.STUDENT_EXAM_URL + studentID)
                                .header("referer", "http://class.sise.com.cn:7001/sise/module/student_states/student_select_class/main.jsp")
                                .header("Cookie", cookie)
                                .header("Host", SchoolConfig.BROWSER_HOST)
                                .header("User-Agent", SchoolConfig.BROWSER_USER_AGENT)
                                .build();

                        Response responseExam = OkHttpUtil.execute(requestExam);
                        if (responseExam.isSuccessful()) {
                            Document documentExam = Jsoup.parse(responseExam.body().string());
                            List<Exam> exams = new ArrayList<>();

                            Element tableMain = documentExam.getElementsByTag("table").get(4);

                            Elements examTrs = tableMain.getElementsByTag("tr");
                            String studentCodeId = documentExam.getElementsByTag("div").get(5).text().trim();
                            for (int i = 1; i < examTrs.size(); i++) {
                                Exam exam = new Exam();

                                exam.setStuentId(studentCodeId);
                                exam.setCourseCode(examTrs.get(i).getElementsByTag("td").get(0).text());
                                exam.setCourseName(examTrs.get(i).getElementsByTag("td").get(1).text());
                                exam.setExamDate(examTrs.get(i).getElementsByTag("td").get(2).text());
                                exam.setExamTime(examTrs.get(i).getElementsByTag("td").get(3).text());
                                exam.setPlaceCode(examTrs.get(i).getElementsByTag("td").get(4).text());
                                exam.setPlaceName(examTrs.get(i).getElementsByTag("td").get(5).text());
                                exam.setExamSeat(Integer.parseInt(examTrs.get(i).getElementsByTag("td").get(6).text()));
                                exam.setExamStatus(examTrs.get(i).getElementsByTag("td").get(7).text());

                                //test
//                                System.out.println("学号"+exam.getStuentId());
//                                System.out.println("代码"+exam.getCourseCode());
//                                System.out.println("课程名称"+exam.getCourseName());
//                                System.out.println("日期"+exam.getExamDate());
//                                System.out.println("时间"+exam.getExamTime());
//                                System.out.println("考试地点代码"+exam.getPlaceCode());
//                                System.out.println("考试地点"+exam.getPlaceName());
//                                System.out.println("座位"+exam.getExamSeat());
//                                System.out.println("状态"+exam.getExamStatus());
                                //test - end

                                exams.add(exam);
                            }
                            listener.success(exams);
                        }

                    } else {
                        System.out.println("失败了......");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//        private String studentId;//学生学号
//        private String courseCode;//课程代码
//        private String courseName;//课程名称
//        private String examDate;//考试日期
//        private String examTime;//考试时间
//        private String placeCode;//考场代码
//        private String placeName;//考场名称
//        private int examSeat;//考试座位
//        private String examStatus;//考试状态

    @Override
    public List<Exam> getExamFromLocal(Context context, String studentId) {
        List<Exam> exams = new ArrayList<>();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(AppConfig.TABLE_EXAM, null, "studentId=?", new String[]{studentId}, null, null, null);

        while (cursor.moveToNext()) {
            Exam exam = new Exam();
            exam.setStuentId(cursor.getString(cursor.getColumnIndex("studentId")));
            exam.setCourseCode(cursor.getString(cursor.getColumnIndex("courseCode")));
            exam.setCourseName(cursor.getString(cursor.getColumnIndex("courseName")));
            exam.setExamDate(cursor.getString(cursor.getColumnIndex("examDate")));
            exam.setExamTime(cursor.getString(cursor.getColumnIndex("examTime")));
            exam.setPlaceCode(cursor.getString(cursor.getColumnIndex("placeCode")));
            exam.setPlaceName(cursor.getString(cursor.getColumnIndex("placeName")));
            exam.setExamSeat(cursor.getInt(cursor.getColumnIndex("examSeat")));
            exam.setExamStatus(cursor.getString(cursor.getColumnIndex("examStatus")));
            exams.add(exam);
        }
        //close
        cursor.close();
        readableDatabase.close();
        if (exams.size() > 0)
            return exams;
        else
            return null;
    }

    @Override
    public void saveExamToLocal(Context context, List<Exam> exams) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();

        Cursor cursor = readableDatabase.query(
                AppConfig.TABLE_EXAM,
                null,
                "studentId=?", new String[]{DataUtil.readSharedPreference(context, "username", "")},
                null,
                null,
                null);

        if (cursor.getCount() == exams.size()) {

        } else {
            for (Exam exam : exams) {
                ContentValues values = new ContentValues();
                values.put("studentId", exam.getStuentId());
                values.put("courseCode", exam.getCourseCode());
                values.put("courseName", exam.getCourseName());
                values.put("examDate", exam.getExamDate());
                values.put("examTime", exam.getExamTime());
                values.put("placeCode", exam.getPlaceCode());
                values.put("placeName", exam.getPlaceName());
                values.put("examSeat", exam.getExamSeat());
                values.put("examStatus", exam.getExamStatus());
                writableDatabase.insert(AppConfig.TABLE_EXAM, null, values);
                //debug
                AppUtil.LOG(this.getClass().getName(), "考试时间已经导入");
            }
        }
        writableDatabase.close();
        readableDatabase.close();
        cursor.close();
    }

    @Override
    public boolean deleteExamFromLocal(Context context, String studentId) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();
        int delete = writableDatabase.delete(AppConfig.TABLE_EXAM, "studentId=?", new String[]{studentId});
        //debug
        AppUtil.LOG(this.getClass().getName(), "我在删除考试数据------------ing");
        if (delete >= 0)
            return true;
        else
            return false;
    }
}
