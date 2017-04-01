package com.home.rw.mvp.ui.activitys.social;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.common.HostType;
import com.home.rw.listener.AppBarStateChangeListener;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.CommListPresenter;
import com.home.rw.mvp.presenter.impl.FocusPresenterImpl;
import com.home.rw.mvp.presenter.impl.ZanPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.CommunicationAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.view.CommListView;
import com.home.rw.mvp.view.FocusView;
import com.home.rw.mvp.view.ZanView;
import com.home.rw.utils.FrescoUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class OthersDetailActivity extends BaseActivity implements CommListView,FocusView,ZanView {

    private boolean mIsLoadingMore;

    private int mTotalPage = 0;

    private int mTotalNums = 0;

    private int mCurrentPage = 0;

    private final int PAGE_SIZE = 15;

    private ArrayList<CommunicationEntity.DataEntity.ResLst> dataSource  = new ArrayList<>();

    private FacusListEntity.DataEntity.ResLst receiveData;

    private CommunicationAdapter mAdapter;

    //是否关注状态
    private boolean isFacused = true;

    private final int DETAIL = 1;

    private int requestPos = 0;
    private int focusPos = 0;
    private int zanPos = 0;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.tv_focus)
    TextView mFocus;

    @BindView(R.id.iv_header)
    SimpleDraweeView mHeader;

    @BindView(R.id.tv_name)
    TextView mName;

    @BindView(R.id.tv_publish)
    TextView mSupportNum;

    @BindView(R.id.compName)
    TextView mCompany;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout  mCollapse;

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @Inject
    CommListPresenter mCommListPresenter;

    @Inject
    FocusPresenterImpl mFocusPresenterImpl;

    @Inject
    ZanPresenterImpl mZanPresenterImpl;
    @OnClick({
            R.id.back,
            R.id.tv_focus

    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                doFinish();

                break;
            case R.id.tv_focus:
                if(isFacused){
                    mFocusPresenterImpl.setReqType(HostType.CANCLE_FOCUS);
                }else{
                    mFocusPresenterImpl.setReqType(HostType.FOCUS);
                }
                mFocusPresenterImpl.doFocus(receiveData.getUserId());
                break;
            default:
                break;

        }

    }

    @Override
    public void onBackPressed() {
        doFinish();
    }

    private void doFinish(){
        Intent intent = new Intent();
        intent.putExtra("isFocus",isFacused);
        setResult(RESULT_OK,intent);
        finish();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_others_detail;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        receiveData = (FacusListEntity.DataEntity.ResLst)getIntent().getSerializableExtra("data");
        mback.setImageResource(R.drawable.btn_back);
        mToolBar.setBackgroundResource(R.color.transparent);

        if(TextUtils.isEmpty(receiveData.getFocus()) ||
                receiveData.getFocus().equals("1"))
            isFacused = true;
        else
            isFacused = false;

        if(isFacused){
            mFocus.setText(getString(R.string.cancleFacus));
        }else{
            mFocus.setText(getString(R.string.addFacus));
        }
        mCollapse.setTitle(receiveData.getRealname());
        mCollapse.setCollapsedTitleTextColor(Color.WHITE);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ) {
                    midText.setText("");
                    //展开状态

                }else if(state == State.COLLAPSED){
                    midText.setText(receiveData.getRealname());
                    //折叠状态

                }else {
                    midText.setText("");
                    //中间状态

                }
            }
        });
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mCurrentPage = 0;
                mCommListPresenter.getCommList(receiveData.getUserId(),mCurrentPage,PAGE_SIZE);
            }
        });
        mCommListPresenter.attachView(this);
        mCommListPresenter.setReqType(HostType.OTHER_PUBLISH);
        mFocusPresenterImpl.attachView(this);
        mZanPresenterImpl.attachView(this);

        mHeader.setImageURI(receiveData.getAvatar());
        mName.setText(receiveData.getRealname());
        mSupportNum.setText(receiveData.getSupportNum());
        mCompany.setText(receiveData.getCompany());

        mCommListPresenter.beforeRequest();
        mCommListPresenter.getCommList(receiveData.getUserId(),mCurrentPage,PAGE_SIZE);

        initRecycleView();
    }

    private void initRecycleView(){

        mAdapter = new CommunicationAdapter(dataSource,"other",this);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(OthersDetailActivity.this,CommDetailActivity.class);
                intent.putExtra("commData",dataSource.get(position));
                intent.putExtra("entryType","Other");
                startActivityForResult(intent,DETAIL);
                requestPos = position;
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
            private boolean isFirstScrolled = true;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.i("cty","onScrolled: dy"+dy);
                if((isFirstScrolled) && (dy>0)){

                    mAdapter.notifyDataSetChanged();
                    isFirstScrolled = false;

                }



            }
        });
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
                        mCommListPresenter.getCommList(receiveData.getUserId(),mCurrentPage,PAGE_SIZE);

                    }

                }


            }

        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
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
        if(reqType == HostType.CANCLE_FOCUS
                || reqType == HostType.FOCUS
                || reqType == HostType.ZAN){

        }
        else
            Toast.makeText(this,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void doFocusCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            isFacused = !isFacused;
            if(isFacused){
                Toast.makeText(this,getString(R.string.focusSucceed),Toast.LENGTH_SHORT).show();
                mFocus.setText(getString(R.string.cancleFacus));
            }else{
                mFocus.setText(getString(R.string.addFacus));
            }

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
        }
    }
}
