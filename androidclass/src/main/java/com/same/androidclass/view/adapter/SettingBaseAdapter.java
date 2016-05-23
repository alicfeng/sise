package com.same.androidclass.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;

import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;

import com.same.androidclass.R;
import com.same.androidclass.bean.SettingAdapterListCell;
import com.same.androidclass.util.Colors;


/**
 * 设置适配器adapter
 * Created by alic on 16-5-3.
 */
public class SettingBaseAdapter extends BaseAdapter {
    private SettingAdapterListCell[] settingItems;
    private Context context;
    private IconicFontDrawable remindIcon,softwareIcon, changeUserIcon,logoutIcon;

    public SettingBaseAdapter(Context context) {
        this.context = context;
        initIcons();
        settingItems = new SettingAdapterListCell[]{
                new SettingAdapterListCell(remindIcon, "提醒设置"),
                new SettingAdapterListCell(softwareIcon, "关于软件"),
                new SettingAdapterListCell(changeUserIcon, "切换账号"),
                new SettingAdapterListCell(logoutIcon, "退出程序"),
        };
    }

    public void initIcons() {
        remindIcon = new IconicFontDrawable(context,FontAwesomeIcon.TIME);
        remindIcon.setIconColor(Color.GRAY);

        softwareIcon = new IconicFontDrawable(context,FontAwesomeIcon.INFO_SIGN);
        softwareIcon.setIconColor(Color.GRAY);

        changeUserIcon = new IconicFontDrawable(context,EntypoIcon.SHUFFLE);
        changeUserIcon.setIconColor(Color.GRAY);

        logoutIcon = new IconicFontDrawable(context,FontAwesomeIcon.OFF);
        logoutIcon.setIconColor(Color.GRAY);
    }

    @Override
    public int getCount() {
        return settingItems.length;
    }

    @Override
    public Object getItem(int position) {
        return settingItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getSelectedItemName(int position) {
        return settingItems[position].getSettingName();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.setting_item_layout, null);

        SettingAdapterListCell data = (SettingAdapterListCell) getItem(position);

        //获得组件
        ImageView icon = (ImageView) convertView.findViewById(R.id.setting_item_icon);
        TextView settingName = (TextView) convertView.findViewById(R.id.setting_item_name);

        //设置组件信息
//        icon.setImageResource();
        icon.setBackground(data.getIcon());
        settingName.setText(data.getSettingName());

        return convertView;
    }
}
