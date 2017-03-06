package com.home.rw.mvp.ui.activitys.work;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.RollMeEntity;
import com.home.rw.mvp.entity.SignEntity;
import com.home.rw.mvp.presenter.impl.RollListPresenterImpl;
import com.home.rw.mvp.presenter.impl.SignListPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.adapters.RollMelistAdapter;
import com.home.rw.mvp.ui.adapters.SignListAdapter;
import com.home.rw.mvp.view.SignListView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class SignListActivity extends BaseActivity implements SignListView{

    private boolean mIsLoadingMore;

    private int mTotalPage = 0;

    private int mTotalNums = 0;

    private int mCurrentPage = 0;

    private final int PAGE_SIZE = 15;

    private SignListAdapter mAdapter;

    private ArrayList<SignEntity.DataEntity.ResLst> dataSource  = new ArrayList<>();


    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    @Inject
    SignListPresenterImpl mSignListPresenterImpl;


    @OnClick({
            R.id.back,
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;

            default:
                break;


        }

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_list;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.signList);
        mback.setImageResource(R.drawable.btn_back);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mCurrentPage = 0;
                mSignListPresenterImpl.getSignList(mCurrentPage,PAGE_SIZE);
            }
        });
        mSignListPresenterImpl.attachView(this);
        mSignListPresenterImpl.beforeRequest();
        mSignListPresenterImpl.getSignList(mCurrentPage,PAGE_SIZE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initRecycleView();
    }

    private void initRecycleView() {



        mAdapter = new SignListAdapter(dataSource,this);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
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
                                mSignListPresenterImpl.getSignList(mCurrentPage,PAGE_SIZE);

                            }

                        }
                    }

                });
            }

        });
    }
    @Override
    public void getSignListComplete(SignEntity data) {
        if(data.getCode().equals("ok")){
            mTotalPage = data.getData().getTotalPages();
            if(mCurrentPage == 0){
                dataSource = data.getData().getResLst();
                mAdapter.setList(dataSource);
            }
            else
                mAdapter.addMore(data.getData().getResLst());
        }else{
            Toast.makeText(this,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSignListPresenterImpl!=null){
            mSignListPresenterImpl.onDestroy();
        }
    }
}
