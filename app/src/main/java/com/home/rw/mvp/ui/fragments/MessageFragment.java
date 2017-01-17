package com.home.rw.mvp.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.MessegeMainEntity;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.adapters.CommunicationAdapter;
import com.home.rw.mvp.ui.adapters.HomePagerAdapter;
import com.home.rw.mvp.ui.adapters.MessegeMainAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cty on 2016/12/13.
 */

public class MessageFragment extends BaseFragment {

    private MessegeMainAdapter mAdapter;

    @BindView(R.id.back)
    ImageButton mScan;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @Inject
    Activity mActivity;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    ArrayList<MessegeMainEntity.DataEntity> dataSource = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        midText.setText(R.string.messageTitle);
        rightText.setText(R.string.more);
        mScan.setImageResource(R.drawable.btn_scan);
        initRecycleView();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initRecycleView() {

        ArrayList<MessegeMainEntity.DataEntity> subData = new ArrayList<>();

        MessegeMainEntity.DataEntity child1 = new MessegeMainEntity.DataEntity(
                "0",
                0,
                "商务电话",
                "最近通话:小李",
                "10-28",
                null,
                false
        );
        MessegeMainEntity.DataEntity child2 = new MessegeMainEntity.DataEntity(
                "0",
                0,
                "我的好友",
                "最近通话:小陈",
                "10-29",
                null,
                false
        );
        MessegeMainEntity.DataEntity child3 = new MessegeMainEntity.DataEntity(
                "0",
                0,
                "置地公告",
                null,
                "10-28",
                null,
                false
        );
        MessegeMainEntity.DataEntity child4 = new MessegeMainEntity.DataEntity(
                "0",
                0,
                "公司公告",
                null,
                "10-28",
                null,
                false
        );
        MessegeMainEntity.DataEntity child5 = new MessegeMainEntity.DataEntity(
                "0",
                0,
                "企业通讯录",
                null,
                null,
                null,
                false
        );
        MessegeMainEntity.DataEntity child6 = new MessegeMainEntity.DataEntity(
                "0",
                0,
                "常用联系人",
                null,
                null,
                subData,
                false
        );


        MessegeMainEntity.DataEntity subchild1 = new MessegeMainEntity.DataEntity(
                "0",
                0,
                "张依旧",
                "13736239384",
                "10-28",
                null,
                false
        );
        MessegeMainEntity.DataEntity subchild2 = new MessegeMainEntity.DataEntity(
                "0",
                0,
                "陈无人",
                "13947589484",
                "10-29",
                null,
                false
        );
        MessegeMainEntity.DataEntity subchild3 = new MessegeMainEntity.DataEntity(
                "0",
                0,
                "导师",
                "15543245867",
                "10-28",
                null,
                false
        );

        subData.add(subchild1);
        subData.add(subchild2);
        subData.add(subchild3);
        subData.add(subchild1);
        subData.add(subchild2);
        subData.add(subchild3);
        subData.add(subchild2);
        subData.add(subchild3);
        subData.add(subchild1);
        subData.add(subchild1);
        subData.add(subchild2);
        subData.add(subchild2);
        subData.add(subchild3);
        subData.add(subchild1);
        subData.add(subchild3);
        dataSource.add(child1);
        dataSource.add(child2);
        dataSource.add(child3);
        dataSource.add(child4);
        dataSource.add(child5);
        dataSource.add(child6);


        saveSetions();

        mAdapter = new MessegeMainAdapter(dataSource,mActivity);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MessegeMainEntity.DataEntity entity = dataSource.get(position);
                if(entity.getChilds()!=null){
                    if(entity.isExpanded()){
                        dataSource.removeAll(entity.getChilds());
                      entity.setExpanded(false);
                    }else{
                        dataSource.addAll(position+1,entity.getChilds());
                      entity.setExpanded(true);
                    }
                   mAdapter.notifyDataSetChanged();
                }
            }
        });

        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity,
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

                if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1) {
                    Log.i("mRecycleView","end");
                }
            }

        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }


    //遍历并保存setion属性
    private void saveSetions(){
        for(int i = 0;i<dataSource.size();i++){
            MessegeMainEntity.DataEntity child = dataSource.get(i);
            child.setType(0);
            if(child.getChilds()!=null){
                for(int j = 0;j<child.getChilds().size();j++){
                    MessegeMainEntity.DataEntity subChild = child.getChilds().get(j);
                    subChild.setType(1);
                }
            }


        }
    }
}
