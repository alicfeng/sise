package com.same.androidclass.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.same.androidclass.R;
import com.same.androidclass.bean.Course;
import com.same.androidclass.common.AppConfig;
import com.same.androidclass.presenter.CoursePresenter;
import com.same.androidclass.util.ActivityCollector;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.util.DateUtil;
import com.same.androidclass.view.activity.CourseDetailActivity;
import com.same.androidclass.view.view.CourseView;
import com.thuongnh.zprogresshud.ZProgressHUD;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.LinkagePicker;

/**
 * 课程视图
 * Created by alic on 16-5-8.
 */
public class HomeClassesFragment extends Fragment implements CourseView, View.OnClickListener {
    private View viewCourse;
    private TableLayout table;
    private String schoolYear = String.valueOf(Integer.parseInt(DateUtil.formatDate(new Date(), DateUtil.yyyy)) - 1);//服务端_课程_学年_option
    private String semester = AppConfig.CURRENT_TERM;//服务端_课程_学期——option
    private String courseTerm="2015-2016学年 第二学期";//本地获取数据的学期option
    private String week ;//当前周数
    private CoursePresenter coursePresenter;
    private ZProgressHUD loading;//加载组件

    private FloatingActionButton floatingActionButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewCourse = inflater.inflate(R.layout.home_viewpager_one, container, false);
        initView();
        return viewCourse;
    }

    /**
     * 初始化视图
     */
    public void initView() {
        week= DataUtil.readSharedPreference(viewCourse.getContext(),"currentWeek","14");
        //FloatingActionButton
        floatingActionButton = (FloatingActionButton) viewCourse.findViewById(R.id.fab_classes);
        table = (TableLayout) viewCourse.findViewById(R.id.home_main_scroll_body);
        floatingActionButton.setOnClickListener(this);


        //loading
        loading = ZProgressHUD.getInstance(viewCourse.getContext());

        coursePresenter = new CoursePresenter(this, viewCourse.getContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                coursePresenter.doLoadCourses();
            }
        }).start();

    }


    //显示课程数据
    @Override
    public void displayCourses(final List<Course> courses) {

        System.out.println("我在加载   你选了了第" + week + "周");

        //遍历表格并填充
        int line = 0, row = 0;
        for (final Course course : courses) {
            //课程信息
            if (course.getCourseName() != null && DataUtil.isContainer(course.getCourseRange().split(" "), week)) {
                //获取要填充的表格
                final String content = DataUtil.subString(course.getCourseName(), 10, "...") + "\n" + course.getCourseClassroom();
                ((TextView) ((TableRow) table.getChildAt(line)).getChildAt(row + 1))
                        .setText(content);
                //设置其状态 为了改变颜色
                (((TableRow) table.getChildAt(line)).getChildAt(row + 1)).setSelected(true);

                //添加监听事件
                (((TableRow) table.getChildAt(line)).getChildAt(row + 1))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent detailIntent = new Intent(viewCourse.getContext(), CourseDetailActivity.class);
                                Bundle courseBundle = new Bundle();
                                courseBundle.putSerializable("detailCourse", course);
                                detailIntent.putExtras(courseBundle);
                                startActivity(detailIntent);
                                ActivityCollector.addActivity(getActivity());
                            }
                        });
            } else {
                //清空没课的text
                ((TextView) ((TableRow) table.getChildAt(line)).getChildAt(row + 1)).setText("");
                //设置其没课状态 为了改变颜色
                (((TableRow) table.getChildAt(line)).getChildAt(row + 1)).setSelected(false);
            }
            row++;
            if (row >= 5) {
                row = 0;
                line++;
            }
        }
    }


    @Override
    public String getSchoolYear() {
        return schoolYear;
    }

    @Override
    public String getSemester() {
        return semester;
    }

    @Override
    public String getCourseTerm() {
//        return "2015-2016学年 第二学期";
        return courseTerm;
    }

    @Override
    public void showLoading() {
        loading.show();
    }

    @Override
    public void hideLoading() {
        loading.dismissWithSuccess();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_classes:
//                new AlertView("课程周数", null, "", null, AppConfig.COURSE_WEEKS,
//                        viewCourse.getContext(), AlertView.Style.ActionSheet, this).show();
                onLinkagePicker();
                break;
            default:
                break;
        }
    }

    public void onLinkagePicker() {
        ArrayList<String> firstList = AppConfig.COURSE_TREMS_OPTION(viewCourse.getContext());

        ArrayList<ArrayList<String>> secondList = new ArrayList<ArrayList<String>>();
        ArrayList<String> secondListItem = AppConfig.COURSE_WEEKS_OPTION();
        secondList.add(secondListItem);//对应今天
        secondList.add(secondListItem);//对应今天
        secondList.add(secondListItem);//对应今天
        secondList.add(secondListItem);//对应今天
        secondList.add(secondListItem);//对应明天
        secondList.add(secondListItem);//对应明天
        secondList.add(secondListItem);//对应明天
        LinkagePicker picker = new LinkagePicker(getActivity(), firstList, secondList);
        picker.setSelectedItem(schoolYear+"-"+semester, AppConfig.COURSE_WEEKS_OPTION().get(Integer.parseInt(week)-1));
        picker.setOnLinkageListener(new LinkagePicker.OnLinkageListener() {

            @Override
            public void onPicked(String first, String second, String third) {
//                Toast.makeText(viewCourse.getContext(), first + "-" + second, Toast.LENGTH_SHORT).show();
                schoolYear = first.split("-")[0];
                semester = first.split("-")[1];

                if (semester.equals("1"))
                    courseTerm = schoolYear + "-" + (Integer.parseInt(schoolYear) + 1) + "学年 " + "第一学期";
                else
                    courseTerm = schoolYear + "-" + (Integer.parseInt(schoolYear) + 1) + "学年 " + "第二学期";

                switch (second) {
                    case "第一周":
                        week = "1";
                        break;
                    case "第二周":
                        week = "2";
                        break;
                    case "第三周":
                        week = "3";
                        break;
                    case "第四周":
                        week = "4";
                        break;
                    case "第五周":
                        week = "5";
                        break;
                    case "第六周":
                        week = "6";
                        break;
                    case "第七周":
                        week = "7";
                        break;
                    case "第八周":
                        week = "8";
                        break;
                    case "第九周":
                        week = "9";
                        break;
                    case "第十周":
                        week = "10";
                        break;
                    case "第十一周":
                        week = "11";
                        break;
                    case "第十二周":
                        week = "12";
                        break;
                    case "第十三周":
                        week = "13";
                        break;
                    case "第十四周":
                        week = "14";
                        break;
                    case "第十五周":
                        week = "15";
                        break;
                    case "第十六周":
                        week = "16";
                        break;
                    case "第十七周":
                        week = "17";
                        break;
                    default:
                        break;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        coursePresenter.doLoadCourses();
                    }
                }).start();

            }
        });
        picker.show();
    }


}
