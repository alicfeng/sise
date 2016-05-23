package com.same.androidclass.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;
import com.same.androidclass.R;
import com.same.androidclass.util.Colors;

import java.util.List;

/**
 * 成绩选择列表
 * Created by alic on 16-5-18.
 */
public class SimpleListAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private IconicFontDrawable icon;

    public SimpleListAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        initIcon();
    }
    public void initIcon(){
        icon = new IconicFontDrawable(context, EntypoIcon.CHEVRON_THIN_RIGHT);
        icon.setIconColor(Colors.color_theme);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.simple_single_adapter_layout, null);

        TextView name = (TextView) convertView.findViewById(R.id.simple_single_adapter_name);
        View icon = convertView.findViewById(R.id.simple_single_adapter_icon);

        icon.setBackground(this.icon);
        name.setText(list.get(position));
        return convertView;
    }
}
