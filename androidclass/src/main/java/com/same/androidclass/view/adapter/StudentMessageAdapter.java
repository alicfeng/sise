package com.same.androidclass.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.same.androidclass.R;
import com.same.androidclass.bean.Student;
import com.same.androidclass.bean.StudentMessageListCell;
import com.same.androidclass.model.StudentModelImpl;
import com.same.androidclass.util.DataUtil;

/**
 *Profile学生个人信息的适配器adapter
 * Created by alic on 16-4-30.
 */
public class StudentMessageAdapter extends BaseAdapter {
    private Context context;
    private StudentMessageListCell[] listCells;
    private Student student;
    private StudentModelImpl studentModel = new StudentModelImpl();

    //construct
    public StudentMessageAdapter(Context context, Student student) {
        this.context = context;
        this.student = student;
        listCells = new StudentMessageListCell[]{
                new StudentMessageListCell("学号",student.getStudentId()),
                new StudentMessageListCell("姓名",student.getUsername()),
                new StudentMessageListCell("年级",student.getGradeYear()),
                new StudentMessageListCell("专业",student.getProfession()),
                new StudentMessageListCell("身份证",student.getIdCard()),
                new StudentMessageListCell("电子邮箱",student.getEmail()),
                new StudentMessageListCell("行政班",student.getGradeAdmin()),
                new StudentMessageListCell("班主任",student.getHeadTeacher()),
                new StudentMessageListCell("辅导员",student.getAssistant()),
        };
    }

    public StudentMessageAdapter(Context context) {
        this.context = context;
        student = studentModel.getCurrentUser(context, DataUtil.readSharedPreference(context,"username",""));
        listCells = new StudentMessageListCell[]{
                new StudentMessageListCell("学号",student.getStudentId()),
                new StudentMessageListCell("姓名",student.getUsername()),
                new StudentMessageListCell("年级",student.getGradeYear()),
                new StudentMessageListCell("专业",student.getProfession()),
                new StudentMessageListCell("身份证",DataUtil.hiddenCode(student.getIdCard(), 6, 12, "*")),
                new StudentMessageListCell("电子邮箱",student.getEmail()),
                new StudentMessageListCell("行政班",student.getGradeAdmin()),
                new StudentMessageListCell("班主任",student.getHeadTeacher()),
                new StudentMessageListCell("辅导员",student.getAssistant()),
        };
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return listCells.length;
    }

    @Override
    public StudentMessageListCell getItem(int position) {
        return listCells[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //定义一个子项
        LinearLayout ll = null;
        if (convertView!=null){
            ll = (LinearLayout) convertView;
        }else {
            ll = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.profile_item_layout,null);
        }
        StudentMessageListCell data = getItem(position);


        TextView messageName = (TextView) ll.findViewById(R.id.student_message_lists_name);
        TextView messageValue = (TextView) ll.findViewById(R.id.student_message_lists_value);

//        icon.setImageResource(data.getIcon());
        messageName.setText(data.getMessageName());
        messageValue.setText(data.getMessageValue());
        return ll;
    }
}
