package com.home.rw.mvp.ui.fragments.social;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.common.HostType;
import com.home.rw.mvp.entity.LinkedEntity;
import com.home.rw.mvp.presenter.impl.LinkedPresenterImpl;
import com.home.rw.mvp.ui.activitys.WebViewActivity;
import com.home.rw.mvp.ui.activitys.message.LandMarkDetail;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.activitys.social.CommListActivity;
import com.home.rw.mvp.ui.activitys.work.ApprovementActivity;
import com.home.rw.mvp.ui.activitys.work.CardActivity;
import com.home.rw.mvp.ui.activitys.work.DailyLogActivity;
import com.home.rw.mvp.ui.activitys.work.SendRollActivity;
import com.home.rw.mvp.ui.activitys.work.SignInActivity;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.mvp.view.LinkedView;
import com.home.rw.utils.DimenUtil;
import com.home.rw.utils.ImageHelper;
import com.home.rw.widget.AutoScrollViewPager;
import com.home.rw.widget.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class FindFragment extends BaseFragment implements LinkedView {
    @Inject
    Activity mActivity;

    @BindView(R.id.AutoScrollViewPager)
    AutoScrollViewPager autoScrollViewPager;

    @BindView(R.id.viewPagerIndicator)
    LinearLayout viewPagerIndicator;

    @BindView(R.id.iv_ad)
    ImageView mAD;

    //indicate
    private ArrayList<View> mScrollImageViews = new ArrayList<>();

    //轮播图测试数据源
    private ArrayList<LinkedEntity.DataEntity> data;

    @Inject
    LinkedPresenterImpl mLinkedPresenterImpl;

    @Inject
    LinkedPresenterImpl mLinkedPresenterImpl2;
    @OnClick({R.id.ll_proj,R.id.ll_activity,R.id.ll_culture,R.id.ll_coop})
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){

            case R.id.ll_proj:
                intent = new Intent(mActivity, CommListActivity.class);
                intent.putExtra("startType",0);
                startActivity(intent);
                break;
            case R.id.ll_activity:
                intent = new Intent(mActivity, CommListActivity.class);
                intent.putExtra("startType",1);
                startActivity(intent);
                break;
            case R.id.ll_culture:
                intent = new Intent(mActivity, CommListActivity.class);
                intent.putExtra("startType",2);
                startActivity(intent);
                break;
            case R.id.ll_coop:
                intent = new Intent(mActivity, CommListActivity.class);
                intent.putExtra("startType",3);
                startActivity(intent);
                break;
            default:
                break;


        }


    }
    @Inject
    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        mLinkedPresenterImpl.attachView(this);
        mLinkedPresenterImpl2.attachView(this);
        mLinkedPresenterImpl.setReqType(HostType.OUT_LINK2);
        mLinkedPresenterImpl.getLinked();

        mLinkedPresenterImpl2.setReqType(HostType.OUT_LINK3);
        mLinkedPresenterImpl2.getLinked();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (autoScrollViewPager!=null) {
                autoScrollViewPager.startAutoScroll();
            }
        }else{
            if (autoScrollViewPager!=null) {
                autoScrollViewPager.stopAutoScroll();
            }
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mLinkedPresenterImpl!=null)
            mLinkedPresenterImpl.onDestroy();

        if(mLinkedPresenterImpl2!=null)
            mLinkedPresenterImpl2.onDestroy();

    }

    /**
     * 初始化轮播图控件
     */
    private void initAutoScrollViewPager()
    {
        autoScrollViewPager.setAdapter(mPagerAdapter);


        autoScrollViewPager.setScrollFactgor(10); // 控制滑动速度
        autoScrollViewPager.setOffscreenPageLimit(6);
        autoScrollViewPager.startAutoScroll(3000);

        autoScrollViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int arg0)
            {
                showSelectScrollImage(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {

            }
        });
        //for test
        addScrollImage(data.size());

    }
    /**
     * 轮播图底部的下划线
     *
     * @param size
     */
    private void addScrollImage(int size)
    {
        viewPagerIndicator.removeAllViews();
        mScrollImageViews.clear();

        for (int i = 0; i < size; i++)
        {
            View iv = new View(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) DimenUtil.dp2px(5), ViewGroup.LayoutParams.MATCH_PARENT);
            params.rightMargin = (int)DimenUtil.dp2px(7);
            if (i != 0)
            {
                iv.setBackgroundResource(R.drawable.shape_carousel_indigcate);
            } else
            {
                iv.setBackgroundResource(R.drawable.shape_carousel_indigcatesel);
            }
            iv.setLayoutParams(params);
            viewPagerIndicator.addView(iv);// 将图片加到一个布局里
            mScrollImageViews.add(iv);
        }
    }
    /**
     * 当前滑动的轮播图对应底部的下划线
     * @param position
     */
    private void showSelectScrollImage(int position)
    {
        if (position<0||position>=mScrollImageViews.size())  return;
        if (mScrollImageViews != null)
        {
            for (View iv : mScrollImageViews)
            {
                iv.setBackgroundResource(R.drawable.shape_carousel_indigcate);
            }
            mScrollImageViews.get(position).setBackgroundResource(R.drawable.shape_carousel_indigcatesel);
        }
    }
    /**
     * 轮播图适配器
     */
    PagerAdapter mPagerAdapter = new PagerAdapter()
    {
        @Override
        public int getCount()
        {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup container,
                                      final int position)
        {
            final ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            final LinkedEntity.DataEntity entity = data.get(position);
            ImageHelper.getInstance().displayWithoutPs(
                    imageView,
                    entity.getImgs());
            container.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent;
                    if(entity.getType().equals("0")){
                        intent = new Intent(mActivity, WebViewActivity.class);
                        intent.putExtra("title",entity.getTitle());
                        intent.putExtra("url",entity.getToLink());
                        startActivity(intent);
                    }else if(entity.getType().equals("1")){
                        intent = new Intent(mActivity, LandMarkDetail.class);
                        intent.putExtra("id",entity.getId());
                        startActivity(intent);
                    }else {
                        intent = new Intent(mActivity, CommDetailActivity.class);
                        intent.putExtra("id",entity.getId());
                        startActivity(intent);
                    }
                }

            });
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object)
        {
            container.removeView((View) object);
        }
    };

    @Override
    public void getLinkedCompleted(int reqType,LinkedEntity data) {
        if(data.getCode().equals("ok")){

            if(data.getData().size() == 0)
                return;

            switch (reqType){
                case HostType.OUT_LINK2:
                    this.data = data.getData();
                    initAutoScrollViewPager();
                    break;
                case HostType.OUT_LINK3:
                    final LinkedEntity.DataEntity entity = data.getData().get(0);
                    Glide.with(this).load(data.getData().get(0).getImgs()).into(mAD);
                    mAD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            if(entity.getType().equals("0")){
                                intent = new Intent(mActivity, WebViewActivity.class);
                                intent.putExtra("title",entity.getTitle());
                                intent.putExtra("url",entity.getToLink());
                                startActivity(intent);
                            }else if(entity.getType().equals("1")){
                                intent = new Intent(mActivity, LandMarkDetail.class);
                                intent.putExtra("id",entity.getId());
                                startActivity(intent);
                            }else {
                                intent = new Intent(mActivity, CommDetailActivity.class);
                                intent.putExtra("id",entity.getId());
                                startActivity(intent);
                            }
                        }
                    });
                    break;
            }

        }
    }

    @Override
    public void showProgress(int reqType) {

    }

    @Override
    public void hideProgress(int reqType) {

    }

    @Override
    public void showErrorMsg(int reqType, String msg) {

    }
}
