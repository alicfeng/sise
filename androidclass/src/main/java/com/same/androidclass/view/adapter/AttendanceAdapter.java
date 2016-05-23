package com.same.androidclass.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.same.androidclass.R;
import com.same.androidclass.bean.Attendance;
import com.same.androidclass.util.DataUtil;

import java.util.List;

/**
 *
 * Created by zjm on 2016/5/10.
 */
public class AttendanceAdapter extends BaseAdapter {
    private List<Attendance> attendances;
    private Context context;

    public AttendanceAdapter(Context context,List<Attendance> attendances) {
        this.attendances = attendances;
        this.context=context;
    }

    @Override
    public int getCount() {
        return attendances.size();
    }

    @Override
    public Object getItem(int position) {
        return attendances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(context, R.layout.home_listview_layout, null);

        TextView class_code = (TextView) convertView.findViewById(R.id.class_code);
        TextView class_name = (TextView) convertView.findViewById(R.id.class_name);
        TextView class_status = (TextView) convertView.findViewById(R.id.class_status);

        class_code.setText("["+attendances.get(position).getClassCode()+"]");
        class_name.setText(DataUtil.subString(attendances.get(position).getClassName(),12,"..."));
        class_status.setText(attendances.get(position).getClassStatus());
        return convertView;
    }
}
