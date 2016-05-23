package com.same.androidclass.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.same.androidclass.R;

import java.util.ArrayList;

public class campusFragment extends Fragment {

    private View campusView;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        campusView = inflater.inflate(R.layout.campus, container, false);
        initView();
        return campusView;
    }

    private void initView(){
        listView   = (ListView) campusView.findViewById(R.id.campus_listView);
    }

}
