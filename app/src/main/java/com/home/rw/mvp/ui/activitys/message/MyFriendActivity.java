package com.home.rw.mvp.ui.activitys.message;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.MyFriendEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.adapters.HomePagerAdapter;
import com.home.rw.mvp.ui.adapters.MyFriendAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class MyFriendActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    private ArrayList<MyFriendEntity.DataEntity> dataSource  = new ArrayList<>();

    private MyFriendAdapter mAdapter;

    private boolean mIsLoadingMore;
    View mHeader;

    @OnClick({R.id.back,
            R.id.rightText,

    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rightText:
                intent = new Intent(this, AddFriendIndex.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_friend;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.haoYou));
        rightText.setText(getString(R.string.addFriend));
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
        //dummy data
        MyFriendEntity.DataEntity entity1 = new MyFriendEntity.DataEntity();

        entity1.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=9db9758da6c27d1eb12b33967abcc60b/21a4462309f79052d1a480170ef3d7ca7bcbd564.jpg");
        entity1.setName("习近平");
        entity1.setDate("01-22");

        MyFriendEntity.DataEntity entity2 = new MyFriendEntity.DataEntity();

        entity2.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=31326f1bab18972bb737089887a410ec/f31fbe096b63f6248a446a0c8744ebf81a4ca3a0.jpg");
        entity2.setName("江泽民");
        entity2.setDate("01-20");

        MyFriendEntity.DataEntity entity3 = new MyFriendEntity.DataEntity();

        entity3.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=6d88c0a5cd177f3e0439f45f11a650a2/0bd162d9f2d3572cad9fcecf8e13632762d0c3a0.jpg");
        entity3.setName("胡锦涛");
        entity3.setDate("01-18");

        MyFriendEntity.DataEntity entity4 = new MyFriendEntity.DataEntity();

        entity4.setName("邓超");
        entity4.setDate("01-16");

        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity4);
        dataSource.add(entity3);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity3);
        dataSource.add(entity2);

        mHeader = LayoutInflater.from(this).inflate(R.layout.cell_friend_top, null, false);
        ((TextView)mHeader.findViewById(R.id.tv_friend_num)).setText("15");

        mAdapter = new MyFriendAdapter(dataSource,this);
        mAdapter.setIsShowHeader(true);
        mAdapter.setHeaderView(mHeader);
        mHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyFriendActivity.this,MyNewFriendActivity.class));
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RongIM.getInstance().startConversation(MyFriendActivity.this, Conversation.ConversationType.PRIVATE,"1",dataSource.get(position).getName());
            }
        });

        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
//        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//
//                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
//                        .findLastCompletelyVisibleItemPosition();
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//
//                if (!mIsLoadingMore && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
//                        && lastVisibleItemPosition >= totalItemCount - 1) {
//                    Log.i("mRecycleView","end");
//                    mAdapter.showFooter();
//                    mIsLoadingMore = true;
//                    mRecycleView.scrollToPosition(mAdapter.getItemCount() - 1);
//                    Observable.timer(2, TimeUnit.SECONDS).
//                            observeOn(AndroidSchedulers.mainThread()).
//                            subscribe(new Observer<Long>() {
//                                @Override
//                                public void onCompleted() {
//                                    mAdapter.hideFooter();
//                                    mIsLoadingMore = false;
//                                    MyFriendEntity.DataEntity entity3 = new MyFriendEntity.DataEntity();
//
//                                    entity3.setAvatar("https://imgsa.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=6d88c0a5cd177f3e0439f45f11a650a2/0bd162d9f2d3572cad9fcecf8e13632762d0c3a0.jpg");
//                                    entity3.setName("胡锦涛");
//                                    entity3.setDate("01-18");
//                                    ArrayList<MyFriendEntity.DataEntity> temp  = new ArrayList<>();
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    temp.add(entity3);
//                                    mAdapter.addMore(temp);
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//
//                                @Override
//                                public void onNext(Long aLong) {
//
//                                }
//                            });
//                }
//            }
//
//        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
}
