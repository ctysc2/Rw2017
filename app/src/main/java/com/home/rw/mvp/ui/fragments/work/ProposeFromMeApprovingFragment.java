package com.home.rw.mvp.ui.fragments.work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.ui.activitys.work.ExtraWorkActivity;
import com.home.rw.mvp.ui.adapters.ApprovementListAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewDivider;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.home.rw.mvp.ui.adapters.RecycleViewDivider.APPROVEMENT_LIST_CELL;

/**
 * Created by cty on 2016/12/20.
 */

public class ProposeFromMeApprovingFragment extends BaseFragment {

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @Inject
    Activity mActivity;

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }



    private ApprovementListAdapter mAdapter;

    private ArrayList<ApprovementListEntity.DataEntity> dataSource  = new ArrayList<>();

    @Inject
    public ProposeFromMeApprovingFragment(){

    }

    @Override
    public void initViews(View view) {
        initRecycleView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_proposeapproving;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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


        list.add(child1);
        list.add(child2);
        list.add(child3);
        list.add(child4);

        entity.setData(list);
        dataSource = entity.getData();


        mAdapter = new ApprovementListAdapter(dataSource,mActivity);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent;
                switch (dataSource.get(position).getAppType()){
                    case 2:
                        intent = new Intent(mActivity,ExtraWorkActivity.class);
                        intent.putExtra("entryType","show");
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

    }
}
