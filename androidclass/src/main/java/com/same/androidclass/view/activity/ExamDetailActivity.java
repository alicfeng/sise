package com.same.androidclass.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;
import com.same.androidclass.R;
import com.same.androidclass.bean.Exam;
import com.same.androidclass.presenter.ExamDetailPresenter;
import com.same.androidclass.util.Colors;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.view.adapter.ExamDetailAdapter;
import com.same.androidclass.view.view.ExamDetailView;

import java.util.List;

/**
 * 考试具体详细界面
 * Created by alic on 16-5-13.
 */
public class ExamDetailActivity extends Activity implements ExamDetailView, View.OnClickListener {
    private View backIcon;
    private TextView toolbarName;
    private ListView listView;
    private ExamDetailAdapter adapter;
    private Exam exam;
    private ExamDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);
        initView();
    }
    public void initView(){
        presenter = new ExamDetailPresenter(this,this);
        presenter.doGetExam();
        //处理返回icon
        backIcon = findViewById(R.id.exam_detail_back_icon);
        IconicFontDrawable back = new IconicFontDrawable(this, EntypoIcon.CHEVRON_THIN_LEFT);
        back.setIconColor(Colors.color_pure_white);
        backIcon.setBackground(back);
        backIcon.setOnClickListener(this);
        //顶部标题处理 课程名称
        toolbarName = (TextView) findViewById(R.id.exam_detail_toolbar_name);
        toolbarName.setText(DataUtil.subString(exam.getCourseName(),15,"..."));
        //处理ListView
        listView = (ListView) findViewById(R.id.exam_detail_listView);
        adapter = new ExamDetailAdapter(this,exam);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public void getIntentExam() {
        exam =  (Exam) getIntent().getSerializableExtra("exam");
    }
}
