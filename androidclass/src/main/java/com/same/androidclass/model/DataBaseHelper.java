package com.same.androidclass.model;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.same.androidclass.common.AppConfig;

/**
 * 数据库助手
 * Created by alic on 16-5-5.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "com.same.androidclass.model";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * 数据库第一次创建时被调用
     *
     * @param db 数据库
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建学生实体数据表
//        private String studentId;//学号
//        private String username;//姓名
//        private String gradeYear;//年级
//        private String profession;//专业
//        private String idCard;//身份证
//        private String email;//邮箱
//        private String gradeAdmin;//行政班
//        private String assistant;//辅导员
//        private String headTeacher;//班主任
        db.execSQL("CREATE TABLE " + AppConfig.TABLE_STUDENT + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "studentId VARCHAR(11)," +
                        "username VARCHAR(6)," +
                        "gradeYear VARCHAR(4)," +
                        "profession VARCHAR(20)," +
                        "idCard VARCHAR(18)," +
                        "email VARCHAR(20)," +
                        "gradeAdmin VARCHAR(25)," +
                        "assistant VARCHAR(6)," +
                        "headTeacher VARCHAR(6)" +
                        ")"
        );
        System.out.println("你已经创建了学生表格");


        //课程表
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
        db.execSQL("CREATE TABLE " + AppConfig.TABLE_COURSE + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "courseName VARCHAR(25)," +
                        "courseCode VARCHAR(8)," +
                        "courseTeacher VARCHAR(6)," +
                        "courseRange VARCHAR(40)," +
                        "courseClassroom VARCHAR(5)," +
                        "courseWeek VARCHAR(1)," +
                        "courseTime VARCHAR(1)," +
                        "courseTerm VARCHAR(16)," +
                        "studentId VARCHAR(11)" +
                        ")"
        );
        System.out.println("你已经创建了学生表格");

        //考勤表
        /*
            private String classCode;//课程代码
            private String className;//课程名称
            private String classStatus;//考勤状态
            private String absenceNum;//学期的旷课节数
            private String studentId;//学生学号
            private String term;//学期
         */
        db.execSQL("CREATE TABLE " + AppConfig.TABLE_ATTENDANCE + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "classCode VARCHAR(8)," +
                        "className VARCHAR(25)," +
                        "classStatus VARCHAR(5)," +
                        "absenceNum VARCHAR(3)," +
                        "studentId VARCHAR(11)," +
                        "term VARCHAR(5)" +
                        ")"
        );
        System.out.println("你已经创建了学生考勤表");

//        private String studentId;//学生学号
//        private String courseCode;//课程代码
//        private String courseName;//课程名称
//        private String examDate;//考试日期
//        private String examTime;//考试时间
//        private String placeCode;//考场代码
//        private String placeName;//考场名称
//        private int examSeat;//考试座位
//        private String examStatus;//考试状态
        db.execSQL("CREATE TABLE " + AppConfig.TABLE_EXAM + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "studentId VARCHAR(11)," +
                        "courseCode VARCHAR(8)," +
                        "courseName VARCHAR(25)," +
                        "examDate VARCHAR(10)," +
                        "examTime VARCHAR(17)," +
                        "placeCode VARCHAR(4)," +
                        "placeName VARCHAR(4)," +
                        "examSeat INT," +
                        "examStatus VARCHAR(4)" +
                        ")"
        );
        System.out.println("考试时间表建立啦");

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
        db.execSQL("CREATE TABLE " + AppConfig.TABLE_GRADE + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "studentId VARCHAR(11)," +
                        "term VARCHAR(9)," +
                        "courseCode VARCHAR(10)," +
                        "courseName VARCHAR(25)," +
                        "credit FLOAT," +
                        "method VARCHAR(2)," +
                        "schoolYear VARCHAR(10)," +
                        "grade VARCHAR(4)," +
                        "credited FLOAT," +
                        "genre INT" +
                        ")"
        );
        System.out.println("考成绩表建立啦");

//        private String studentId;
//        private float obligatoryGradeAll;//必修总学分
//        private float obligatoryGraded;//必修已获得学分
//        private float optionGradeAll;//选修总学分
//        private float optionGraded;//选修已获得学分
//        private float graded;//已修学分
//        private float grading;//在读课程学分
//        private float optionCourseGrade;//已选课程学分
//        private float expectedGrade;//预期获得学分
//        private float averagePoint;//平均学分绩点
//        private float gradeAll;//本专业本年级毕业需修满学分


        db.execSQL("CREATE TABLE " + AppConfig.TABLE_COURGE_MESSAGE + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "studentId VARCHAR(11)," +
                        "obligatoryGradeAll FLOAT," +
                        "obligatoryGraded FLOAT," +
                        "optionGradeAll FLOAT," +
                        "optionGraded FLOAT," +
                        "graded FLOAT," +
                        "grading FLOAT," +
                        "optionCourseGrade FLOAT," +
                        "expectedGrade FLOAT," +
                        "averagePoint FLOAT," +
                        "gradeAll FLOAT" +
                        ")"
        );
        System.out.println("学分绩表建立啦");
    }

    /**
     * 软件版本号发生改变时调用
     *
     * @param db         数据库
     * @param oldVersion 旧版本
     * @param newVersion 新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("应用版本已经修改了");
    }

    /**
     * 关闭数据库1
     * @param writeDatabase writeDatabase
     * @param readDatabase readDatabase
     * @param cursor cursor
     */
    public static void closeDatabase(SQLiteDatabase writeDatabase, SQLiteDatabase readDatabase, Cursor cursor) {
        if (cursor != null)
            cursor.close();
        if (writeDatabase != null)
            writeDatabase.close();
        if (readDatabase != null)
            readDatabase.close();
    }
    /**
     * 关闭数据库2
     * @param writeOrReadDatabase writeOrReadDatabase
     * @param cursor cursor
     */
    public static void closeDatabase(SQLiteDatabase writeOrReadDatabase, Cursor cursor) {
        if (cursor != null)
            cursor.close();
        if (writeOrReadDatabase != null)
            writeOrReadDatabase.close();
    }
    /**
     * 关闭数据库2
     * @param writeOrReadDatabase writeOrReadDatabase
     */
    public static void closeDatabase(SQLiteDatabase writeOrReadDatabase) {
        if (writeOrReadDatabase != null)
            writeOrReadDatabase.close();
    }
}
