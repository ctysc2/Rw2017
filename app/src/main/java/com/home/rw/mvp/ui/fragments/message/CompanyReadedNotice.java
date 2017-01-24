package com.home.rw.mvp.ui.fragments.message;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.rw.R;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.CompanyNoticeEntity;
import com.home.rw.mvp.ui.activitys.message.CompanyNoticeDetailActivity;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.adapters.CompanyNoticeListAdapter;
import com.home.rw.mvp.ui.adapters.HomePagerAdapter;
import com.home.rw.mvp.ui.adapters.RecycleViewSperate;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.utils.DialogUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;


public class CompanyReadedNotice extends BaseFragment implements AlertDialogListener {

    private ArrayList<CompanyNoticeEntity.DataEntity> dataSource  = new ArrayList<>();

    private CompanyNoticeListAdapter mAdapter;

    private boolean mIsLoadingMore;

    private int selectPositon;

    @BindView(R.id.rv_list)
    RecyclerView mRecycleView;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout mRefresh;

    @Inject
    Activity mActivity;

    @Inject
    public CompanyReadedNotice() {

    }


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

    private void initRecycleView() {

        CompanyNoticeEntity.DataEntity child1 = new CompanyNoticeEntity.DataEntity();

        child1.setTitle("开始工作啦");
        child1.setDate("2017.01.11");
        child1.setContent("年终总结是人们对一年来的工作学习进行回顾和分析，从中找出经验和教训，引出规律性认识，以指导今后工作和实践活动的一种应用文体。年终总结的内容包括一年来的情况概述、成绩和经验、存在的问题和教训、今后努力的方向。年终总结可以大体分为：单位总结、个人总结、综合性总结、专题总结等。");

        CompanyNoticeEntity.DataEntity child2 = new CompanyNoticeEntity.DataEntity();

        child2.setTitle("开始吃烤肉啦");
        child2.setDate("2017.01.05");
        child2.setContent("烧烤恐怕是人类最早接触到的加工熟食的方法。据记载，伏羲作为中国文明代表人物三皇之首，《三字经》中有自羲农，至黄帝，号三皇据上世。创造精神是伏羲文化精神。在远古时代，河里、湖泊里、海里有许多鱼，天空有许多鸟，地上有许多兽类，人们不会捕捉。人们手提一根树干在水边等着，看着鱼游过来就打一棒子，靠这种方法偶尔才能捕到几条鱼。伏羲将野麻，晒成干搓成绳，然后用细一些的绳子编成网，教人们捕鱼；用粗一些的绳子编成网，教人们捕鸟捕兽。这比只吃树上野果要好多了，但是生鱼生鸟吃起来味道并不好，严重的是有的弄不好，吃了还要闹肚子生病。当伏羲取来天火后，便教人们用火把鸟儿、鱼儿烤熟了吃。从此，人们吃着香喷喷的烤肉，身体也就更健康了。为了纪念伏羲，人们把他称庖牺即第一个用火烤熟兽肉的人，这也便成为了烧烤的雏形。\n" +
                " \n" +
                "具体来说，中国饮食、烹饪经过了火烹、石烹、水烹、油烹四个发展阶段，而火烹是最原始的烹调方法。火烹最原始的操作方法就是烧、烤。\n" +
                "烤有两种解释：一是使东西着火燃烧，例如烧火、烧木柴等；\n" +
                "二是用火或者是发热的东西是物品\n" +
                "受热其变化，例如烧水、烧炭等。第二层意思是一种深引，一种烹饪的烧。烧烤\n" +
                "包含：熏、烘烤、烙。考古专家在鲁南临沂市内五里堡村出土的一座东汉晚期画像石残墓中发现两方刻有烤肉串的画像石，\n" +
                "经研究发现这两幅画中所见的人物形象皆汉人，他们烤的肉串是牛羊肉串，这两幅庖厨图反映出1800\n" +
                "年前鲁南民间饮食风俗。");
        CompanyNoticeEntity.DataEntity child3 = new CompanyNoticeEntity.DataEntity();


        child3.setTitle("开始放假啦");
        child3.setDate("2016.12.31");
        child3.setContent("春节是指汉字文化圈传统上的农历新年，俗称“年节”，传统名称为新年、大年、新岁，但口头上又称度岁、庆新岁、过年。[1]  中国人过春节已有4000多年的历史。在现代，人们把春节定于农历正月初一，但一般至少要到正月十五（上元节）新年才算结束，在民间，传统意义上的春节是指从腊月的腊祭或腊月二十三或二十四的祭灶，一直到正月十九。\n" +
                        "在春节期间，中国的汉族和一些少数民族都要举行各种庆祝活动。这些活动均以祭祀祖神、祭奠祖先、除旧布新、迎禧接福、祈求丰年为主要内容，形式丰富多彩，带有浓郁的各民族特色。受到中华文化的影响，属于汉字文化圈的一些国家和民族也有庆祝春节的习俗。人们在春节这一天都尽可能地回到家里和亲人团聚，表达对未来一年的热切期盼和对新一年生活的美好祝福");

        dataSource.add(child1);
        dataSource.add(child2);
        dataSource.add(child3);
        dataSource.add(child3);
        dataSource.add(child2);
        dataSource.add(child3);
        dataSource.add(child2);
        dataSource.add(child3);
        dataSource.add(child3);
        dataSource.add(child2);
        dataSource.add(child3);
        dataSource.add(child2);
        dataSource.add(child3);
        dataSource.add(child3);
        dataSource.add(child2);
        dataSource.add(child3);
        dataSource.add(child2);
        dataSource.add(child3);

        mAdapter = new CompanyNoticeListAdapter(dataSource,mActivity);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mActivity,CompanyNoticeDetailActivity.class);
                intent.putExtra("data",dataSource.get(position));
                startActivity(intent);
            }
        });
        mAdapter.setOnItemDeleteListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectPositon = position;
                mAlertDialog = DialogUtils.create(mActivity,DialogUtils.TYPE_ALERT);
                mAlertDialog.show(CompanyReadedNotice.this,getString(R.string.noticedelHint1));
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
                                    CompanyNoticeEntity.DataEntity child3 = new CompanyNoticeEntity.DataEntity();


                                    child3.setTitle("开始放假啦");
                                    child3.setDate("2016.12.31");
                                    child3.setContent("春节是指汉字文化圈传统上的农历新年，俗称“年节”，传统名称为新年、大年、新岁，但口头上又称度岁、庆新岁、过年。[1]  中国人过春节已有4000多年的历史。在现代，人们把春节定于农历正月初一，但一般至少要到正月十五（上元节）新年才算结束，在民间，传统意义上的春节是指从腊月的腊祭或腊月二十三或二十四的祭灶，一直到正月十九。\n" +
                                            "在春节期间，中国的汉族和一些少数民族都要举行各种庆祝活动。这些活动均以祭祀祖神、祭奠祖先、除旧布新、迎禧接福、祈求丰年为主要内容，形式丰富多彩，带有浓郁的各民族特色。受到中华文化的影响，属于汉字文化圈的一些国家和民族也有庆祝春节的习俗。人们在春节这一天都尽可能地回到家里和亲人团聚，表达对未来一年的热切期盼和对新一年生活的美好祝福");

                                    ArrayList<CompanyNoticeEntity.DataEntity> temp  = new ArrayList<>();
                                    temp.add(child3);
                                    temp.add(child3);
                                    temp.add(child3);
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
    public int getLayoutId() {
        return R.layout.fragment_company_readed_notice;
    }


    @Override
    public void onConFirm() {
        mAlertDialog.dismiss();
        mAdapter.delete(selectPositon);
    }

    @Override
    public void onCancel() {
        mAlertDialog.dismiss();
    }
}
