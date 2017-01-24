package com.home.rw.mvp.ui.fragments.work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.ui.activitys.work.AskForLeaveActivity;
import com.home.rw.mvp.ui.activitys.work.ExtraWorkActivity;
import com.home.rw.mvp.ui.activitys.work.GetOutActivity;
import com.home.rw.mvp.ui.activitys.work.WipedActivity;
import com.home.rw.mvp.ui.adapters.ApprovementListAdapter;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by cty on 2016/12/20.
 */

public class ProposeFromMePassedFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    private ApprovementListAdapter mAdapter;

    private boolean mIsLoadingMore;

    private ArrayList<ApprovementListEntity.DataEntity> dataSource  = new ArrayList<>();

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                Observable.timer(1, TimeUnit.SECONDS).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<Long>() {
                            @Override
                            public void onCompleted() {
                                mRefresh.setRefreshing(false);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {

                            }
                        });
            }
        });
        initRecycleView();
    }

    @Inject
    Activity mActivity;

    @Inject
    public ProposeFromMePassedFragment(){

    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_proposepassed;
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
        list.add(child2);
        list.add(child3);
        list.add(child4);
        list.add(child2);
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
                    case 0:
                        intent = new Intent(mActivity,GetOutActivity.class);
                        intent.putExtra("entryType","show");
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(mActivity,AskForLeaveActivity.class);
                        intent.putExtra("entryType","show");
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(mActivity,ExtraWorkActivity.class);
                        intent.putExtra("entryType","show");
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(mActivity,WipedActivity.class);
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
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastCompletelyVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (!mIsLoadingMore && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1) {
                    Log.i("mRecycleView","end");
                    mAdapter.showFooter();
                    mIsLoadingMore = true;
                    mRecycleView.scrollToPosition(mAdapter.getItemCount() - 1);
                    Observable.timer(2, TimeUnit.SECONDS).
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribe(new Observer<Long>() {
                                @Override
                                public void onCompleted() {
                                    mAdapter.hideFooter();
                                    mIsLoadingMore = false;
                                    ApprovementListEntity.DataEntity child3 = new ApprovementListEntity.DataEntity();
                                    child3.setName("xigua");
                                    child3.setAppType(3);

                                    ArrayList<ApprovementListEntity.DataEntity> temp  = new ArrayList<>();
                                    temp.add(child3);
                                    temp.add(child3);
                                    temp.add(child3);
                                    temp.add(child3);
                                    temp.add(child3);
                                    mAdapter.addMore(temp);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(Long aLong) {

                                }
                            });
                }
            }

        });
    }
}
