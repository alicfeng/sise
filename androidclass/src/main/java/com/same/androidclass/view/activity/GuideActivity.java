package com.same.androidclass.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.same.androidclass.R;
import com.same.androidclass.util.ActivityCollector;
import com.same.androidclass.util.StatusBarCompat;
import com.same.androidclass.view.adapter.GuideViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导界面
 * Created by alic on 16-5-12.
 */
public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private GuideViewPagerAdapter adapter;//presenter
    private ViewPager viewPager;//viewPager
    private List<View> views;//三张视图
    private ImageView[] points;
    private Button actionEnter;//进入按钮

    public final static String TAG = "view.activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_pager);
        StatusBarCompat.hideStatusBar(this, false);
        initView();

        //app工具类应用处理
        ActivityCollector.addActivity(this);
    }

    public void initView(){
        viewPager = (ViewPager) findViewById(R.id.guide_viewPager);
        //由于当前类的xml不是viewPager承载子元素的资源xml 需要LayoutInflater获取
        LayoutInflater inflater = LayoutInflater.from(this);
        //实例化view视图
        views = new ArrayList<>();
        views.add(inflater.inflate(R.layout.guide_viewpager_image_one,null));
        views.add(inflater.inflate(R.layout.guide_viewpager_image_two,null));
        views.add(inflater.inflate(R.layout.guide_viewpager_image_three,null));

        //实例化三个点
        points = new ImageView[views.size()];
        points[0] = ((ImageView) findViewById(R.id.guide_view_page_point_one));
        points[1] = ((ImageView) findViewById(R.id.guide_view_page_point_two));
        points[2] = ((ImageView) findViewById(R.id.guide_view_page_point_three));

        adapter = new GuideViewPagerAdapter(this,views);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        //实例化进入按钮
        actionEnter = (Button) views.get(2).findViewById(R.id.actionEnter);
        actionEnter.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e(TAG,"引导图片滑动了");
    }

    @Override
    public void onPageSelected(int position) {
        Log.e(TAG,"引导图片页面已经选中");
        for (int i = 0; i < views.size(); i++) {
            if (position==i)
                points[i].setImageResource(R.drawable.guide_view_pager_point_selected);
            else
                points[i].setImageResource(R.drawable.guide_view_pager_point);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.e(TAG,"引导图片滑动改变了");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actionEnter:
                Intent loginIntent = new Intent(this,StudentLoginActivity.class);
                startActivity(loginIntent);
                ActivityCollector.addActivity(this);
                finish();
                break;
            default:
                break;
        }
    }
}
