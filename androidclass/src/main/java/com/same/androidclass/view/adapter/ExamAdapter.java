package com.same.androidclass.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.same.androidclass.R;
import com.same.androidclass.bean.Exam;
import com.same.androidclass.util.DataUtil;

import java.util.List;

/**
 * 考试概览适配器
 * Created by alic on 16-5-13.
 */
public class ExamAdapter extends BaseAdapter {
    private Context context;
    private List<Exam> exams;

    public ExamAdapter(Context context, List<Exam> exams) {
        this.context = context;
        this.exams = exams;
    }

    @Override
    public int getCount() {
        return exams.size();
    }

    @Override
    public Object getItem(int position) {
        return exams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = LayoutInflater.from(context).inflate(R.layout.home_exam_listview_item, null);
        Exam exam = exams.get(position);

        TextView examName = (TextView) convertView.findViewById(R.id.exam_name_item);
        TextView examTime = (TextView) convertView.findViewById(R.id.exam_time_item);

        examName.setText(DataUtil.subString(exam.getCourseName(),13,"..."));
        examTime.setText(exam.getExamDate());

        return convertView;
    }
}
