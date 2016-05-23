package com.same.androidclass.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.same.androidclass.R;
import com.same.androidclass.bean.Student;
import com.same.androidclass.view.adapter.StudentMessageAdapter;

public class ProfileFragment extends Fragment {
    private ListView messageLists;
    private StudentMessageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.profile, container, false);
        messageLists = (ListView) parentView.findViewById(R.id.student_message_lists);

        adapter = new StudentMessageAdapter(parentView.getContext());

        messageLists.setAdapter(adapter);

        return parentView;
    }

}
