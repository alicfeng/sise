package com.same.androidclass.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.same.androidclass.R;
import com.same.androidclass.bean.Grade;
import com.same.androidclass.presenter.GradeDetailPresenter;
import com.same.androidclass.util.Colors;
import com.same.androidclass.view.adapter.GradeAdapter;
import com.same.androidclass.view.view.GradeDetailView;

import java.util.List;

/**
 *
 * Created by zjm on 2016/5/16.
 */
public class GradeDetailActivity extends Activity implements GradeDetailView, View.OnClickListener{
    private ExpandableListView expandableListView;
    private List<Grade> grades;
    private View gradeBackIcon;
    private TextView term,gradeTitle;
    private IconicFontDrawable back;
    private GradeAdapter gradeAdapter;
    private GradeDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_listview);

        initView();
    }


    private void initView(){
        presenter = new GradeDetailPresenter(this,this);
        presenter.doLoadGrades();
        //初始化组件
        expandableListView=(ExpandableListView)findViewById(R.id.grade_elv);
        term=(TextView)findViewById(R.id.school_year);
        gradeBackIcon=findViewById(R.id.grade_lv_back_icon);
        gradeTitle=(TextView)findViewById(R.id.grade_lv_title);

        back = new IconicFontDrawable(this, EntypoIcon.CHEVRON_THIN_LEFT);
        back.setIconColor(Colors.color_pure_white);
        gradeBackIcon.setBackground(back);
        gradeBackIcon.setOnClickListener(this);

        term.setText(grades.get(0).getSchoolYear());
        gradeAdapter = new GradeAdapter(GradeDetailActivity.this, grades);
        expandableListView.setAdapter(gradeAdapter);

        if(getIntent().getStringExtra("genre").equals("1")){
            gradeTitle.setText("必修课程成绩");
        }else{
            gradeTitle.setText("选修课程成绩");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.grade_lv_back_icon:
                onBackPressed();
                break;
        }
    }

    @Override
    public void getGrades(List<Grade> grades) {
        this.grades = grades;
    }

    @Override
    public String getTerm() {
        return getIntent().getStringExtra("term");
    }

    @Override
    public String getGenre() {
        return getIntent().getStringExtra("genre");
    }
}
