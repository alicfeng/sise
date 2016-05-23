package com.same.androidclass.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.same.androidclass.R;
import com.same.androidclass.presenter.SettingPresenter;
import com.same.androidclass.util.ActivityCollector;
import com.same.androidclass.view.activity.AboutSoftwareActivity;
import com.same.androidclass.view.activity.CourseRemindActivity;
import com.same.androidclass.view.view.SettingView;
import com.same.androidclass.view.activity.StudentLoginActivity;
import com.same.androidclass.view.adapter.SettingBaseAdapter;


public class SettingsFragment extends Fragment implements SettingView, AdapterView.OnItemClickListener {
    private View settingView;
    private ListView settingListView;
    private SettingBaseAdapter adapter;
    private AlertDialog logoutAlert;
    private AlertDialog.Builder logoutBuilder;
    private Context context;
    private SettingPresenter settingPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingView= inflater.inflate(R.layout.settings, container, false);
        //初始化组件
        initView(settingView);
        return settingView;
    }

    /**
     * 初始化视图组件
     * @param parentView 父级视图
     */
    public void initView(View parentView){
        this.context = parentView.getContext();
        settingPresenter = new SettingPresenter(this);
        settingListView = (ListView) parentView.findViewById(R.id.setting_items);
        adapter = new SettingBaseAdapter(parentView.getContext());
        settingListView.setAdapter(adapter);
        settingListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (adapter.getSelectedItemName(position)){
            case "提醒设置":
                Intent remindIntent = new Intent(settingView.getContext(), CourseRemindActivity.class);
                startActivity(remindIntent);
                ActivityCollector.addActivity(getActivity());
                break;
            case "关于软件":
                Intent aboutIntent = new Intent(settingView.getContext(), AboutSoftwareActivity.class);
                startActivity(aboutIntent);
                ActivityCollector.addActivity(getActivity());
                break;
            case "切换账号":
                System.out.println("你选择了切换账号");
                settingPresenter.changeUser();
                break;
            case "退出程序":
                System.out.println("你选择了退出程序");
                settingPresenter.logout();
                break;
            default:
                break;
        }
    }

    //退出程序
    @Override
    public void logout() {
        logoutBuilder = new AlertDialog.Builder(context);
        logoutAlert = logoutBuilder.setTitle("提示")
                .setMessage("你确定要退出MyScse应用")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.AppExit(context);
                    }
                })
                .create();
        logoutAlert.show();
    }

    //切换账号
    @Override
    public void changeUser() {
        Intent loginIntent = new Intent();
        loginIntent.setClass(context,StudentLoginActivity.class);
        startActivity(loginIntent);
    }
}
