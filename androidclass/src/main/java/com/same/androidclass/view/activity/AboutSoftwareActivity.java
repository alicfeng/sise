package com.same.androidclass.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.same.androidclass.R;
import com.same.androidclass.util.AppUtil;
import com.same.androidclass.util.Colors;

/**
 * 关于软件界面
 * Created by alic on 16-5-16.
 */
public class AboutSoftwareActivity extends Activity implements View.OnClickListener {
    private TextView versionName;
    private LinearLayout ideaHandler,updateHandler;
    private View backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_software);

        initView();
    }

    public void initView() {
        //处理返回icon
        backIcon = findViewById(R.id.about_software_back_icon);
        IconicFontDrawable back = new IconicFontDrawable(this, EntypoIcon.CHEVRON_THIN_LEFT);
        back.setIconColor(Colors.color_pure_white);
        backIcon.setBackground(back);
        backIcon.setOnClickListener(this);

        //版本
        versionName = (TextView) findViewById(R.id.about_app_version_name);
        versionName.setText(AppUtil.getVersionName(this));

        //建议
        ideaHandler = (LinearLayout) findViewById(R.id.about_idea);
        ideaHandler.setOnClickListener(this);

        //更新
        updateHandler = (LinearLayout) findViewById(R.id.about_update);
        updateHandler.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_idea:
                Toast.makeText(this, "此功能正在完善", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_software_back_icon:
                onBackPressed();
                break;
            case R.id.about_update:
                Toast.makeText(this,"已经是最新版本啦",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
