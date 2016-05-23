package com.same.androidclass.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.same.androidclass.R;
import com.same.androidclass.bean.Exam;
import com.same.androidclass.bean.Student;
import com.same.androidclass.presenter.ExamPresenter;
import com.same.androidclass.util.ActivityCollector;
import com.same.androidclass.util.DataUtil;
import com.same.androidclass.util.NetWorkUtils;
import com.same.androidclass.view.activity.ExamDetailActivity;
import com.same.androidclass.view.adapter.ExamAdapter;
import com.same.androidclass.view.view.ExamView;
import com.thuongnh.zprogresshud.ZProgressHUD;

import java.util.List;

/**
 *考试Fragment
 * Created by alic on 16-5-8.
 */
public class HomeExamFragment extends Fragment implements ExamView, SwipeRefreshLayout.OnRefreshListener {
    private final String TAG = "HomeExamFragment";
    private SwipeRefreshLayout swipeRefresh;
    private TextView username,studentId,studentEmail,studentIdCard,gradeAdmin;
    private ListView listView;
    private ExamAdapter adapter;
    private View viewExam;
    private ZProgressHUD loading;//加载组件
    private ExamPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewExam= inflater.inflate(R.layout.home_viewpager_three,container, false);
        initView();

        return viewExam;
    }
    public void initView(){
        username = (TextView) viewExam.findViewById(R.id.exam_studentName_item);
        studentId = (TextView) viewExam.findViewById(R.id.exam_studentId_item);
        studentEmail = (TextView) viewExam.findViewById(R.id.exam_studentEmail_item);
        studentIdCard = (TextView) viewExam.findViewById(R.id.exam_studentIdCard_item);
        gradeAdmin = (TextView) viewExam.findViewById(R.id.exam_gradeAdmin_item);

        //loading
        loading = ZProgressHUD.getInstance(viewExam.getContext());


        swipeRefresh = (SwipeRefreshLayout) viewExam.findViewById(R.id.home_exam_swipeRefresh);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefresh.setOnRefreshListener(this);
        listView = (ListView) viewExam.findViewById(R.id.home_exam_listView);

        presenter = new ExamPresenter(viewExam.getContext(),this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //显示考试信息
                presenter.doLoadExam();
                //显示考试相关个人信息
                presenter.doDisplayStudentMessage();
            }
        }).start();

    }

    @Override
    public void displayExam(final List<Exam> exams) {
        adapter = new ExamAdapter(viewExam.getContext(),exams);
        listView.setAdapter(adapter);
        //监听单击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到具体详细的考试信息界面
                Intent detailIntent = new Intent(viewExam.getContext(), ExamDetailActivity.class);
                Bundle detailBundle = new Bundle();
                detailBundle.putSerializable("exam",exams.get(position));
                detailIntent.putExtras(detailBundle);
                startActivity(detailIntent);
                //为了退出优化处理
                ActivityCollector.addActivity(getActivity());
            }
        });
    }

    @Override
    public void displayStudentMessage(Student student) {
        username.setText(student.getUsername());
        studentId.setText(student.getStudentId());
        studentEmail.setText(student.getEmail());
        studentIdCard.setText(DataUtil.hiddenCode(student.getIdCard(),6,12,"*"));
        gradeAdmin.setText(student.getGradeAdmin());
    }

    @Override
    public void hideRefresh() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showSuccess() {
        Toast.makeText(viewExam.getContext(),"加载完毕",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(viewExam.getContext(),"Sorry 加载失败",Toast.LENGTH_SHORT).show();
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
    public void onRefresh() {
        //判断是否有网络
        if (NetWorkUtils.isNetworkConnected(viewExam.getContext())){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    presenter.refreshExam();
                }
            }).start();
        }else {
            Toast.makeText(viewExam.getContext(),"Sorry 没有可用网络",Toast.LENGTH_SHORT).show();
            this.hideRefresh();
        }
    }
}
