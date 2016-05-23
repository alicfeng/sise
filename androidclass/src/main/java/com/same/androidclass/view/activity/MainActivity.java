package com.same.androidclass.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.same.androidclass.R;
import com.same.androidclass.common.AppConfig;
import com.same.androidclass.service.CoreService;
import com.same.androidclass.util.ActivityCollector;
import com.same.androidclass.util.AppUtil;
import com.same.androidclass.view.fragment.HomeFragment;
import com.same.androidclass.view.fragment.ProfileFragment;
import com.same.androidclass.view.fragment.SettingsFragment;
import com.same.androidclass.view.fragment.campusFragment;
import com.same.androidclass.view.view.MainActivityView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

/**
 * 程序的主界面
 * Created by alic on 16-4-30.
 */
public class MainActivity extends AppCompatActivity implements MainActivityView, View.OnClickListener {

    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;
    /*item-fragment*/
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private campusFragment calenderFragment;
    private SettingsFragment settingFragment;
    private Fragment currentFragment = new Fragment();
    private boolean isHome = true;//判断是否在主页面
    private long lastClickTime = 0;//点击返回键当前的时间戳


    /**
     * **在某些场景下，手势滑动开启/关闭菜单可能与您的某些控件产生冲突
     * 例如viewpager，这时您可以把viewpager添加到ignored view.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置toolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//        toolbar.setTitle("MyScse");
        //setSupportActionBar必须现设 bug setTitle例外
        setSupportActionBar(toolbar);
        //初始化菜单界面
        initMenuActivity();

        //设置默认显示为主页
        if (savedInstanceState == null)
            switchFragment(homeFragment, true);
        //app工具类应用处理
        ActivityCollector.addActivity(this);
    }

    /**
     * 初始化菜单
     */
    private void initMenuActivity() {
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        //禁止手势操作某个菜单方向
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
        //是否3D效果
        resideMenu.setUse3D(false);
        //背景图片
        resideMenu.setBackground(R.drawable.menu_background);
        //必设属性
        resideMenu.attachToActivity(this);
        //设置菜单的监听事件
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        //主页面滑动后占屏幕的百分比 0.0f-1.0f
        resideMenu.setScaleValue(0.65f);

        //创建菜单子项
        itemHome = new ResideMenuItem(this, R.drawable.icon_home, "MyScse");
        itemProfile = new ResideMenuItem(this, R.drawable.icon_profile, "个人信息");
        itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar, "校园服务");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "设置");

        //菜单子项的点击事件
        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        //为菜单添加子项 参数(item,Direction)子项 方向ResideMenu.DIRECTION_RIGHT
        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);

        //实例化fragment
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        calenderFragment = new campusFragment();
        settingFragment = new SettingsFragment();
    }

    //如果您需要使用手势滑动开启|关闭菜单，请复写activity的dispatchTouchEvent()
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {
        //菜单子项的事件处理
        if (view == itemHome)
            switchFragment(homeFragment, true);
        else if (view == itemProfile)
            switchFragment(profileFragment, false);
        else if (view == itemCalendar)
            switchFragment(calenderFragment, false);
        else if (view == itemSettings)
            switchFragment(settingFragment, false);
        //关闭主页界面
        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        //菜单打开时回调函数
        @Override
        public void openMenu() {
//            Toast.makeText(mContext, "打开菜单啦", Toast.LENGTH_SHORT).show();
        }

        //菜单关闭时回调函数
        @Override
        public void closeMenu() {
//            Toast.makeText(mContext, "关闭菜单啦", Toast.LENGTH_SHORT).show();
        }
    };

    private void switchFragment(Fragment targetFragment, boolean isHome) {
        this.isHome = isHome;

        resideMenu.clearIgnoredViewList();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.main_fragment, targetFragment)
                    .commit();
            AppUtil.LOG(this.getClass().getName(),"还没添加呢");
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
            AppUtil.LOG(this.getClass().getName(), "添加了( ⊙o⊙ )哇");
        }
        currentFragment = targetFragment;
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu() {
        return resideMenu;
    }


    @Override
    public void onBackPressed() {
        //退出应用程序处理
        if (isHome) {
            if (lastClickTime <= 0) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                lastClickTime = System.currentTimeMillis();
            } else {
                long currentClickTime = System.currentTimeMillis();
                if (currentClickTime - lastClickTime < 1000) {
                    ActivityCollector.AppExit(this);
                } else {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    lastClickTime = currentClickTime;
                }
            }
        } else {
            switchFragment(homeFragment, true);
        }
    }
}
