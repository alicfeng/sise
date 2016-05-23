package com.same.androidclass.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.same.androidclass.bean.Student;
import com.same.androidclass.common.AppConfig;
import com.same.androidclass.common.SchoolConfig;
import com.same.androidclass.model.listener.OnLoginListener;
import com.same.androidclass.util.AppUtil;
import com.same.androidclass.util.OkHttpUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Created by alic on 16-4-28.
 */
public class StudentModelImpl implements StudentModel {

    @Override
    public void login(String username, String password, OnLoginListener onLoginListener) {

        Log.e("login","here");
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
                    System.out.println("用户名或密码错误");
                    onLoginListener.loginFailed();
                } else {
                    System.out.println("登陆成功");
                    String cookie =  response.headers().values("Set-Cookie").toString().trim().split(";")[0].substring(1);
                    onLoginListener.loginSuccess(cookie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存或更新用户的信息
     * @param context 上下文
     * @param student 学生实体
     */
    @Override
    public void saveStudent(Context context,Student student) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME,null, AppUtil.getVersionCode(context));
        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();
        SQLiteDatabase writableDatabase = dataBaseHelper.getWritableDatabase();
        //参数1：表名
        //参数2：要想显示的列
        //参数3：where子句
        //参数4：where子句对应的条件值
        //参数5：分组方式
        //参数6：having条件
        //参数7：排序方式
        Cursor cursor = readableDatabase.query(AppConfig.TABLE_STUDENT, null, "studentId=?", new String[]{student.getStudentId()}, null, null, null, null);
//        System.out.println(cursor.getCount()+"-------------------");
        if (cursor.getCount()>0){
            //更新数据
            //生成ContentValues对象，key:列名  value:想插入的值
            ContentValues values = new ContentValues();
            values.put("username", student.getUsername());
            values.put("gradeYear", student.getGradeYear());
            values.put("profession", student.getProfession());
            values.put("idCard", student.getIdCard());
            values.put("email", student.getEmail());
            values.put("gradeAdmin", student.getGradeAdmin());
            values.put("assistant", student.getAssistant());
            values.put("headTeacher", student.getHeadTeacher());

            writableDatabase.update(AppConfig.TABLE_STUDENT, values, "studentId=?", new String[]{student.getStudentId()});
            //debug
            AppUtil.LOG(this.getClass().getName(), "更新了一条数据--用户信息");

        }else {
            //写入数据
            //生成ContentValues对象，key:列名  value:想插入的值
            ContentValues values = new ContentValues();
            values.put("studentId", student.getStudentId());
            values.put("username", student.getUsername());
            values.put("gradeYear", student.getGradeYear());
            values.put("profession", student.getProfession());
            values.put("idCard", student.getIdCard());
            values.put("email", student.getEmail());
            values.put("gradeAdmin", student.getGradeAdmin());
            values.put("assistant", student.getAssistant());
            values.put("headTeacher", student.getHeadTeacher());

            writableDatabase.insert(AppConfig.TABLE_STUDENT, null, values);
            //debug
            AppUtil.LOG(this.getClass().getName(), "写进一条数据--用户信息");
        }
        cursor.close();
        readableDatabase.close();
        writableDatabase.close();
    }

    /**
     * 获得当前登陆的学生信息
     * @param context 上下文
     * @param studentId 学生的学号
     * @return 学生实体
     */
    @Override
    public Student getCurrentUser(Context context,String studentId){
        Student student = new Student();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, AppConfig.DATABASE_NAME,null,AppUtil.getVersionCode(context));
        SQLiteDatabase readableDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(AppConfig.TABLE_STUDENT, null, "studentId=?", new String[]{studentId}, null, null, null);

        while (cursor.moveToNext()){
            student.setStudentId(studentId);
            student.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            student.setGradeYear(cursor.getString(cursor.getColumnIndex("gradeYear")));
            student.setProfession(cursor.getString(cursor.getColumnIndex("profession")));
            student.setIdCard(cursor.getString(cursor.getColumnIndex("idCard")));
            student.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            student.setGradeAdmin(cursor.getString(cursor.getColumnIndex("gradeAdmin")));
            student.setAssistant(cursor.getString(cursor.getColumnIndex("assistant")));
            student.setHeadTeacher(cursor.getString(cursor.getColumnIndex("headTeacher")));

            //debug
            AppUtil.LOG(this.getClass().getName(), "获得了当前学生的信息");
        }

        cursor.close();
        readableDatabase.close();
        dataBaseHelper.close();
        return student;
    }
}
