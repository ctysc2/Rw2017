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
import com.home.rw.common.HostType;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.presenter.impl.ApprovementListPresenterImpl;
import com.home.rw.mvp.ui.activitys.work.AskForLeaveActivity;
import com.home.rw.mvp.ui.activitys.work.ExtraWorkActivity;
import com.home.rw.mvp.ui.activitys.work.GetOutActivity;
import com.home.rw.mvp.ui.activitys.work.WipedActivity;
import com.home.rw.mvp.ui.adapters.ApprovementListAdapter;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.mvp.view.ApprovementListView;

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

public class ProposeFromMePassedFragment extends BaseFragment implements ApprovementListView {
    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    private ApprovementListAdapter mAdapter;

    private boolean mIsLoadingMore;

    private boolean isFirstLoad = true;

    private boolean isFragmentVisible = false;

    private boolean isViewCreated = false;

    private int mTotalPage = 0;

    private int mCurrentPage = 0;

    private final int PAGE_SIZE = 15;

    @Inject
    ApprovementListPresenterImpl mApprovementListPresenterImpl;

    private ArrayList<ApprovementListEntity.DataEntity.ResLst> dataSource  = new ArrayList<>();

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        mApprovementListPresenterImpl.attachView(this);
        mApprovementListPresenterImpl.setAddApplyType(HostType.APPROVE_PASSED);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mCurrentPage = 0;
                mApprovementListPresenterImpl.getApprovementList(mCurrentPage,PAGE_SIZE);

            }
        });
        initRecycleView();
        isViewCreated = true;
        if(isFirstLoad && isFragmentVisible && isViewCreated){
            mApprovementListPresenterImpl.beforeRequest();
            mApprovementListPresenterImpl.getApprovementList(mCurrentPage,PAGE_SIZE);
            isFirstLoad = false;
        }
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


        mAdapter = new ApprovementListAdapter(dataSource,mActivity);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent;
                switch (dataSource.get(position).getType()){
                    case "0":
                        intent = new Intent(mActivity,GetOutActivity.class);
                        intent.putExtra("entryType","show");
                        intent.putExtra("formID",dataSource.get(position).getId());
                        startActivity(intent);
                        break;
                    case "1":
                        intent = new Intent(mActivity,AskForLeaveActivity.class);
                        intent.putExtra("entryType","show");
                        intent.putExtra("formID",dataSource.get(position).getId());
                        startActivity(intent);
                        break;
                    case "2":
                        intent = new Intent(mActivity,ExtraWorkActivity.class);
                        intent.putExtra("entryType","show");
                        intent.putExtra("formID",dataSource.get(position).getId());
                        startActivity(intent);
                        break;
                    case "3":
                        intent = new Intent(mActivity,WipedActivity.class);
                        intent.putExtra("entryType","show");
                        intent.putExtra("formID",dataSource.get(position).getId());
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
                    mCurrentPage++;
                    if(mCurrentPage<mTotalPage){
                        mAdapter.showFooter();
                        mIsLoadingMore = true;
                        mRecycleView.scrollToPosition(mAdapter.getItemCount() - 1);
                        mApprovementListPresenterImpl.getApprovementList(mCurrentPage,PAGE_SIZE);

                    }

                }
            }

        });
    }
    @Override
    public void getApprovementListCompleted(ApprovementListEntity data) {
        if(data.getCode().equals("ok")){
            mTotalPage = data.getData().getTotalPages();
            if(mCurrentPage == 0){
                dataSource = data.getData().getResLst();
                mAdapter.setList(dataSource);
            }
            else
                mAdapter.addMore(data.getData().getResLst());
        }else{
            Toast.makeText(mActivity,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showProgress(int reqType) {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress(int reqType) {
        mRefresh.setRefreshing(false);
        if(mIsLoadingMore){
            mAdapter.hideFooter();
            mIsLoadingMore = false;
        }

    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        mRefresh.setRefreshing(false);
        if(mIsLoadingMore){
            mAdapter.hideFooter();
            mIsLoadingMore = false;
        }
        Toast.makeText(mActivity,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isFragmentVisible = isVisibleToUser;


        if(isFirstLoad && isFragmentVisible && isViewCreated){
            mApprovementListPresenterImpl.beforeRequest();
            mApprovementListPresenterImpl.getApprovementList(mCurrentPage,PAGE_SIZE);
            isFirstLoad = false;
        }


    }

}
