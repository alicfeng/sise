package com.same.androidclass.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;
import com.atermenji.android.iconicdroid.icon.IconicIcon;
import com.same.androidclass.R;
import com.same.androidclass.bean.Exam;
import com.same.androidclass.bean.ExamDetailListCell;
import com.same.androidclass.util.Colors;
import com.same.androidclass.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by alic on 16-5-14.
 */
public class ExamDetailAdapter extends BaseAdapter {
    private Context context;
    private Exam exam;
    private List<ExamDetailListCell> datas;
    private IconicFontDrawable courseNameIcon,courseCodeIcon,examDateIcon,examWeekIcon,examTimeIcon,placeCodeIcon,
            placeNameIcon,placeSeatIcon,examStatusIcon;

//    课程代码	课程名称	考试日期 考试星期 	考试时间	考场编码	考场名称	考试座位	考试状态

    public ExamDetailAdapter(Context context,Exam exam) {
        this.exam = exam;
        this.context = context;
        //初始化icon
        initIcon();
        datas = new ArrayList<>();
        datas.add(0,new ExamDetailListCell(courseNameIcon, "课程名称", exam.getCourseName()));
        datas.add(1,new ExamDetailListCell(courseCodeIcon, "课程代码", exam.getCourseCode()));
        datas.add(2,new ExamDetailListCell(examDateIcon, "考试日期", exam.getExamDate()));
        datas.add(3,new ExamDetailListCell(examWeekIcon, "考试星期", DateUtil.getWeek(exam.getExamDate(),DateUtil.yyyyMMdd)));
        datas.add(4,new ExamDetailListCell(examTimeIcon, "考试时间", exam.getExamTime()));
        datas.add(5,new ExamDetailListCell(placeNameIcon, "考场名称", exam.getPlaceName()));
        datas.add(6,new ExamDetailListCell(placeCodeIcon, "考场编码", exam.getPlaceCode()));
        datas.add(7,new ExamDetailListCell(placeSeatIcon, "考试座位", exam.getExamSeat()+""));
        datas.add(8,new ExamDetailListCell(examStatusIcon, "考试状态", exam.getExamStatus()));
    }
    public void initIcon(){
        //课程代码
        courseNameIcon = new IconicFontDrawable(context, EntypoIcon.BOOK);
        courseNameIcon.setIconColor(Colors.color_theme);
        //课程名称
        courseCodeIcon = new IconicFontDrawable(context, IconicIcon.TAG);
        courseCodeIcon.setIconColor(Colors.color_theme);
        //考试日期
        examDateIcon = new IconicFontDrawable(context, FontAwesomeIcon.CALENDAR);
        examDateIcon.setIconColor(Colors.color_theme);
        //考试星期
        examWeekIcon = new IconicFontDrawable(context, FontAwesomeIcon.STAR_EMPTY);
        examWeekIcon.setIconColor(Colors.color_theme);
        //考试时间
        examTimeIcon = new IconicFontDrawable(context, FontAwesomeIcon.RSS);
        examTimeIcon.setIconColor(Colors.color_theme);
        //考场编码
        placeCodeIcon = new IconicFontDrawable(context, FontAwesomeIcon.FONT);
        placeCodeIcon.setIconColor(Colors.color_theme);
        //考场名称
        placeNameIcon = new IconicFontDrawable(context, EntypoIcon.MAP);
        placeNameIcon.setIconColor(Colors.color_theme);
        //考试座位
        placeSeatIcon = new IconicFontDrawable(context, EntypoIcon.CC_ND);
        placeSeatIcon.setIconColor(Colors.color_theme);
        //考试状态
        examStatusIcon = new IconicFontDrawable(context, EntypoIcon.CIRCLED_INFO);
        examStatusIcon.setIconColor(Colors.color_theme);


    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = LayoutInflater.from(context).inflate(R.layout.exam_detail_item_layout,null);
        //获取组件
        View icon = convertView.findViewById(R.id.exam_detail_icon);
        TextView name = (TextView) convertView.findViewById(R.id.exam_detail_name);
        TextView value = (TextView) convertView.findViewById(R.id.exam_detail_value);

        //获得一条数据
        ExamDetailListCell data = datas.get(position);

        //赋值数据
        icon.setBackground(data.getIconicFontDrawable());
        name.setText(data.getName());
        value.setText(data.getValue());

        return convertView;
    }
}
