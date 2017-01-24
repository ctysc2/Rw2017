package com.home.rw.mvp.ui.fragments.work;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.entity.LogEntity;
import com.home.rw.mvp.ui.adapters.ApprovementListAdapter;
import com.home.rw.mvp.ui.adapters.ReceivedLogListAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

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

public class SendLogFragment extends BaseFragment {

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    private boolean mIsLoadingMore;

    private ReceivedLogListAdapter mAdapter;

    private ArrayList<LogEntity.DataEntity> dataSource  = new ArrayList<>();

    @Inject
    public SendLogFragment(){

    }
    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @Inject
    Activity mActivity;

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
                Observable.timer(1, TimeUnit.SECONDS).
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_send_log;
    }

    private void initRecycleView(){
        //假数据
        LogEntity.DataEntity entity1 = new LogEntity.DataEntity();
        entity1.setHeadUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1482859226&di=2602769e6d195b1c0ce52cc9929841e5&src=http://bbs.niuyou5.com/data/attachment/forum/201601/31/002052d2liyp2ex95e79yy.jpg");
        entity1.setName("黄旭东");
        entity1.setDate("09月13日 13:13");
        entity1.setContent("干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东," +
                "干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东," +
                "干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东");

        LogEntity.DataEntity entity2 = new LogEntity.DataEntity();
        entity2.setHeadUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1482859226&di=2602769e6d195b1c0ce52cc9929841e5&src=http://bbs.niuyou5.com/data/attachment/forum/201601/31/002052d2liyp2ex95e79yy.jpg");
        entity2.setName("孙一峰");
        entity2.setDate("09月23日 13:16");
        entity2.setContent("干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东," +
                "干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东," +
                "干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东");


        LogEntity.DataEntity entity3 = new LogEntity.DataEntity();
        entity3.setHeadUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1482859226&di=2602769e6d195b1c0ce52cc9929841e5&src=http://bbs.niuyou5.com/data/attachment/forum/201601/31/002052d2liyp2ex95e79yy.jpg");
        entity3.setName("no总");
        entity3.setDate("09月13日 13:13");
        entity3.setContent("干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东," +
                "干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东," +
                "干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东");


        LogEntity.DataEntity entity4 = new LogEntity.DataEntity();
        entity4.setHeadUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1482859226&di=2602769e6d195b1c0ce52cc9929841e5&src=http://bbs.niuyou5.com/data/attachment/forum/201601/31/002052d2liyp2ex95e79yy.jpg");
        entity4.setName("mayuki");
        entity4.setDate("09月13日 13:13");
        entity4.setContent("干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东," +
                "干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东," +
                "干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东/n,干死黄旭东,干死黄旭东" +
                ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东");

        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity3);
        dataSource.add(entity4);

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
                                    LogEntity.DataEntity entity3 = new LogEntity.DataEntity();
                                    entity3.setHeadUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1482859226&di=2602769e6d195b1c0ce52cc9929841e5&src=http://bbs.niuyou5.com/data/attachment/forum/201601/31/002052d2liyp2ex95e79yy.jpg");
                                    entity3.setName("no总");
                                    entity3.setDate("09月13日 13:13");
                                    entity3.setContent("干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东," +
                                            "干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东," +
                                            "干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                                            ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                                            ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东" +
                                            ",干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东,干死黄旭东");

                                    ArrayList<LogEntity.DataEntity> temp  = new ArrayList<>();
                                    temp.add(entity3);
                                    temp.add(entity3);
                                    temp.add(entity3);
                                    temp.add(entity3);
                                    temp.add(entity3);
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
}
