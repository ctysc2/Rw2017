package com.home.rw.mvp.ui.activitys.message;

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

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.MyFriendEntity;
import com.home.rw.mvp.entity.ReceiveFriendEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.MyFriendAdapter;
import com.home.rw.mvp.ui.adapters.ReceiveFriendAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongContext;
import io.rong.imkit.model.Event;
import io.rong.imlib.model.Conversation;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class MyNewFriendActivity extends BaseActivity {

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
    private ArrayList<ReceiveFriendEntity.DataEntity> dataSource  = new ArrayList<>();
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

    }

    @Override
    public void initViews() {
        mTargetId = getIntent().getStringExtra("mTargetId");
        midText.setText(getString(R.string.newFriend));
        mBack.setImageResource(R.drawable.btn_back);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                Observable.timer(2, TimeUnit.SECONDS).
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

    private void initRecycleView() {
        ReceiveFriendEntity.DataEntity entity1 = new ReceiveFriendEntity.DataEntity();
        entity1.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=c5ea2c8dd000baa1ae214fe92679d277/63d0f703918fa0ec522d856c279759ee3c6ddbed.jpg");
        entity1.setTitle("小池彻平");
        entity1.setApproved(false);
        entity1.setSubTitle("小池です、友たちになれるのか？");

        ReceiveFriendEntity.DataEntity entity2 = new ReceiveFriendEntity.DataEntity();
        entity2.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=72239cb5524e9258b2398ebcfdebba3d/6609c93d70cf3bc7e43db93dd500baa1cd112a25.jpg");
        entity2.setTitle("苍井空");
        entity2.setApproved(false);
        entity2.setSubTitle("苍井です、よろしくね〜〜");

        ReceiveFriendEntity.DataEntity entity3 = new ReceiveFriendEntity.DataEntity();
        entity3.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike180%2C5%2C5%2C180%2C60/sign=e6c6c4a53ddbb6fd3156ed74684dc07d/b64543a98226cffca90bcfecbd014a90f603ea4f.jpg");
        entity3.setTitle("波多野结衣");
        entity3.setApproved(false);
        entity3.setSubTitle("私のこと知らないの？");

        ReceiveFriendEntity.DataEntity entity4 = new ReceiveFriendEntity.DataEntity();
        entity4.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=1e12fb42b351f819e5280b18bbdd2188/b3b7d0a20cf431adecef3e504836acaf2edd989d.jpg");
        entity4.setTitle("柚木ティナ");
        entity4.setApproved(false);


        ReceiveFriendEntity.DataEntity entity5 = new ReceiveFriendEntity.DataEntity();
        entity5.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=410c5f8a3ca85edfee81f671283d6246/f703738da97739124e26b3bbf0198618377ae2ec.jpg");
        entity5.setTitle("黑崎一护");
        entity5.setApproved(true);
        entity5.setSubTitle("hi~");


        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity4);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity4);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity4);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity4);
        dataSource.add(entity4);
        dataSource.add(entity5);

        mAdapter = new ReceiveFriendAdapter(dataSource,this);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                dataSource.get(position).setApproved(true);
                mAdapter.notifyDataSetChanged();
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
                                    ReceiveFriendEntity.DataEntity entity5 = new ReceiveFriendEntity.DataEntity();
                                    entity5.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=410c5f8a3ca85edfee81f671283d6246/f703738da97739124e26b3bbf0198618377ae2ec.jpg");
                                    entity5.setTitle("黑崎一护");
                                    entity5.setApproved(true);
                                    entity5.setSubTitle("hi~");
                                    ArrayList<ReceiveFriendEntity.DataEntity> temp = new ArrayList<ReceiveFriendEntity.DataEntity>();
                                    temp.add(entity5);
                                    temp.add(entity5);
                                    temp.add(entity5);
                                    temp.add(entity5);
                                    temp.add(entity5);
                                    temp.add(entity5);
                                    temp.add(entity5);
                                    temp.add(entity5);
                                    temp.add(entity5);
                                    temp.add(entity5);
                                    temp.add(entity5);
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
}
