package com.home.rw.mvp.ui.fragments.social;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.common.HostType;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.MainPageEntity;
import com.home.rw.mvp.interactor.impl.CommListInteractorImpl;
import com.home.rw.mvp.presenter.impl.CommListPresenter;
import com.home.rw.mvp.presenter.impl.MainPagePresenterImpl;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.adapters.HomePagerAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.mvp.view.CommListView;
import com.home.rw.mvp.view.MainPageView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by cty on 2017/1/5.
 */

public class HomePageFragment extends BaseFragment implements MainPageView,CommListView{

    private HomePagerAdapter mAdapter;

    private boolean mIsLoadingMore;

    private int mTotalPage = 0;

    private int mTotalNums = 0;

    private int mCurrentPage = 0;

    private final int PAGE_SIZE = 15;

    @Inject
    MainPagePresenterImpl mMainPagePresenterImpl;

    @Inject
    CommListPresenter mCommListPresenterImpl;
    @Inject
    Activity mActivity;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    private ArrayList<CommunicationEntity.DataEntity.ResLst> dataSource  = new ArrayList<>();

    private View mHeader;

    private CommunicationEntity.DataEntity.ResLst mCompany = new CommunicationEntity.DataEntity.ResLst();

    @Inject
    public HomePageFragment(){

    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        mMainPagePresenterImpl.attachView(this);
        mCommListPresenterImpl.attachView(this);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mMainPagePresenterImpl.getMainPage();
            }
        });


        mMainPagePresenterImpl.beforeRequest();
        mMainPagePresenterImpl.getMainPage();
        initRecycleView();

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_homepage;
    }
    private void initRecycleView(){

        mHeader = LayoutInflater.from(mActivity).inflate(R.layout.layout_homepage_header, null, false);

        mAdapter = new HomePagerAdapter(dataSource,mActivity);
        mAdapter.setIsShowHeader(true);
        mAdapter.setHeaderView(mHeader);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                    if(position > 0){
                        Intent intent = new Intent(mActivity,CommDetailActivity.class);
                        intent.putExtra("commData",dataSource.get(position));
                        intent.putExtra("entryType","HomePage");
                        startActivity(intent);
                    }



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
                        mCommListPresenterImpl.getCommList(mCurrentPage,PAGE_SIZE);

                    }

                }

            }

        });

    }

    @Override
    public void getMainPageCompleted(MainPageEntity data) {
        if(data.getCode().equals("ok")){
            TextView mHead = (TextView)mHeader.findViewById(R.id.tv_comp_header);
            SimpleDraweeView mAvatar = (SimpleDraweeView)mHeader.findViewById(R.id.iv_header);
            TextView mName = (TextView)mHeader.findViewById(R.id.tv_name);
            TextView mDetail = (TextView)mHeader.findViewById(R.id.tv_detail);
            TextView mYear = (TextView)mHeader.findViewById(R.id.tv_year);
            TextView mLocation = (TextView)mHeader.findViewById(R.id.tv_loaction);

            mName.setText(data.getData().getName());
            mDetail.setText(data.getData().getIntro());
            mYear.setText(data.getData().getRegDate());
            mLocation.setText(data.getData().getRegCity());

            if(!TextUtils.isEmpty(data.getData().getLogo())){
                mAvatar.setVisibility(View.VISIBLE);
                mAvatar.setImageURI(data.getData().getLogo());
                mHead.setVisibility(View.INVISIBLE);
            }else{
                mAvatar.setVisibility(View.INVISIBLE);
                mHead.setVisibility(View.VISIBLE);

                if(data.getData().getName().length()>=1)
                    mHead.setText(data.getData().getName().substring(0,1));
            }

            mCompany.setTitle(data.getData().getTitle());
            mCompany.setContent(data.getData().getContent());
            mCurrentPage = 0;
            mCommListPresenterImpl.setReqType(HostType.DYN);
            mCommListPresenterImpl.getCommList(mCurrentPage,PAGE_SIZE);
        }

    }

    @Override
    public void showProgress(int reqType) {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress(int reqType) {
        if(reqType == HostType.MAIN_PAGE)
            return;
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
    public void getCommListCompleted(CommunicationEntity data) {
        if(data.getCode().equals("ok")){
            mTotalPage = data.getData().getTotalPages();
            if(mCurrentPage == 0){
                dataSource = new ArrayList<CommunicationEntity.DataEntity.ResLst>();
                dataSource.add(mCompany);
                dataSource.addAll(1,data.getData().getResLst());
                mAdapter.setList(dataSource);
            }
            else
                mAdapter.addMore(data.getData().getResLst());
        }else{
            Toast.makeText(mActivity,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();
        }
    }
}
