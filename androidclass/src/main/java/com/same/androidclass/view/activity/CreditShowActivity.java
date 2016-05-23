package com.same.androidclass.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.same.androidclass.R;
import com.same.androidclass.bean.Credit;
import com.same.androidclass.bean.Grade;
import com.same.androidclass.presenter.CreditPresenter;
import com.same.androidclass.util.Colors;
import com.same.androidclass.view.view.CreditShowView;

import java.util.List;

/**
 *
 * Created by zjm on 2016/5/17.
 */
public class CreditShowActivity extends Activity implements CreditShowView, View.OnClickListener{
    private View creditShowBackIcon;
    private TextView obligatoryGradeAll,obligatoryGraded,optionGradeAll, optionGraded,
            graded,grading,optionCourseGrade,expectedGrade,averagePoint,gradeAll;

    private IconicFontDrawable back;
    private CreditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_layout);
        init();
    }

    public void init(){
        presenter = new CreditPresenter(this,this);

        //icon-back
        creditShowBackIcon=findViewById(R.id.credit_back_icon);
        back = new IconicFontDrawable(this, EntypoIcon.CHEVRON_THIN_LEFT);
        back.setIconColor(Colors.color_pure_white);
        creditShowBackIcon.setBackground(back);
        creditShowBackIcon.setOnClickListener(this);

        //组件初始化
        obligatoryGradeAll = (TextView) findViewById(R.id.credit_obligatoryGradeAll);
        obligatoryGraded = (TextView) findViewById(R.id.credit_obligatoryGraded);
        optionGradeAll = (TextView) findViewById(R.id.credit_optionGradeAll);
        optionGraded = (TextView) findViewById(R.id.credit_optionGraded);
        graded = (TextView) findViewById(R.id.credit_graded);
        grading = (TextView) findViewById(R.id.credit_grading);
        optionCourseGrade = (TextView) findViewById(R.id.credit_optionCourseGrade);
        expectedGrade = (TextView) findViewById(R.id.credit_expectedGrade);
        averagePoint = (TextView) findViewById(R.id.credit_averagePoint);
        gradeAll = (TextView) findViewById(R.id.credit_gradeAll);

        //在做里做主持的事
        presenter.doShowCreditMessage();
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
    public void showCredit(Credit credit) {
        obligatoryGradeAll.setText(String.valueOf(credit.getObligatoryGradeAll()));
        obligatoryGraded.setText(String.valueOf(credit.getObligatoryGraded()));
        optionGradeAll.setText(String.valueOf(credit.getOptionGradeAll()));
        optionGraded.setText(String.valueOf(credit.getOptionGraded()));
        graded.setText(String.valueOf(credit.getGraded()));
        grading.setText(String.valueOf(credit.getGrading()));
        optionCourseGrade.setText(String.valueOf(credit.getOptionCourseGrade()));
        expectedGrade.setText(String.valueOf(credit.getExpectedGrade()));
        averagePoint.setText(String.valueOf(credit.getAveragePoint()));
        gradeAll.setText(String.valueOf(credit.getGradeAll()));
    }
}
