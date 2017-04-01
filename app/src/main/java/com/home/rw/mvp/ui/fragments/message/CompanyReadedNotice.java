package com.home.rw.mvp.ui.fragments.message;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.common.HostType;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.CompanyNoticeEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.CompNoticeEntity;
import com.home.rw.mvp.entity.message.TopicCommonEntity;
import com.home.rw.mvp.presenter.impl.CompanyNoticePresenterImpl;
import com.home.rw.mvp.presenter.impl.DelNoticePresenterImpl;
import com.home.rw.mvp.ui.activitys.message.CompanyNoticeDetailActivity;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.adapters.CompanyNoticeListAdapter;
import com.home.rw.mvp.ui.adapters.HomePagerAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.mvp.view.CompanyNoticeView;
import com.home.rw.mvp.view.DelNoticeView;
import com.home.rw.utils.DialogUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;


public class CompanyReadedNotice extends BaseFragment implements AlertDialogListener,CompanyNoticeView,DelNoticeView {

    private ArrayList<TopicCommonEntity> dataSource  = new ArrayList<>();

    private CompanyNoticeListAdapter mAdapter;

    private boolean mIsLoadingMore;

    private boolean isFirstLoad = true;

    private boolean isFragmentVisible = false;

    private boolean isViewCreated = false;

    private int mTotalPage = 0;

    private int mCurrentPage = 0;

    private final int PAGE_SIZE = 15;

    private int selectPositon;

    private static final int READED = 0;

    private String topid = "";

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    @Inject
    Activity mActivity;

    @Inject
    CompanyNoticePresenterImpl mCompanyNoticePresenterImpl;

    @Inject
    DelNoticePresenterImpl mDelNoticePresenterImpl;

    @Inject
    public CompanyReadedNotice() {

    }


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
                mCompanyNoticePresenterImpl.getCompanyNotice(mCurrentPage,PAGE_SIZE,READED);
            }
        });
        mCompanyNoticePresenterImpl.attachView(this);
        mCompanyNoticePresenterImpl.setReqType(HostType.COMPANY_NOTICE);

        mDelNoticePresenterImpl.attachView(this);
        mDelNoticePresenterImpl.setReqType(HostType.DEL_NOTICE);
        initRecycleView();
        isViewCreated = true;
        if(isFirstLoad && isFragmentVisible && isViewCreated){
            mCompanyNoticePresenterImpl.beforeRequest();
            mCompanyNoticePresenterImpl.getCompanyNotice(mCurrentPage,PAGE_SIZE,READED);
            isFirstLoad = false;
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isFragmentVisible = isVisibleToUser;


        if(isFirstLoad && isFragmentVisible && isViewCreated){
            mCompanyNoticePresenterImpl.beforeRequest();
            mCompanyNoticePresenterImpl.getCompanyNotice(mCurrentPage,PAGE_SIZE,READED);
            isFirstLoad = false;
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mCompanyNoticePresenterImpl!=null)
            mCompanyNoticePresenterImpl.onDestroy();

        if(mDelNoticePresenterImpl!=null)
            mDelNoticePresenterImpl.onDestroy();
    }

    private void initRecycleView() {


        mAdapter = new CompanyNoticeListAdapter(dataSource,mActivity);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mActivity,CompanyNoticeDetailActivity.class);
                intent.putExtra("data",dataSource.get(position));
                startActivity(intent);
            }
        });
        mAdapter.setOnItemDeleteListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectPositon = position;
                topid = dataSource.get(position).getId();
                mAlertDialog = DialogUtils.create(mActivity,DialogUtils.TYPE_ALERT);
                mAlertDialog.show(CompanyReadedNotice.this,getString(R.string.noticedelHint1));
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
                        mCompanyNoticePresenterImpl.getCompanyNotice(mCurrentPage,PAGE_SIZE,READED);

                    }

                }
            }

        });

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_company_readed_notice;
    }


    @Override
    public void onConFirm() {
        mAlertDialog.dismiss();
        if(!TextUtils.isEmpty(topid))
            mDelNoticePresenterImpl.delNotice(topid);
        else
            Toast.makeText(mActivity,getString(R.string.deleteFailed),Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onCancel() {
        mAlertDialog.dismiss();
    }

    @Override
    public void getCompanyNoticeCompleted(CompNoticeEntity data) {
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
        if(reqType == HostType.COMPANY_NOTICE){
            mRefresh.setRefreshing(true);
        }else{
            if(mLoadDialog == null){
                mLoadDialog = DialogUtils.create(mActivity, DialogUtils.TYPE_DEL);
                mLoadDialog.show();
            }
        }

    }

    @Override
    public void hideProgress(int reqType) {
        if(reqType == HostType.COMPANY_NOTICE){
            mRefresh.setRefreshing(false);
            if(mIsLoadingMore){
                mAdapter.hideFooter();
                mIsLoadingMore = false;
            }
        }else{
            if(mLoadDialog!=null){
                mLoadDialog.dismiss();
                mLoadDialog = null;
            }

        }


    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        if(reqType == HostType.COMPANY_NOTICE){
            mRefresh.setRefreshing(false);
            if(mIsLoadingMore){
                mAdapter.hideFooter();
                mIsLoadingMore = false;
            }
            Toast.makeText(mActivity,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();
        }else{
            if(mLoadDialog!=null){
                mLoadDialog.dismiss();
                mLoadDialog = null;
            }
            Toast.makeText(mActivity,getString(R.string.deleteFailed),Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void delNoticeCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            Toast.makeText(mActivity,getString(R.string.deleteSuccess),Toast.LENGTH_SHORT).show();
            //删除公告
            mAdapter.delete(selectPositon);
        }

    }
}
