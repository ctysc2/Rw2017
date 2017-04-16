package com.home.rw.mvp.ui.fragments.work;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.common.HostType;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.entity.LogEntity;
import com.home.rw.mvp.presenter.impl.LogListPresenterImpl;
import com.home.rw.mvp.ui.adapters.ApprovementListAdapter;
import com.home.rw.mvp.ui.adapters.ReceivedLogListAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.mvp.view.LogListView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by cty on 2016/12/27.
 */

public class ReceivedLogFragment extends BaseFragment implements LogListView {
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    private boolean mIsLoadingMore;

    private boolean isFirstLoad = true;

    private int mTotalPage = 0;

    private int mTotalNums = 0;

    private int mCurrentPage = 0;

    private boolean isFragmentVisible = false;

    private boolean isViewCreated = false;

    private final int PAGE_SIZE = 15;

    private ReceivedLogListAdapter mAdapter;

    private ArrayList<LogEntity.DataEntity.ResLst> dataSource  = new ArrayList<>();

    @Inject
    public ReceivedLogFragment(){

    }
    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @Inject
    Activity mActivity;

    @Inject
    LogListPresenterImpl mLogListPresenterImpl;

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
                mCurrentPage = 0;
                mLogListPresenterImpl.getLogList(mCurrentPage,PAGE_SIZE);
            }
        });

        initRecycleView();
        mLogListPresenterImpl.attachView(this);
        mLogListPresenterImpl.setAddApplyType(HostType.RECEIVE_LOG);
        isViewCreated = true;
        if(isFirstLoad && isFragmentVisible && isViewCreated){
            mLogListPresenterImpl.beforeRequest();
            mLogListPresenterImpl.getLogList(mCurrentPage,PAGE_SIZE);
            isFirstLoad = false;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_receive_log;
    }

    private void initRecycleView(){

        mAdapter = new ReceivedLogListAdapter(dataSource,mActivity);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.addItemDecoration(new RecycleViewSperate());
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
                        mLogListPresenterImpl.getLogList(mCurrentPage,PAGE_SIZE);

                    }

                }

            }

        });
    }
    @Override
    public void getLogListCompleted(LogEntity data) {
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
            mLogListPresenterImpl.beforeRequest();
            mLogListPresenterImpl.getLogList(mCurrentPage,PAGE_SIZE);
            isFirstLoad = false;
        }


    }
}
