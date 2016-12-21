package com.home.rw.mvp.ui.fragments.work;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.rw.R;
import com.home.rw.mvp.entity.ApprovementListEntity;
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

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Inject
    Activity mActivity;

    private ApprovementListAdapter mAdapter;

    private List<ApprovementListEntity> dataSource  = new ArrayList<>();
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
        dataSource.add(new ApprovementListEntity());
        dataSource.add(new ApprovementListEntity());
        dataSource.add(new ApprovementListEntity());
        dataSource.add(new ApprovementListEntity());

        mAdapter = new ApprovementListAdapter(dataSource,mActivity);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        //mRecycleView .addItemDecoration(new RecycleViewDivider(mActivity,LinearLayoutManager.VERTICAL,APPROVEMENT_LIST_CELL));

        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);

    }
}
