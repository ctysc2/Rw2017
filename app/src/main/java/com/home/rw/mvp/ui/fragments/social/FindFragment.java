package com.home.rw.mvp.ui.fragments.social;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.mvp.ui.activitys.social.CommListActivity;
import com.home.rw.mvp.ui.activitys.work.ApprovementActivity;
import com.home.rw.mvp.ui.activitys.work.CardActivity;
import com.home.rw.mvp.ui.activitys.work.DailyLogActivity;
import com.home.rw.mvp.ui.activitys.work.SendRollActivity;
import com.home.rw.mvp.ui.activitys.work.SignInActivity;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.utils.DimenUtil;
import com.home.rw.widget.AutoScrollViewPager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class FindFragment extends BaseFragment {


    @Inject
    Activity mActivity;

    @BindView(R.id.AutoScrollViewPager)
    AutoScrollViewPager autoScrollViewPager;

    @BindView(R.id.viewPagerIndicator)
    LinearLayout viewPagerIndicator;

    //indicate
    private ArrayList<View> mScrollImageViews = new ArrayList<>();

    //轮播图测试数据源
    private String[] testData = {
            "http://img9.cache.hxsd.com/game/2012/01/17/647054_1326796795_20.jpg",
            "http://img3.12349.net/13a4/12349.net_3y0kllulrxl.jpg",
            "http://shouyouph.onlyzhu.com/uploadfile/2013/0617/20130617032023116.jpg",
            "http://i0.wp.com/gearnuke.com/wp-content/uploads/2014/02/starcraft2_logo_cinematic.jpg?resize=798%2C350"
    };

    @OnClick({R.id.ll_proj,R.id.ll_activity,R.id.ll_culture,R.id.ll_coop})
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){

            case R.id.ll_proj:
                intent = new Intent(mActivity, CommListActivity.class);
                intent.putExtra("startType","Proj");
                startActivity(intent);
                break;
            case R.id.ll_activity:
                intent = new Intent(mActivity, CommListActivity.class);
                intent.putExtra("startType","Activity");
                startActivity(intent);
                break;
            case R.id.ll_culture:
                intent = new Intent(mActivity, CommListActivity.class);
                intent.putExtra("startType","Culture");
                startActivity(intent);
                break;
            case R.id.ll_coop:
                intent = new Intent(mActivity, CommListActivity.class);
                intent.putExtra("startType","Coop");
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
        initAutoScrollViewPager();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (autoScrollViewPager!=null) {
            autoScrollViewPager.startAutoScroll();
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        if (autoScrollViewPager!=null) {
            autoScrollViewPager.stopAutoScroll();
        }

    }

    @Override
    public void onDestroyView()
    {
//        if (viewPagerIndicator != null)
//            viewPagerIndicator.removeAllViews();
//        if (mScrollImageViews != null)
//            mScrollImageViews.clear();
        super.onDestroyView();
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
        addScrollImage(testData.length);

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
            //return mScrollImgDataList.size();
            return testData.length;
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
            //final ImageView view = new ImageView(container.getContext());

            final SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageURI(testData[position]);
            container.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

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
}
