package com.same.androidclass.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.same.androidclass.R;
import com.same.androidclass.bean.Grade;
import com.same.androidclass.util.DataUtil;

import java.util.List;

/**
 * Created by zjm on 2016/5/17.
 */
public class GradeAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Grade> grades;

    private TextView course_name, grade, name, code, credit, course_grade;

    public GradeAdapter(Context context, List<Grade> grades) {
        this.context = context;
        this.grades = grades;

    }

    @Override
    public int getGroupCount() {
        return grades.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(context, R.layout.grade_listview_detial, null);

        course_name = (TextView) convertView.findViewById(R.id.course_name);
        grade = (TextView) convertView.findViewById(R.id.grade);
        course_name.setText(DataUtil.subString(grades.get(groupPosition).getCourseName(), 10, "..."));
        grade.setText(grades.get(groupPosition).getGrade());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.grade_listview_child_detail, null);

        name = (TextView) convertView.findViewById(R.id.course_name);
        code = (TextView) convertView.findViewById(R.id.courde_code);
        credit = (TextView) convertView.findViewById(R.id.course_credit);
        course_grade = (TextView) convertView.findViewById(R.id.course_grade);

        name.setText("课程名称: " + grades.get(groupPosition).getCourseName());
        credit.setText("学分: " + grades.get(groupPosition).getCredit() + "");
        code.setText("课程代码: " + grades.get(groupPosition).getCourseCode());
        course_grade.setText("成绩: " + grades.get(groupPosition).getGrade());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
