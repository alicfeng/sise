package com.same.androidclass.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.same.androidclass.bean.Course;
import com.same.androidclass.common.AppConfig;
import com.same.androidclass.common.SchoolConfig;
import com.same.androidclass.model.listener.OnGetCourseRemoteListener;
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
 * 课程model
 * Created by alic on 16-5-7.
 */
public class CourseModelImpl implements CourseModel {
    @Override
    public void getCoursesFromServer(String username, String password, String schoolYear, String semester, OnGetCourseRemoteListener listener) {
        List<Course> courses = new ArrayList<>();
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
//                    listener.failed();
                    return;
                } else {
                    System.out.println("登陆成功");
                    String cookie = response.headers().values("Set-Cookie").toString().trim().split(";")[0].substring(1);


                    Request classRequest = new Request.Builder()
                            .url(SchoolConfig.STUDENT_COURSE_URL + "schoolyear=" + schoolYear + "&semester=" + semester)
                            .get()
                            .header("Host", "class.sise.com.cn:7001")
                            .header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:45.0) Gecko/20100101 Firefox/45.0")
                            .header("Referer", "http://class.sise.com.cn:7001/sise/module/student_states/student_select_class/main.jsp")
                            .header("Cookie", cookie)
                            .build();

                    Response classResponse = OkHttpUtil.execute(classRequest);
                    if (classResponse.isSuccessful()) {
                        String result = classResponse.body().string();
                        Document document = Jsoup.parse(result);
                        Elements mainTr = document.getElementsByTag("table").get(6).getElementsByTag("tr");
                        System.out.println(mainTr.size());

                        for (int i = 1; i < mainTr.size(); i++) {
                            Elements cell = mainTr.get(i).getElementsByTag("td");
                            System.out.println("-------------------------------------");
                            for (int j = 1; j < cell.size() - 2; j++) {
                                Course course = new Course();
                                if (cell.get(j).text().length() == 1) {
                                    System.out.println("没课");
                                    course.setCourseTime(String.valueOf(j));
                                    course.setCourseWeek(String.valueOf(i));
                                    course.setCourseTerm(document.select("span[class=style17]").first().text().trim().substring(0, 16));
                                    course.setStudentId(username);
                                    courses.add(course);
                                } else {
                                    String pattern = "(\\([A-Z]*[0-9]*)";
                                    String content = cell.get(j).text().trim().split(",")[0];
                                    System.out.println(content);
                                    course.setCourseName(content.split("\\(")[0]);
                                    course.setCourseCode(content.split("\\(")[1].split(" ")[0]);
                                    course.setCourseTeacher(content.split(DataUtil.getReg(content, pattern).trim().substring(1) + " ")[1].split(" ")[0]);

                                    course.setCourseRange(content.split("周")[0].split(course.getCourseTeacher() + " ")[1]);
                                    course.setCourseClassroom(content.split("\\[")[1].split("\\]")[0]);
                                    course.setCourseTime(String.valueOf(j));
                                    course.setCourseWeek(String.valueOf(i));
                                    course.setCourseTerm(document.select("span[class=style17]").first().text().trim().substring(0, 16));
                                    course.setStudentId(username);
                                    courses.add(course);

                                    //test start
//                                    System.out.println(course.getCourseName());
//                                    System.out.println(course.getCourseCode());
//                                    System.out.println(course.getCourseTeacher());
//                                    System.out.println(course.getCourseRange());
//                                    System.out.println(course.getCourseClassroom());
//                                    System.out.println(course.getCourseTime());
//                                    System.out.println(course.getCourseWeek());
//                                    System.out.println(course.getCourseTerm());
//                                    System.out.println(course.getStudentId());
                                    //test end
                                }
                            }
                        }
                        listener.success(courses);
                    } else {
                        listener.failed();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getCoursesFromLocal(Context context, String studentId, String courseTerm) {
        List<Course> courses = new ArrayList<>();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(AppConfig.TABLE_COURSE, null, "studentId=? AND courseTerm=?", new String[]{studentId, courseTerm}, null, null, null);
        while (cursor.moveToNext()) {
            Course course = new Course();
            course.setCourseName(cursor.getString(cursor.getColumnIndex("courseName")));
            course.setCourseCode(cursor.getString(cursor.getColumnIndex("courseCode")));
            course.setCourseTeacher(cursor.getString(cursor.getColumnIndex("courseTeacher")));
            course.setCourseRange(cursor.getString(cursor.getColumnIndex("courseRange")));
            course.setCourseClassroom(cursor.getString(cursor.getColumnIndex("courseClassroom")));
            course.setCourseWeek(cursor.getString(cursor.getColumnIndex("courseWeek")));
            course.setCourseTime(cursor.getString(cursor.getColumnIndex("courseTime")));
            course.setCourseTerm(cursor.getString(cursor.getColumnIndex("courseTerm")));
            course.setStudentId(studentId);
            courses.add(course);
        }
        //debug
        AppUtil.LOG(this.getClass().getName(), "从本地获取课程信息成功");
        //close
        cursor.close();
        readableDatabase.close();
        if (courses.size() > 0)
            return courses;
        else
            return null;
    }

    @Override
    public void saveCoursesToLocal(Context context, List<Course> courses, String studentId) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();
        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(AppConfig.TABLE_COURSE, null, "studentId=? AND courseTerm=?", new String[]{studentId, courses.get(0).getCourseTerm()}, null, null, null);


        if (cursor.getCount() == courses.size()) {

        } else {
            writableDatabase.delete(AppConfig.TABLE_COURSE, "studentId=? AND courseTerm=?", new String[]{studentId, courses.get(0).getCourseTerm()});
            /*
        private String courseName;//课程名称
        private String courseCode;//课程代码
        private String courseTeacher;//负责老师
        private String courseRange;//上课时间范围
        private String courseClassroom;//课室
        private String courseWeek;//第几周
        private String courseTime;//时间
        private String courseTerm;//学期
        private String studentId;//学生学号
        */
            for (Course course : courses) {
                ContentValues values = new ContentValues();
                values.put("courseName", course.getCourseName());
                values.put("courseCode", course.getCourseCode());
                values.put("courseTeacher", course.getCourseTeacher());
                values.put("courseRange", course.getCourseRange());
                values.put("courseClassroom", course.getCourseClassroom());
                values.put("courseWeek", course.getCourseWeek());
                values.put("courseTime", course.getCourseTime());
                values.put("courseTerm", course.getCourseTerm());
                values.put("studentId", course.getStudentId());

                writableDatabase.insert(AppConfig.TABLE_COURSE, "courseName", values);
                AppUtil.LOG(this.getClass().getName(), "插入课程已完成");
            }
        }
        //close
        cursor.close();
        readableDatabase.close();
        writableDatabase.close();
    }

