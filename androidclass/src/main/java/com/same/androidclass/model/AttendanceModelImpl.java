package com.same.androidclass.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.same.androidclass.bean.Attendance;
import com.same.androidclass.common.AppConfig;
import com.same.androidclass.common.SchoolConfig;
import com.same.androidclass.model.listener.OnGetAttendanceListener;
import com.same.androidclass.util.AppUtil;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.util.OkHttpUtil;

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
 * 考勤model类
 * Created by alic on 16-5-9.
 */
public class AttendanceModelImpl implements AttendanceModel {
    @Override
    public void getAttendanceFromServer(String username, String password, String yearSemester, OnGetAttendanceListener listener) {

        List<Attendance> attendances = new ArrayList<>();
        try {
            String[] hidden = SchoolConfig.getHiddenMap();
            //post传过去的参数
            FormBody loginBody = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .add(hidden[0], hidden[1])
                    .build();

            Request loginRequest = new Request.Builder()
                    .url(SchoolConfig.LOGIN_URL)
                    .post(loginBody)
                    .header("Host", SchoolConfig.BROWSER_HOST)
                    .header("User-Agent", SchoolConfig.BROWSER_USER_AGENT)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Referer", "http://my.scse.com.cn/service_menu.asp")
                    .build();
            //post提交
            Response loginResponse = OkHttpUtil.execute(loginRequest);

            if (loginResponse.isSuccessful()) {

                if (!loginResponse.body().string().trim().equals("<script>top.location.href='/sise/index.jsp'</script>")) {
                    listener.failed();
                } else {
                    System.out.println("登陆成功");
                    String cookie = loginResponse.headers().values("Set-Cookie").toString().trim().split(";")[0].substring(1);


                    //获取考勤url所带的参数
                    Request requestPara = new Request.Builder()
                            .url(SchoolConfig.MYSCSE_MENU_URL)
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
                        System.out.println("url参数：" + studentID);
                    }


                    //获取考勤信息 post
                    FormBody body = new FormBody.Builder()
                            .add("studentID", studentID)
                            .add("studentCode", username)
                            .add("yearSemester", yearSemester)
                            .build();

                    Request build = new Request.Builder()
                            .url(SchoolConfig.STUDENT_ATTENDANCE_URL)
                            .post(body)
                            .header("Host", SchoolConfig.BROWSER_HOST)
                            .header("User-Agent", SchoolConfig.BROWSER_USER_AGENT)
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .header("Referer", "http://class.sise.com.cn:7001/SISEWeb/pub/studentstatus/attendance/studentAttendanceViewAction.do?method=doYearTermSelect")
                            .header("Cookie", cookie)
                            .build();
                    Response responsePost = OkHttpUtil.execute(build);
                    if (responsePost.isSuccessful()) {

                        String classMessage = responsePost.body().string();
                        Document document = Jsoup.parse(classMessage);
                        System.out.println(document.getElementsByTag("table").size());
                        if (document.getElementsByTag("table").size() == 8) {
                            System.out.println("没有考勤相关");
                            listener.successNothing();
                        } else {
                            System.out.println("have");
                            Elements selects = document.getElementById("table1").getElementsByTag("tr");

                            //分析数据并打包
                            for (int i = 1; i < selects.size(); i++) {
                                Attendance attendance = new Attendance();
                                Elements trs = selects.get(i).getElementsByTag("td");
                                attendance.setClassCode(trs.get(0).text().trim());
                                attendance.setClassName(trs.get(1).text().trim());
                                attendance.setClassStatus(trs.get(2).text().trim());
                                attendance.setAbsenceNum(document.getElementsByTag("td").get(7).text().split("：")[1]);
                                attendance.setStudentId(username);
                                attendance.setTerm(document.select("option[selected=selected]").first().val());
                                attendances.add(attendance);
                            }

                            //success
                            listener.success(attendances);
                            //test
                            for (Attendance attendance : attendances) {
                                System.out.println(attendance.getClassCode());
                                System.out.println(attendance.getClassName());
                                System.out.println(attendance.getClassStatus());
                                System.out.println(attendance.getAbsenceNum());
                                System.out.println(attendance.getTerm());
                                System.out.println(attendance.getStudentId());
                            }
                            System.out.println("这学期有几门课呢?" + attendances.size());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Attendance> getAttendanceFromLocal(Context context, String studentId, String term) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(AppConfig.TABLE_ATTENDANCE, null, "studentId=? AND term=?", new String[]{studentId, term}, null, null, null);

        List<Attendance> attendances = new ArrayList<>();

        while (cursor.moveToNext()) {
            Attendance attendance = new Attendance();
            attendance.setClassCode(cursor.getString(cursor.getColumnIndex("classCode")));
            attendance.setClassName(cursor.getString(cursor.getColumnIndex("className")));
            attendance.setClassStatus(cursor.getString(cursor.getColumnIndex("classStatus")));
            attendance.setAbsenceNum(cursor.getString(cursor.getColumnIndex("absenceNum")));
            attendance.setStudentId(cursor.getString(cursor.getColumnIndex("studentId")));
            attendance.setTerm(cursor.getString(cursor.getColumnIndex("term")));
            attendances.add(attendance);
        }
        cursor.close();
        readableDatabase.close();
        dataBaseHelper.close();
        if (attendances.size() > 0)
            return attendances;
        else
            return null;
    }

    /**
     * 将数据保存到本地数据库
     *
     * @param context     context
     * @param attendances attendances
     * @param studentId   studentId
     */
    @Override
    public void saveAttendanceToLocal(Context context, List<Attendance> attendances, String studentId) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();
        Cursor cursor = readableDatabase.query(AppConfig.TABLE_ATTENDANCE, null, "studentId=? AND term=?", new String[]{studentId, attendances.get(0).getTerm()}, null, null, null);

        if (cursor.getCount() == attendances.size()) {

        } else {
            //有改变就先清空
            writableDatabase.delete(AppConfig.TABLE_ATTENDANCE, "studentId=? AND term=?", new String[]{studentId, attendances.get(0).getTerm()});

            for (Attendance attendance : attendances) {
                ContentValues values = new ContentValues();
                values.put("classCode", attendance.getClassCode());
                values.put("className", attendance.getClassName());
                values.put("classStatus", attendance.getClassStatus());
                values.put("absenceNum", attendance.getAbsenceNum());
                values.put("studentId", studentId);
                values.put("term", attendance.getTerm());

                writableDatabase.insert(AppConfig.TABLE_ATTENDANCE, null, values);
                System.out.println("考勤写入本地成功");
            }
            //debug
            AppUtil.LOG(this.getClass().getName(),"获得了当前学生的信息");
        }
        cursor.close();
        readableDatabase.close();
        writableDatabase.close();
    }
}
