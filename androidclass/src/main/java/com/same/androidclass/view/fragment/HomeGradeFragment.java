package com.same.androidclass.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.same.androidclass.R;
import com.same.androidclass.bean.Grade;
import com.same.androidclass.common.AppConfig;
import com.same.androidclass.presenter.GradePresenter;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.view.activity.CreditShowActivity;
import com.same.androidclass.view.activity.GradeDetailActivity;
import com.same.androidclass.view.adapter.SimpleListAdapter;
import com.same.androidclass.view.view.GradeView;
import com.thuongnh.zprogresshud.ZProgressHUD;

import java.util.List;

/**
 *
 * Created by alic on 16-5-8.
 */
public class HomeGradeFragment extends Fragment implements GradeView, AdapterView.OnItemClickListener {
    private View viewGrade;
    private GradePresenter presenter;
    private ZProgressHUD loading;//加载组件
    private ListView listView;
    private SimpleListAdapter adapter;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGrade = inflater.inflate(R.layout.home_viewpager_four, container, false);

        init();
        return viewGrade;
    }

    public void init() {
        //嘿嘿
        intent = new Intent(viewGrade.getContext(),GradeDetailActivity.class);
        intent.putExtra("genre","1");
        //初始化组件
        listView = (ListView) viewGrade.findViewById(R.id.grade_listView);
        adapter = new SimpleListAdapter(viewGrade.getContext(),AppConfig.GRADE_OPTION());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        //loading
        loading = ZProgressHUD.getInstance(viewGrade.getContext());
        //实例化presenter
        presenter = new GradePresenter(viewGrade.getContext(), this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                presenter.doDisplayGrade();
            }
        }).start();
    }


    @Override
    public void showLoading() {
        loading.show();
    }

    @Override
    public void hideLoading() {
        loading.dismissWithSuccess();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent creditIntent = new Intent(viewGrade.getContext(), CreditShowActivity.class);
                startActivity(creditIntent);
                break;
            case 1:
                Intent optionIntent = new Intent(viewGrade.getContext(),GradeDetailActivity.class);
                optionIntent.putExtra("term","");
                optionIntent.putExtra("genre","0");
                startActivity(optionIntent);
                break;
            case 2:
                intent.putExtra("term","第一学期");
                startActivity(intent);
                break;
            case 3:
                intent.putExtra("term","第二学期");
                startActivity(intent);
                break;
            case 4:
                intent.putExtra("term","第三学期");
                startActivity(intent);
                break;
            case 5:
                intent.putExtra("term","第四学期");
                startActivity(intent);
                break;
            case 6:
                intent.putExtra("term","第五学期");
                startActivity(intent);
                break;
            case 7:
                intent.putExtra("term","第六学期");
                startActivity(intent);
                break;
            case 8:
                intent.putExtra("term","第七学期");
                startActivity(intent);
                break;
            case 9:
                intent.putExtra("term","第八学期");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
