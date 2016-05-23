package com.same.androidclass.common;

import android.content.Context;

import com.same.androidclass.util.DataUtil;
import com.same.androidclass.util.DateUtil;
import com.same.androidclass.view.adapter.SimpleListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 应用程序配置信息
 * Created by alic on 16-5-6.
 */
public final class AppConfig {
    /**
     * 是否开启debug模式
     */
    public final static boolean DEBUG = true;
    /**
     * SharedPreferences中的user文件
     */
    public final static String SHAREPREFERENCES_NAME = "user";
    /**
     * 数据库名称 main
     */
    public final static String DATABASE_NAME = "myscse";

    /**
     * 学生表
     */
    public final static String TABLE_STUDENT = "student";
    /**
     * 课程表
     */
    public final static String TABLE_COURSE = "course";
    /**
     * 考勤表
     */
    public final static String TABLE_ATTENDANCE = "attendance";
    /**
     * 考试a安排表
     */
    public final static String TABLE_EXAM = "exam";
    /**
     * 成绩表
     */
    public final static String TABLE_GRADE = "grade";

    /**
     * 绩点相关表
     */
    public final static String TABLE_COURGE_MESSAGE = "courseMessage";
    /**
     * 当前学期
     */
    public final static String CURRENT_TERM = "2";
    /**
     * 学期周数数组
     */
    public static ArrayList<String> COURSE_WEEKS_OPTION() {
        ArrayList<String> datas = new ArrayList<>();
        datas.add("第一周");
        datas.add("第二周");
        datas.add("第三周");
        datas.add("第四周");
        datas.add("第五周");
        datas.add("第六周");
        datas.add("第七周");
        datas.add("第八周");
        datas.add("第九周");
        datas.add("第十周");
        datas.add("第十一周");
        datas.add("第十二周");
        datas.add("第十三周");
        datas.add("第十四周");
        datas.add("第十五周");
        datas.add("第十六周");
        return datas;
    }

    /**
     * 课程表的学年学期
     * @param context 上下文
     * @return 课程表的学年学期
     */
    public static ArrayList<String> COURSE_TREMS_OPTION(Context context) {
        ArrayList<String> datas = new ArrayList<>();
        int currentYear = Integer.parseInt(DateUtil.formatDate(new Date(), DateUtil.yyyy));
        int inYear = Integer.parseInt(DataUtil.readSharedPreference(context, "gradeYear", ""));
        //动态添加学期学年
        for (int i = 0; i < currentYear - inYear; i++) {
            datas.add(inYear + i + "-1");
            datas.add(inYear + i + "-2");
        }
        return datas;
    }

    /**
     * 成绩列表String
     * @return List<String>
     */
    public static List<String> GRADE_OPTION() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("查询学分详情");
        strings.add("查询选修成绩");
        strings.add("大一第一学期");
        strings.add("大一第二学期");
        strings.add("大二第一学期");
        strings.add("大二第二学期");
        strings.add("大三第一学期");
        strings.add("大三第二学期");
        strings.add("大四第一学期");
        strings.add("大四第二学期");
        return strings;
    }
}
