package com.same.androidclass.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;
import com.atermenji.android.iconicdroid.icon.IconicIcon;
import com.same.androidclass.R;
import com.same.androidclass.util.Colors;
import com.same.androidclass.view.activity.MainActivity;
import com.same.androidclass.view.adapter.HomeFragmentAdapter;
import com.special.ResideMenu.ResideMenu;
import com.thuongnh.zprogresshud.ZProgressHUD;

public class HomeFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private View parentView;
    private ResideMenu resideMenu;
    private ViewPager viewPager;
    private LinearLayout menu_item,classes_item,attendance_item,exam_item,grade_item;
    private HomeFragmentAdapter adapter;
    private TextView classesText,attendanceText,examText,gradeText,menuText;
    private View classesView,attendanceView,examView,gradeView,menuView;
    private IconicFontDrawable classesIcon,attendanceIcon,examIcon,gradeIcon,menuIcon;
    private ZProgressHUD loading;//加载组件

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        //初始化组件
        initView();
        initIcon();
        return parentView;
    }

    public void initView() {
        MainActivity parentActivity = (MainActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        //textView 组件
        classesText = (TextView) parentView.findViewById(R.id.home_menu_item_classes_title);
        attendanceText = (TextView) parentView.findViewById(R.id.home_menu_item_attendance_title);
        examText = (TextView) parentView.findViewById(R.id.home_menu_item_exam_title);
        gradeText = (TextView) parentView.findViewById(R.id.home_menu_item_grade_title);
        menuText = (TextView) parentView.findViewById(R.id.home_menu_item_menu_title);

        classesView = parentView.findViewById(R.id.home_menu_item_classes_icon);
        attendanceView = parentView.findViewById(R.id.home_menu_item_attendance_icon);
        examView = parentView.findViewById(R.id.home_menu_item_exam_icon);
        gradeView = parentView.findViewById(R.id.home_menu_item_grade_icon);
        menuView = parentView.findViewById(R.id.home_menu_item_menu_icon);


        //LinearLayout
        classes_item = (LinearLayout) parentView.findViewById(R.id.home_menu_item_classes);
        attendance_item = (LinearLayout) parentView.findViewById(R.id.home_menu_item_attendance);
        exam_item = (LinearLayout) parentView.findViewById(R.id.home_menu_item_exam);
        grade_item = (LinearLayout) parentView.findViewById(R.id.home_menu_item_grade);
        //例外的处理右边滑动item
        menu_item = (LinearLayout) parentView.findViewById(R.id.home_menu_item_menu);

        //默认课表选中
        classesText.setSelected(true);
        //实例化viewPager
        viewPager = (ViewPager) parentView.findViewById(R.id.home_main_viewPager);


        adapter = new HomeFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        //单击的组件的事件监听
        classes_item.setOnClickListener(this);
        attendance_item.setOnClickListener(this);
        exam_item.setOnClickListener(this);
        grade_item.setOnClickListener(this);
        //底部菜单事件监听处理
        menu_item.setOnClickListener(this);


//         add gesture operation's ignored views
//        FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
//        resideMenu.addIgnoredView(ignored_view);
    }

    /**
     * 底部菜单icon初始化
     */
    public void initIcon(){
        classesIcon = new IconicFontDrawable(parentView.getContext());
        classesIcon.setIcon(IconicIcon.CALENDAR_INV);
        classesIcon.setIconColor(Colors.color_theme);

        attendanceIcon = new IconicFontDrawable(parentView.getContext());
        attendanceIcon.setIcon(FontAwesomeIcon.LIST_ALT);
        attendanceIcon.setIconColor(Color.GRAY);

        examIcon = new IconicFontDrawable(parentView.getContext());
        examIcon.setIcon(FontAwesomeIcon.CALENDAR);
        examIcon.setIconColor(Color.GRAY);

        gradeIcon = new IconicFontDrawable(parentView.getContext());
        gradeIcon.setIcon(EntypoIcon.PRINT);
        gradeIcon.setIconColor(Color.GRAY);

        menuIcon = new IconicFontDrawable(parentView.getContext());
        menuIcon.setIcon(FontAwesomeIcon.TH);
        menuIcon.setIconColor(Color.GRAY);



        classesView.setBackground(classesIcon);
        attendanceView.setBackground(attendanceIcon);
        examView.setBackground(examIcon);
        gradeView.setBackground(gradeIcon);
        menuView.setBackground(menuIcon);
    }

    //单击的组件的事件处理
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_menu_item_menu:
                menuHandler();
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                break;
            case R.id.home_menu_item_classes:
                classesHandler();
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.home_menu_item_attendance:
                attendanceHandler();
                viewPager.setCurrentItem(1,false);
                break;
            case R.id.home_menu_item_exam:
                examHandler();
                viewPager.setCurrentItem(2,false);
                break;
            case R.id.home_menu_item_grade:
                gradeHandler();
                viewPager.setCurrentItem(3,false);
                break;
            default:
                break;
        }
    }

    //pager滑动
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //pager已选中
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                classesHandler();
                break;
            case 1:
                attendanceHandler();
                break;
            case 2:
                examHandler();
                break;
            case 3:
                gradeHandler();
                break;
            default:
                break;
        }
    }

    //pager滑动并且已经选中
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //样式处理 item -- start
    public void classesHandler() {
        classesText.setSelected(true);
        attendanceText.setSelected(false);
        examText.setSelected(false);
        gradeText.setSelected(false);
        menuText.setSelected(false);

        classesIcon.setIconColor(Colors.color_theme);
        attendanceIcon.setIconColor(Color.GRAY);
        examIcon.setIconColor(Color.GRAY);
        gradeIcon.setIconColor(Color.GRAY);
        menuIcon.setIconColor(Color.GRAY);

    }

    public void attendanceHandler() {
        classesText.setSelected(false);
        attendanceText.setSelected(true);
        examText.setSelected(false);
        gradeText.setSelected(false);
        menuText.setSelected(false);

        classesIcon.setIconColor(Color.GRAY);
        attendanceIcon.setIconColor(Colors.color_theme);
        examIcon.setIconColor(Color.GRAY);
        gradeIcon.setIconColor(Color.GRAY);
        menuIcon.setIconColor(Color.GRAY);
    }

    public void examHandler() {
        classesText.setSelected(false);
        attendanceText.setSelected(false);
        examText.setSelected(true);
        gradeText.setSelected(false);
        menuText.setSelected(false);

        classesIcon.setIconColor(Color.GRAY);
        attendanceIcon.setIconColor(Color.GRAY);
        examIcon.setIconColor(Colors.color_theme);
        gradeIcon.setIconColor(Color.GRAY);
        menuIcon.setIconColor(Color.GRAY);
    }

    public void gradeHandler() {
        classesText.setSelected(false);
        attendanceText.setSelected(false);
        examText.setSelected(false);
        gradeText.setSelected(true);
        menuText.setSelected(false);

        classesIcon.setIconColor(Color.GRAY);
        attendanceIcon.setIconColor(Color.GRAY);
        examIcon.setIconColor(Color.GRAY);
        gradeIcon.setIconColor(Colors.color_theme);
        menuIcon.setIconColor(Color.GRAY);
    }

    public void menuHandler() {
        classesText.setSelected(false);
        attendanceText.setSelected(false);
        examText.setSelected(false);
        gradeText.setSelected(false);
        menuText.setSelected(true);

        classesIcon.setIconColor(Color.GRAY);
        attendanceIcon.setIconColor(Color.GRAY);
        examIcon.setIconColor(Color.GRAY);
        gradeIcon.setIconColor(Color.GRAY);
        menuIcon.setIconColor(Colors.color_theme);
    }
    //样式处理 item -- end

}
