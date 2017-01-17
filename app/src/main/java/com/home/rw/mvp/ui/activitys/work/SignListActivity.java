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

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.RollMeEntity;
import com.home.rw.mvp.entity.SignEntity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.adapters.RollMelistAdapter;
import com.home.rw.mvp.ui.adapters.SignListAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class SignListActivity extends BaseActivity {

    private boolean mIsLoadingMore;

    private SignListAdapter mAdapter;

    private ArrayList<SignEntity.DataEntity> dataSource  = new ArrayList<>();

    //http://p1.qhimg.com/dmsmty/350_200_/t01cf5a5108bd50b4fc.jpg

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

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
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initRecycleView();
    }

    private void initRecycleView() {

        SignEntity.DataEntity entity1 = new SignEntity.DataEntity();
        entity1.setHeader("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1482949311&di=2cf75ddae16bd743f5e8453ed12e8dc9&src=http://a2.att.hudong.com/31/34/01300001128119142116348739631_s.jpg");
        entity1.setName("Miss");
        entity1.setTime("2016年11月12日 09:38");
        entity1.setAddress("上海市清涧路");

        SignEntity.DataEntity entity2 = new SignEntity.DataEntity();
        entity2.setHeader("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1482949311&di=2cf75ddae16bd743f5e8453ed12e8dc9&src=http://a2.att.hudong.com/31/34/01300001128119142116348739631_s.jpg");
        entity2.setName("Miss");
        entity2.setTime("2016年11月13日 08:37");
        entity2.setAddress("人民广场");
        dataSource.add(entity1);
        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity2);
        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity1);
        dataSource.add(entity2);
        dataSource.add(entity1);
        dataSource.add(entity2);

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
                        .findLastVisibleItemPosition();
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

                                    SignEntity.DataEntity entity2 = new SignEntity.DataEntity();
                                    entity2.setHeader("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1482949311&di=2cf75ddae16bd743f5e8453ed12e8dc9&src=http://a2.att.hudong.com/31/34/01300001128119142116348739631_s.jpg");
                                    entity2.setName("Miss");
                                    entity2.setTime("2016年11月13日 08:37");
                                    entity2.setAddress("人民广场");
                                    ArrayList<SignEntity.DataEntity> temp  = new ArrayList<>();
                                    temp.add(entity2);
                                    temp.add(entity2);
                                    temp.add(entity2);
                                    temp.add(entity2);
                                    temp.add(entity2);
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
