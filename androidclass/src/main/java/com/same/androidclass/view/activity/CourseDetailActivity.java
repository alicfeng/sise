package com.same.androidclass.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.same.androidclass.R;
import com.same.androidclass.bean.Course;
import com.same.androidclass.presenter.CourseDetailPresenter;
import com.same.androidclass.util.ActivityCollector;
import com.same.androidclass.util.Colors;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.view.view.CourseDetailView;

/**
 * 课程具体信息界面
 * Created by alic on 16-5-12.
 */
public class CourseDetailActivity extends AppCompatActivity implements CourseDetailView,View.OnClickListener{
    private CourseDetailPresenter presenter;
    private View backIcon,course_name, course_room, course_teacher, course_num, course_week, course_time;
    private TextView toolbarTitle,show_name, show_room, show_teacher, show_num, show_week, show_time;
    private IconicFontDrawable back,courseName, courseRoom, courseTeacher, courseNum, courseWeek, courseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.course_detail_toolbar);
        toolbar.setTitle(getCourseData().getCourseName());
//        toolbar.setLogo();
        setSupportActionBar(toolbar);
        initView();
        initIcon();
        //app工具类应用处理
        ActivityCollector.addActivity(this);
    }

    //界面初始化
    public void initView() {
        backIcon=findViewById(R.id.course_toolbar_icon);
        course_name = findViewById(R.id.course_name);
        course_room = findViewById(R.id.course_room);
        course_teacher = findViewById(R.id.course_teacher);
        course_num = findViewById(R.id.course_num);
        course_week = findViewById(R.id.course_week);
        course_time = findViewById(R.id.course_time);

        toolbarTitle=(TextView)findViewById(R.id.course_toolbar_title);
        show_name = (TextView) findViewById(R.id.show_name);
        show_room = (TextView) findViewById(R.id.show_room);
        show_teacher = (TextView) findViewById(R.id.show_teacher);
        show_num = (TextView) findViewById(R.id.show_num);
        show_week = (TextView) findViewById(R.id.show_week);
        show_time = (TextView) findViewById(R.id.show_time);

        backIcon.setOnClickListener(this);

        //实例化助手
        presenter = new CourseDetailPresenter(this, this);
        //控制显示数据
        presenter.doDisplayCourseDetail();
    }

    //加载图标
    public void initIcon() {
        back = new IconicFontDrawable(this, EntypoIcon.CHEVRON_THIN_LEFT);
        back.setIconColor(Colors.color_pure_white);


        courseName = new IconicFontDrawable(CourseDetailActivity.this);
        courseName.setIcon(EntypoIcon.VCARD);
        courseName.setIconColor(Colors.color_theme);

        courseRoom = new IconicFontDrawable(CourseDetailActivity.this);
        courseRoom.setIcon(EntypoIcon.LOCATION);
        courseRoom.setIconColor(Colors.color_theme);

        courseTeacher = new IconicFontDrawable(CourseDetailActivity.this);
        courseTeacher.setIcon(EntypoIcon.USER);
        courseTeacher.setIconColor(Colors.color_theme);

        courseNum = new IconicFontDrawable(CourseDetailActivity.this);
        courseNum.setIcon(EntypoIcon.TEXT_DOC);
        courseNum.setIconColor(Colors.color_theme);

        courseWeek = new IconicFontDrawable(CourseDetailActivity.this);
        courseWeek.setIcon(EntypoIcon.CALENDAR);
        courseWeek.setIconColor(Colors.color_theme);

        courseTime = new IconicFontDrawable(CourseDetailActivity.this);
        courseTime.setIcon(EntypoIcon.CLOCK);
        courseTime.setIconColor(Colors.color_theme);

        backIcon.setBackground(back);
        course_name.setBackground(courseName);
        course_room.setBackground(courseRoom);
        course_teacher.setBackground(courseTeacher);
        course_num.setBackground(courseNum);
        course_week.setBackground(courseWeek);
        course_time.setBackground(courseTime);
    }


    //获取传过来课程
    @Override
    public Course getCourseData() {
        return (Course) (getIntent().getSerializableExtra("detailCourse"));
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //显示课程信息
    @Override
    public void displayCourse(Course course) {
        String s = course.getCourseRange().split(" ")[0] + "-"
                + course.getCourseRange().split(" ")[course.getCourseRange().split(" ").length - 1];
        System.out.println("详细的课程信息:" + course.getCourseName());

        toolbarTitle.setText(DataUtil.subString(course.getCourseName(),10,"..."));
        show_name.setText(course.getCourseName() + "[" + course.getCourseCode() + "]");
        show_room.setText(course.getCourseClassroom());
        show_teacher.setText(course.getCourseTeacher());
        show_num.setText(courseSelect(course.getCourseWeek()));
        show_week.setText(s + "周");
        show_time.setText(timeSelect(course.getCourseWeek()));
    }

    //时间匹配
    public String timeSelect(String time) {
        String selectTime;
        switch (time) {
            case "1":
                selectTime = "09:00~10.20";
                break;
            case "2":
                selectTime = "10:40~12:00";
                break;
            case "3":
                selectTime = "12.30~13.50";
                break;
            case "4":
                if (getCourseData().getCourseTime() == "5")
                    selectTime = "13:20~14:40";
                else
                    selectTime = "14:00~15:20";
                break;
            case "5":
                if (getCourseData().getCourseTime() == "5")
                    selectTime = "14:50~16:10";
                else
                    selectTime = "15:30~16:50";
                break;
            case "6":
                selectTime = "17:00~18:20";
                break;
            case "7":
                selectTime = "19:00~20:20";
                break;
            default:
                selectTime = "20:30~21:50";
                break;
        }
        return selectTime;
    }

    //节数匹配
    public String courseSelect(String course){
        String courseSelect;
        switch (course){
            case "1":
                courseSelect="1-2节";
                break;
            case "2":
                courseSelect="3-4节";
                break;
            case "3":
                courseSelect="5-6节";
                break;
            case "4":
                courseSelect="7-8节";
                break;
            case "5":
                courseSelect="9-10节";
                break;
            case "6":
                courseSelect="11-12节";
                break;
            case "7":
                courseSelect="13-14节";
                break;
            default:
                courseSelect="15-16节";
                break;

        }
        return courseSelect;
    }


}
