package com.home.rw.mvp.ui.fragments.work;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.event.BeforeReadEvent;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.ui.activitys.work.AskForLeaveActivity;
import com.home.rw.mvp.ui.activitys.work.ExtraWorkActivity;
import com.home.rw.mvp.ui.activitys.work.GetOutActivity;
import com.home.rw.mvp.ui.activitys.work.WipedActivity;
import com.home.rw.mvp.ui.adapters.ApprovementListAdapter;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.utils.RxBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cty on 2016/12/21.
 */

public class ApproveByMeBeforeFragment extends BaseFragment {

    private ApprovementListAdapter mAdapter;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @Inject
    Activity mActivity;

    @Inject
    public ApproveByMeBeforeFragment(){

    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }



    private ArrayList<ApprovementListEntity.DataEntity> dataSource  = new ArrayList<>();
    @Override
    public void initViews(View view) {
        initRecycleView();
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_approvedbymebefore;
    }

    private void initRecycleView(){
        //dummy data
        ApprovementListEntity entity = new ApprovementListEntity();
        ArrayList<ApprovementListEntity.DataEntity> list = new ArrayList<>();

        //1
        ApprovementListEntity.DataEntity child1 = new ApprovementListEntity.DataEntity();
        child1.setName("cyan");
        child1.setAppType(2);
        //child1.setAppStatus(0);


        //2
        ApprovementListEntity.DataEntity child2 = new ApprovementListEntity.DataEntity();
        child2.setName("macsed");
        child2.setAppType(1);
        //child2.setAppStatus(1);

        //3
        ApprovementListEntity.DataEntity child3 = new ApprovementListEntity.DataEntity();
        child3.setName("xigua");
        child3.setAppType(3);
        //child3.setAppStatus(1);

        //4
        ApprovementListEntity.DataEntity child4 = new ApprovementListEntity.DataEntity();
        child4.setName("leelee");
        child4.setAppType(0);
        //child4.setAppStatus(0);

        //5
        ApprovementListEntity.DataEntity child5 = new ApprovementListEntity.DataEntity();
        child5.setName("棋客");
        child5.setAppType(1);
        //child5.setAppStatus(1);

        //6
        ApprovementListEntity.DataEntity child6 = new ApprovementListEntity.DataEntity();
        child6.setName("scboy");
        child6.setAppType(3);
        //child6.setAppStatus(0);

        list.add(child1);
        list.add(child2);
        list.add(child3);
        list.add(child4);
        list.add(child5);
        list.add(child6);

        entity.setData(list);
        dataSource = entity.getData();


        mAdapter = new ApprovementListAdapter(dataSource,mActivity);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent;
                switch (dataSource.get(position).getAppType()){
                    case 0:
                        intent = new Intent(mActivity,GetOutActivity.class);
                        intent.putExtra("entryType","approve");
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(mActivity,AskForLeaveActivity.class);
                        intent.putExtra("entryType","approve");
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(mActivity,ExtraWorkActivity.class);
                        intent.putExtra("entryType","approve");
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(mActivity,WipedActivity.class);
                        intent.putExtra("entryType","approve");
                        startActivity(intent);
                        break;
                }

            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));

        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);

        //通知外层未处理数目
        RxBus.getInstance().post(new BeforeReadEvent(dataSource.size()));

    }
}
