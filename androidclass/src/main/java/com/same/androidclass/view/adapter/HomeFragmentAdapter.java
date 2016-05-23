package com.same.androidclass.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.same.androidclass.view.fragment.HomeAttendanceFragment;
import com.same.androidclass.view.fragment.HomeClassesFragment;
import com.same.androidclass.view.fragment.HomeExamFragment;
import com.same.androidclass.view.fragment.HomeGradeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Home页面的适配器adapter
 * Created by alic on 16-4-30.
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragmentList = new ArrayList<>();

    //构造方法一 推荐
    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);

        this.fragmentList.add(new HomeClassesFragment());
        this.fragmentList.add(new HomeAttendanceFragment());
        this.fragmentList.add(new HomeExamFragment());
        this.fragmentList.add(new HomeGradeFragment());
    }

    //构造方法二
    public HomeFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    //注意 为了防止fragment销毁 切换再创建出现耗时卡顿
    //暂时修改解决
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
