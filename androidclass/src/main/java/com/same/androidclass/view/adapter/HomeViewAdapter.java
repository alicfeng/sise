package com.same.androidclass.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.same.androidclass.R;

import java.util.ArrayList;

/**
 * Home页面的适配器adapter
 * Created by alic on 16-4-30.
 */
public class HomeViewAdapter extends PagerAdapter {
    private ArrayList<View> viewContainers;
    private Context context;
    public HomeViewAdapter(Context context) {
        viewContainers = new ArrayList<>();

        //定义viewPager容器的子项
        View view_one = LayoutInflater.from(context).inflate(R.layout.home_viewpager_one, null);
        View view_two = LayoutInflater.from(context).inflate(R.layout.home_viewpager_two, null);
        View view_three = LayoutInflater.from(context).inflate(R.layout.home_viewpager_three, null);
        View view_four = LayoutInflater.from(context).inflate(R.layout.home_viewpager_four, null);

        //为viewPager容器添加子项
        viewContainers.add(view_one);
        viewContainers.add(view_two);
        viewContainers.add(view_three);
        viewContainers.add(view_four);
    }


    @Override
    public int getCount() {
        return viewContainers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewContainers.get(position));
        return viewContainers.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewContainers.get(position));
    }
}
