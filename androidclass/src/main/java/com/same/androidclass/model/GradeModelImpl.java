package com.same.androidclass.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.same.androidclass.bean.Credit;
import com.same.androidclass.bean.Grade;
import com.same.androidclass.common.AppConfig;
import com.same.androidclass.common.SchoolConfig;
import com.same.androidclass.model.listener.OnGetGradeListener;
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
 * Created by alic on 16-5-14.
 */
public class GradeModelImpl implements GradeModel {

    @Override
    public void getGradeAndCourseMessageFromServer(String username, String password, OnGetGradeListener listener) {
        try {
            String[] hidden = SchoolConfig.getHiddenMap();
            //post传过去的参数
            FormBody body = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .add(hidden[0], hidden[1])
                    .build();

            Request requestLogin = new Request.Builder()
                    .url(SchoolConfig.LOGIN_URL)
                    .post(body)
                    .header("Host", SchoolConfig.BROWSER_HOST)
                    .header("User-Agent", SchoolConfig.BROWSER_USER_AGENT)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Referer", "http://my.scse.com.cn/service_menu.asp")
                    .build();
            //post提交
            Response responseLogin = OkHttpUtil.execute(requestLogin);

            if (responseLogin.isSuccessful()) {

                if (!responseLogin.body().string().trim().equals("<script>top.location.href='/sise/index.jsp'</script>")) {
                    System.out.println("用户名或密码错误");

                } else {
                    System.out.println("登陆成功");
                    String cookie = responseLogin.headers().values("Set-Cookie").toString().trim().split(";")[0].substring(1);
                    //获取studentId
                    Request requestStudentMenu = new Request.Builder()
                            .url(SchoolConfig.MYSCSE_MENU_URL)
                            .header("Cookie", cookie)
                            .header("Host", SchoolConfig.BROWSER_HOST)
                            .header("User-Agent", SchoolConfig.BROWSER_USER_AGENT)
                            .build();
                    Response responseStudentMenu = OkHttpUtil.execute(requestStudentMenu);
                    String studentCode = "";
                    if (responseStudentMenu.isSuccessful()) {
                        String messageHtml = responseStudentMenu.body().string();
                        Document document = Jsoup.parse(messageHtml);
                        Element td = document.getElementsByTag("td").get(10);
                        studentCode = td.attr("onclick").split("&")[1];

                    }

                    //获取个人信息与课程成绩
                    String path = "http://class.sise.com.cn:7001/SISEWeb/pub/course/courseViewAction.do?method=doMain&" + studentCode;
                    Request request = new Request.Builder()
                            .url(path)
                            .get()
                            .header("Cookie", cookie)
                            .header("Host", SchoolConfig.BROWSER_HOST)
                            .header("Referer", "http://my.scse.com.cn/service_menu.asp")
                            .build();
                    Response responseStudentMessage = OkHttpUtil.execute(request);
                    if (responseStudentMessage.isSuccessful()) {
                        String back = responseStudentMessage.body().string().trim();

                        if (back.equals("<script>top.location.href='login.jsp';</script>")) {
                            System.out.println("用户名或密码错误");
                        } else {
                            System.out.println("登陆成功");
                            Document document = Jsoup.parse(back);

                            //必修
                            Element tableMain = document.getElementsByTag("table").get(6);
                            Elements byTagTR = tableMain.getElementsByTag("tr");
                            List<Grade> grades = new ArrayList<>();
                            String term = "";//学期
                            float optionGradeAll = 0;
                            for (int i = 1; i < byTagTR.size(); i++) {

                                Elements td = byTagTR.get(i).getElementsByTag("td");

                                term = td.get(0).text().trim().length() != 0 ? td.get(0).text().trim() : term;

                                Grade grade = new Grade();

                                grade.setStudentId(username);
                                grade.setTerm(term);
                                grade.setCourseCode(td.get(1).text().trim());
                                grade.setCourseName(td.get(2).text().trim());
                                grade.setCredit((td.get(3).text().trim().length() == 0) ? (float) 0.0 : Float.parseFloat(td.get(3).text().trim()));
                                grade.setMethod(td.get(4).text().trim());
                                grade.setSchoolYear(td.get(7).text().trim());
                                grade.setGrade(td.get(8).text().trim());
                                grade.setCredited((td.get(9).text().trim().length() == 0) ? (float) 0.0 : Float.parseFloat(td.get(9).text().trim()));
                                grade.setGenre(1);

                                grades.add(grade);
                                //start test
//                                System.out.println("学生学号" + grade.getStudentId());
//                                System.out.println("学期" + grade.getTerm());
//                                System.out.println("课程代码" + grade.getCourseCode());
//                                System.out.println("课程名称" + grade.getCourseName());
//                                System.out.println("学分" + grade.getCredit());
//                                System.out.println("考核方式" + grade.getMethod());
//                                System.out.println("修读学年学期" + grade.getSchoolYear());
//                                System.out.println("成绩" + grade.getGrade());
//                                System.out.println("已得学分" + grade.getCredited());
//                                System.out.println("类型" + grade.getGenre());
//                                System.out.println("-----------------------------------");
                            }

                            //选修
                            Element tableAlt = document.getElementsByTag("table").get(9);

                            Elements byTagTRAlt = tableAlt.getElementsByTag("tr");
                            System.out.println(byTagTRAlt.size());
                            for (int i = 1; i < byTagTRAlt.size(); i++) {

                                Elements td = byTagTRAlt.get(i).getElementsByTag("td");
                                Grade grade = new Grade();

                                grade.setStudentId(username);
                                grade.setTerm("");
                                grade.setCourseCode(td.get(0).text().trim());
                                grade.setCourseName(td.get(1).text().trim());
                                grade.setCredit((td.get(2).text().trim().length() == 0) ? (float) 0.0 : Float.parseFloat(td.get(2).text().trim()));
                                grade.setMethod(td.get(3).text().trim());
                                grade.setSchoolYear(td.get(6).text().trim());
                                grade.setGrade(td.get(7).text().trim());
                                grade.setCredited((td.get(8).text().trim().length() == 0) ? (float) 0.0 : Float.parseFloat(td.get(8).text().trim()));
                                grade.setGenre(0);

                                optionGradeAll += grade.getCredit();
                                grades.add(grade);
                                //start test
//                                System.out.println("学生学号" + grade.getStudentId());
//                                System.out.println("学期" + grade.getTerm());
//                                System.out.println("课程代码" + grade.getCourseCode());
//                                System.out.println("课程名称" + grade.getCourseName());
//                                System.out.println("学分" + grade.getCredit());
//                                System.out.println("考核方式" + grade.getMethod());
//                                System.out.println("修读学年学期" + grade.getSchoolYear());
//                                System.out.println("成绩" + grade.getGrade());
//                                System.out.println("已得学分" + grade.getCredited());
//                                System.out.println("类型" + grade.getGenre());
//                                System.out.println("-----------------------------------");
//                                System.out.println(grades.size());
                            }


                            //课程绩点信息
                            Credit credit = new Credit();
                            credit.setStudentId(username);
                            credit.setObligatoryGradeAll(Float.parseFloat(document.getElementsByTag("table").get(7).getElementsByTag("tr").get(0).getElementsByTag("td").get(1).text()));
                            credit.setObligatoryGraded(Float.parseFloat(document.getElementsByTag("table").get(7).getElementsByTag("tr").get(1).getElementsByTag("td").get(1).text()));
                            credit.setOptionGradeAll(optionGradeAll);
                            credit.setOptionGraded(Float.parseFloat(document.getElementsByTag("table").get(10).getElementsByTag("tr").get(0).getElementsByTag("td").get(1).text()));
                            credit.setGraded(Float.parseFloat(document.getElementsByTag("table").get(10).getElementsByTag("tr").get(2).getElementsByTag("td").get(1).text()));
                            credit.setGrading(Float.parseFloat(document.getElementsByTag("table").get(10).getElementsByTag("tr").get(3).getElementsByTag("td").get(3).text()));
                            credit.setOptionCourseGrade(Float.parseFloat(document.getElementsByTag("table").get(10).getElementsByTag("tr").get(4).getElementsByTag("td").get(3).text()));
                            credit.setExpectedGrade(Float.parseFloat(document.getElementsByTag("table").get(10).getElementsByTag("tr").get(5).getElementsByTag("td").get(3).text()));
                            credit.setAveragePoint(Float.parseFloat(document.getElementsByTag("table").get(10).getElementsByTag("tr").get(6).getElementsByTag("td").get(3).text()));
                            credit.setGradeAll(Float.parseFloat(document.getElementsByTag("table").get(10).getElementsByTag("tr").get(7).getElementsByTag("td").get(1).text()));


//                            System.out.println("学号:" + credit.getStudentId());
//                            System.out.println("必修总学分:" + credit.getObligatoryGradeAll());
//                            System.out.println("必修已获得学分:" + credit.getObligatoryGraded());
//                            System.out.println("选修总学分:" + credit.getOptionGradeAll());
//                            System.out.println("选修已获得学分" + credit.getOptionGraded());
//                            System.out.println("已修学分" + credit.getGraded());
//                            System.out.println("在读课程学分" + credit.getGrading());
//                            System.out.println("已选课程学分" + credit.getOptionCourseGrade());
//                            System.out.println("预期获得学分" + credit.getExpectedGrade());
//                            System.out.println("平均学分绩点" + credit.getAveragePoint());
//                            System.out.println("本专业本年级毕业需修满学分" + credit.getGradeAll());


                            //
                            listener.success(grades, credit);
                        }
                    } else {
                        System.out.println("error");
                        listener.failed();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Grade> getGradeFromLocal(Context context, String studentId, String term, String genre) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor;
        List<Grade> grades = new ArrayList<>();
        if (term.equals("all")) {
            //debug
            AppUtil.LOG(this.getClass().getName(), "本地 all的地方");
            cursor = readableDatabase.query(AppConfig.TABLE_GRADE, null, "studentId=? AND genre=?", new String[]{studentId, genre}, null, null, null);

            while (cursor.moveToNext()) {
                Grade grade = new Grade();
                grade.setStudentId(studentId);
                grade.setTerm(cursor.getString(cursor.getColumnIndex("term")));
                grade.setCourseCode(cursor.getString(cursor.getColumnIndex("courseCode")));
                grade.setCourseName(cursor.getString(cursor.getColumnIndex("courseName")));
                grade.setCredit(cursor.getFloat(cursor.getColumnIndex("credit")));
                grade.setMethod(cursor.getString(cursor.getColumnIndex("method")));
                grade.setSchoolYear(cursor.getString(cursor.getColumnIndex("schoolYear")));
                grade.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                grade.setCredited(cursor.getFloat(cursor.getColumnIndex("credited")));
                grade.setGenre(cursor.getInt(cursor.getColumnIndex("genre")));
                grades.add(grade);
            }
        } else {
            cursor = readableDatabase.query(AppConfig.TABLE_GRADE, null, "studentId=? AND term=? AND genre=?", new String[]{studentId, term, genre}, null, null, null);
            while (cursor.moveToNext()) {
                Grade grade = new Grade();
                grade.setStudentId(studentId);
                grade.setTerm(cursor.getString(cursor.getColumnIndex("term")));
                grade.setCourseCode(cursor.getString(cursor.getColumnIndex("courseCode")));
                grade.setCourseName(cursor.getString(cursor.getColumnIndex("courseName")));
                grade.setCredit(cursor.getFloat(cursor.getColumnIndex("credit")));
                grade.setMethod(cursor.getString(cursor.getColumnIndex("method")));
                grade.setSchoolYear(cursor.getString(cursor.getColumnIndex("schoolYear")));
                grade.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                grade.setCredited(cursor.getFloat(cursor.getColumnIndex("credited")));
                grade.setGenre(cursor.getInt(cursor.getColumnIndex("genre")));
                grades.add(grade);
            }

        }
        //close
        DataBaseHelper.closeDatabase(readableDatabase, cursor);
        if (grades.size() == 0)
            return null;
        else
            return grades;
    }


    @Override
    public void saveGradeToLocal(Context context, List<Grade> exams) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();

        deleteGradeFromLocal(context, DataUtil.readSharedPreference(context, "username", ""));

        for (int i = 0; i < exams.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("studentId", exams.get(i).getStudentId());
            values.put("term", exams.get(i).getTerm());
            values.put("courseCode", exams.get(i).getCourseCode());
            values.put("courseName", exams.get(i).getCourseName());
            values.put("credit", exams.get(i).getCredit());
            values.put("method", exams.get(i).getMethod());
            values.put("schoolYear", exams.get(i).getSchoolYear());
            values.put("grade", exams.get(i).getGrade());
            values.put("credited", exams.get(i).getCredited());
            values.put("genre", exams.get(i).getGenre());

            writableDatabase.insert(AppConfig.TABLE_GRADE, null, values);
            //debug
            AppUtil.LOG(this.getClass().getName(), "插入考成成功---------------------------");
        }
        DataBaseHelper.closeDatabase(writableDatabase);
    }

    @Override
    public boolean deleteGradeFromLocal(Context context, String studentId) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();
        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(AppConfig.TABLE_GRADE, null, "studentId=?", new String[]{studentId}, null, null, null);
        if (cursor.getCount() != 0)
            writableDatabase.delete(AppConfig.TABLE_GRADE, "studentId", new String[]{studentId});
        //close
        DataBaseHelper.closeDatabase(writableDatabase, cursor);
        return true;
    }
}
//        private String studentId;//学生学号
//        private String term;//学期
//        private String courseCode;//课程代码
//        private String courseName;//课程名称
//        private float credit;//学分
//        private String method;//考核方式
//        private String schoolYear;//修读学年学期
//        private String grade;//成绩
//        private float credited;//已得学分
//        private int genre;//类型 必修 选修