    @Override
    public int getCourseCountOfDay(Context context, String studentId,String courseTerm,String courseTime,String week) {
        int count = 0;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME, null, AppUtil.getVersionCode(context));
        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(AppConfig.TABLE_COURSE, null, "studentId=? AND courseTerm=? AND courseTime=?", new String[]{studentId, courseTerm,courseTime}, null, null, null);
        while (cursor.moveToNext()) {
            String courseRange = cursor.getString(cursor.getColumnIndex("courseRange"));
            if (courseRange != null) {
                if (DataUtil.isContainer(courseRange.split(" "), week))
                    count++;
            }
        }
        //debug
        AppUtil.LOG(this.getClass().getName(), "");
        //close
        cursor.close();
        readableDatabase.close();
        return count;
    }

    @Override
    public void getCurrentWeek(Context context,String username, String password) {
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
                    return;
                } else {
                    System.out.println("登陆成功");
                    String cookie = response.headers().values("Set-Cookie").toString().trim().split(";")[0].substring(1);

                    //获取考勤url所带的参数
                    Request requestPara = new Request.Builder()
                            .url("http://class.sise.com.cn:7001/sise/module/student_schedular/student_schedular.jsp")
                            .header("Cookie", cookie)
                            .header("Host", "class.sise.com.cn:7001")
                            .header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:45.0) Gecko/20100101 Firefox/45.0")
                            .build();
                    Response responsePara = OkHttpUtil.execute(requestPara);
                    if (responsePara.isSuccessful()) {
                        Document document = Jsoup.parse(responsePara.body().string());
                        Element table = document.getElementsByTag("table").get(5).getElementsByTag("td").get(1);
                        String substring = table.text().trim().split(":")[1];
                        String result = substring.substring(2,substring.length()-4);
                        DataUtil.saveSharedPreference(context,"currentWeek",result);
//                        System.out.println(result);
                        //debug
                        AppUtil.LOG(this.getClass().getName(), "current-Okay"+result);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

