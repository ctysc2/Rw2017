package com.home.rw.mvp.ui.activitys.social;

import android.content.Intent;
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
import com.home.rw.common.HostType;
import com.home.rw.event.BeforeReadEvent;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.CommListPresenter;
import com.home.rw.mvp.presenter.impl.FocusPresenterImpl;
import com.home.rw.mvp.presenter.impl.ZanPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.work.AskForLeaveActivity;
import com.home.rw.mvp.ui.activitys.work.ExtraWorkActivity;
import com.home.rw.mvp.ui.activitys.work.GetOutActivity;
import com.home.rw.mvp.ui.activitys.work.WipedActivity;
import com.home.rw.mvp.ui.adapters.ApprovementListAdapter;
import com.home.rw.mvp.ui.adapters.CommunicationAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.view.CommListView;
import com.home.rw.mvp.view.FocusView;
import com.home.rw.mvp.view.ZanView;
import com.home.rw.utils.RxBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class CommListActivity extends BaseActivity implements CommListView,FocusView,ZanView{

    private boolean mIsLoadingMore;

    private int mTotalPage = 0;

    private int mTotalNums = 0;

    private int mCurrentPage = 0;

    private final int PAGE_SIZE = 15;

    private int mStartType = -1;

    private CommunicationAdapter mAdapter;

    private ArrayList<CommunicationEntity.DataEntity.ResLst> dataSource  = new ArrayList<>();

    private final int DETAIL = 1;
    private int requestPos = 0;
    private int focusPos = 0;
    private int zanPos = 0;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @Inject
    CommListPresenter mCommListPresenter;

    @Inject
    FocusPresenterImpl mFocusPresenterImpl;

    @Inject
    ZanPresenterImpl mZanPresenterImpl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_comm_list;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }
    @OnClick({
            R.id.back
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
    protected void onDestroy() {
        super.onDestroy();

        if(mZanPresenterImpl != null){
            mZanPresenterImpl.onDestroy();
        }
        if(mCommListPresenter != null){
            mCommListPresenter.onDestroy();
        }
        if(mFocusPresenterImpl != null){
            mFocusPresenterImpl.onDestroy();
        }
    }

    @Override
    public void initViews() {
        mStartType = getIntent().getIntExtra("startType",-1);
        mCommListPresenter.attachView(this);
        mFocusPresenterImpl.attachView(this);
        mZanPresenterImpl.attachView(this);
        switch (mStartType){
            case 0:
                midText.setText(R.string.projectCommunicationTitle);
                mCommListPresenter.setReqType(HostType.PUBLISH_LIST1);
                break;
            case 1:
                midText.setText(R.string.ActivityCommunicationTitle);
                mCommListPresenter.setReqType(HostType.PUBLISH_LIST2);
                break;
            case 2:
                midText.setText(R.string.CultureCommunicationTitle);
                mCommListPresenter.setReqType(HostType.PUBLISH_LIST3);
                break;
            case 3:
                midText.setText(R.string.CoopCommunicationTitle);
                mCommListPresenter.setReqType(HostType.PUBLISH_LIST4);
                break;
            case 4:
                midText.setText(R.string.minePublish);
                mCommListPresenter.setReqType(HostType.MY_PUBLISH);
            default:
                break;

        }

        mback.setImageResource(R.drawable.btn_back);

        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mCurrentPage = 0;
                mCommListPresenter.getCommList(mCurrentPage,PAGE_SIZE);
            }
        });

        mCommListPresenter.beforeRequest();
        mCommListPresenter.getCommList(mCurrentPage,PAGE_SIZE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initRecycleView();
    }

    private void initRecycleView(){

        mAdapter = new CommunicationAdapter(dataSource,this);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(CommListActivity.this,CommDetailActivity.class);
                intent.putExtra("commData",dataSource.get(position));
                startActivityForResult(intent,DETAIL);
                requestPos = position;
            }
        });
        mAdapter.setOnItemFocusClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                focusPos = position;
                mFocusPresenterImpl.setReqType(HostType.FOCUS);
                mFocusPresenterImpl.doFocus(dataSource.get(position).getCreatedBy());

            }
        });
        mAdapter.setOnItemZanClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                    zanPos = position;
                    mZanPresenterImpl.zan(dataSource.get(position).getId());

            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
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
                        mCommListPresenter.getCommList(mCurrentPage,PAGE_SIZE);

                        }

                    }
                }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case DETAIL:
                if(resultCode == RESULT_OK){
                    CommunicationEntity.DataEntity.ResLst entity = (CommunicationEntity.DataEntity.ResLst)data.getSerializableExtra("newData");
                    dataSource.set(requestPos,entity);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void getCommListCompleted(CommunicationEntity data) {
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
        if(HostType.FOCUS == reqType)
            Toast.makeText(this,getString(R.string.focusFailed),Toast.LENGTH_SHORT).show();
        else if(HostType.ZAN == reqType){
            Toast.makeText(this,getString(R.string.zanFailed),Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void doFocusCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            dataSource.get(focusPos).setFocus("1");
            mAdapter.notifyDataSetChanged();
            Toast.makeText(this,getString(R.string.focusSucceed),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void zanCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            dataSource.get(zanPos).setSupport("1");
            int supportNum = Integer.parseInt(dataSource.get(zanPos).getSupportNum());
            String newSupport = String.valueOf(supportNum+1);
            dataSource.get(zanPos).setSupport("1");
            dataSource.get(zanPos).setSupportNum(newSupport);
            mAdapter.notifyDataSetChanged();
            Toast.makeText(this,getString(R.string.zanSucceed),Toast.LENGTH_SHORT).show();
        }
    }
}
