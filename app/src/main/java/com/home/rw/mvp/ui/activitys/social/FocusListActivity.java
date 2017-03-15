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
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.presenter.impl.ApprovementListPresenterImpl;
import com.home.rw.mvp.presenter.impl.FoucsListPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.CommunicationAdapter;
import com.home.rw.mvp.ui.adapters.FacusListAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewDivider;
import com.home.rw.mvp.view.FocusListView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class FocusListActivity extends BaseActivity implements FocusListView{

    private boolean mIsLoadingMore;

    private ArrayList<FacusListEntity.DataEntity.ResLst> datasource = new ArrayList<>();

    private FacusListAdapter mAdapterFacus;

    private int mTotalPage = 0;

    private int mCurrentPage = 0;

    private final int PAGE_SIZE = 15;

    private int requestPos = 0;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @Inject
    FoucsListPresenterImpl mFoucsListPresenterImpl;


    @OnClick({
            R.id.back
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
            default:
                break;


        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_focus_list;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.MyFacus);
        mback.setImageResource(R.drawable.btn_back);
        initRecycleView();
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mCurrentPage = 0;
                mFoucsListPresenterImpl.getFocusList(mCurrentPage,PAGE_SIZE);

            }
        });
        mFoucsListPresenterImpl.attachView(this);
        mFoucsListPresenterImpl.beforeRequest();
        mFoucsListPresenterImpl.getFocusList(mCurrentPage,PAGE_SIZE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initRecycleView() {

        //关注的人列表
        mAdapterFacus = new FacusListAdapter(datasource,this);

        mAdapterFacus.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                requestPos = position;
                Intent intent = new Intent(FocusListActivity.this,OthersDetailActivity.class);
                intent.putExtra("data",datasource.get(position));
                startActivityForResult(intent,0);

            }
        });
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.addItemDecoration(new RecycleViewDivider(this,RecycleViewDivider.VERTICAL_LIST,RecycleViewDivider.COMMON));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapterFacus);
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
                        mAdapterFacus.showFooter();
                        mIsLoadingMore = true;
                        mRecycleView.scrollToPosition(mAdapterFacus.getItemCount() - 1);
                        mFoucsListPresenterImpl.getFocusList(mCurrentPage,PAGE_SIZE);

                    }

                }
            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                if(resultCode == RESULT_OK){
                    boolean isFocused =data.getBooleanExtra("isFocus",true);
                    if(!isFocused){
                        datasource.remove(requestPos);
                        mAdapterFacus.notifyDataSetChanged();
                    }
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void getFocusList(FacusListEntity data) {
        if(data.getCode().equals("ok")){
            mTotalPage = data.getData().getTotalPages();
            if(mCurrentPage == 0){
                datasource = data.getData().getResLst();
                mAdapterFacus.setList(datasource);
            }
            else
                mAdapterFacus.addMore(data.getData().getResLst());
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
            mAdapterFacus.hideFooter();
            mIsLoadingMore = false;
        }

    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        mRefresh.setRefreshing(false);
        if(mIsLoadingMore){
            mAdapterFacus.hideFooter();
            mIsLoadingMore = false;
        }
        Toast.makeText(this,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();

    }
}
