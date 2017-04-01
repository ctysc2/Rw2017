package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.common.HostType;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.MyFriendEntity;
import com.home.rw.mvp.entity.ReceiveFriendEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.MessageCommonEntity;
import com.home.rw.mvp.entity.message.NewFriendEntity;
import com.home.rw.mvp.interactor.impl.AcceptFriendInteractorImpl;
import com.home.rw.mvp.interactor.impl.NewFriendInteractorImpl;
import com.home.rw.mvp.presenter.impl.AcceptFriendPresenterImpl;
import com.home.rw.mvp.presenter.impl.NewFriendPresenter;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.MyFriendAdapter;
import com.home.rw.mvp.ui.adapters.ReceiveFriendAdapter;
import com.home.rw.mvp.view.AcceptFriendView;
import com.home.rw.mvp.view.NewFriendView;
import com.home.rw.utils.DialogUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.Event;
import io.rong.imlib.model.Conversation;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class MyNewFriendActivity extends BaseActivity implements NewFriendView,AcceptFriendView{

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    private ReceiveFriendAdapter mAdapter;

    private String mTargetId;

    private boolean mIsLoadingMore;

    private int mTotalPage = 0;

    private int mTotalNums = 0;

    private int mCurrentPage = 0;

    private final int PAGE_SIZE = 15;

    private ArrayList<NewFriendEntity.DataEntity.ResLst> dataSource  = new ArrayList<>();

    private int selPosition = 0;
    @Inject
    NewFriendPresenter mNewFriendPresenter;

    @Inject
    AcceptFriendPresenterImpl mAcceptFriendPresenterImpl;
    @OnClick({R.id.back,

    })
    public void OnClick(View v){
        Intent intent;
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
        return R.layout.activity_my_new_friend;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mNewFriendPresenter!=null)
            mNewFriendPresenter.onDestroy();

        if(mAcceptFriendPresenterImpl!=null)
            mAcceptFriendPresenterImpl.onDestroy();
    }

    @Override
    public void initViews() {
        mTargetId = getIntent().getStringExtra("mTargetId");
        midText.setText(getString(R.string.newFriend));
        mNewFriendPresenter.attachView(this);
        mNewFriendPresenter.setReqType(HostType.NEW_FRIEND);
        mAcceptFriendPresenterImpl.attachView(this);
        mAcceptFriendPresenterImpl.setReqType(HostType.ACCEPT_FRIEND);
        mBack.setImageResource(R.drawable.btn_back);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mCurrentPage = 0;
                mNewFriendPresenter.getNewFriendList(mCurrentPage,PAGE_SIZE);
            }
        });
        initRecycleView();
        mNewFriendPresenter.beforeRequest();
        mNewFriendPresenter.getNewFriendList(mCurrentPage,PAGE_SIZE);
    }

    private void initRecycleView() {


        mAdapter = new ReceiveFriendAdapter(dataSource,this);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                NewFriendEntity.DataEntity.ResLst entity  = dataSource.get(position);
                //点击item，如果已添加则跳转聊天
                if(!entity.getIsFriend().equals("0")){
                    String name = !TextUtils.isEmpty(entity.getNickname())?entity.getNickname():entity.getRealname();
                    RongIM.getInstance().startConversation(MyNewFriendActivity.this, Conversation.ConversationType.PRIVATE,entity.getUserId(),name);
                }
            }
        });
        mAdapter.setOnAcceptClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selPosition = position;
                //同意添加
                mAcceptFriendPresenterImpl.beforeRequest();
                mAcceptFriendPresenterImpl.acceptFriend(dataSource.get(position).getUserId());
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
                if (!mIsLoadingMore && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1) {
                    Log.i("mRecycleView","end");
                    mCurrentPage++;
                    if(mCurrentPage<mTotalPage){
                        mAdapter.showFooter();
                        mIsLoadingMore = true;
                        mRecycleView.scrollToPosition(mAdapter.getItemCount() - 1);
                        mNewFriendPresenter.getNewFriendList(mCurrentPage,PAGE_SIZE);

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
    protected void onResume() {
        super.onResume();
        if(mTargetId!=null && !mTargetId.equals("")){
            Event.SyncReadStatusEvent sync = new Event.SyncReadStatusEvent(Conversation.ConversationType.PRIVATE,mTargetId);
            RongContext.getInstance().getEventBus().post(sync);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void getNewFriendComplete(NewFriendEntity data) {
        if(data.getCode().equals("ok")){
            if(data.getData().getResLst() == null)
                return;
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
        if(reqType == HostType.NEW_FRIEND){
            mRefresh.setRefreshing(true);
        }else{
            if(mLoadDialog == null){
                mLoadDialog = DialogUtils.create(this, DialogUtils.TYPE_ADD);
                mLoadDialog.show();
            }
        }

    }

    @Override
    public void hideProgress(int reqType) {
        if(reqType == HostType.NEW_FRIEND){
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
        if(reqType == HostType.NEW_FRIEND){
            mRefresh.setRefreshing(false);
            if(mIsLoadingMore){
                mAdapter.hideFooter();
                mIsLoadingMore = false;
            }
            Toast.makeText(this,getString(R.string.loadFailed),Toast.LENGTH_SHORT).show();
        }else{
            if(mLoadDialog!=null){
                mLoadDialog.dismiss();
                mLoadDialog = null;
            }
            Toast.makeText(this,getString(R.string.addFriendFailed),Toast.LENGTH_SHORT).show();

        }



    }

    @Override
    public void acceptFriendCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            dataSource.get(selPosition).setIsFriend("1");
            mAdapter.notifyDataSetChanged();

            if(mLoadDialog!=null){
                mLoadDialog.dismiss();
                mLoadDialog = null;
            }
        }
    }
}
