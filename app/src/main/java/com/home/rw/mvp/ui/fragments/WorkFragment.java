package com.home.rw.mvp.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.mvp.entity.CarouselResponseEntity;
import com.home.rw.mvp.ui.activitys.work.ApprovementActivity;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.utils.DimenUtil;
import com.home.rw.utils.SystemTool;
import com.home.rw.widget.AutoScrollViewPager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cty on 2016/12/13.
 */

public class WorkFragment extends BaseFragment {

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.AutoScrollViewPager)
    AutoScrollViewPager autoScrollViewPager;

    @BindView(R.id.viewPagerIndicator)
    LinearLayout viewPagerIndicator;





    @Inject
    Activity mActivity;

    //indicate
    private ArrayList<View> mScrollImageViews = new ArrayList<>();

    //轮播图数据源
    private ArrayList<CarouselResponseEntity.DataEntity> mScrollImgDataList = new ArrayList<>();

    //轮播图测试数据源
    private String[] testData = {
            "http://img3.12349.net/13a4/12349.net_3y0kllulrxl.jpg",
            "http://imga1.pic21.com/bizhi/140904/08814/s07.jpg",
            "http://i0.wp.com/gearnuke.com/wp-content/uploads/2014/02/starcraft2_logo_cinematic.jpg?resize=798%2C350"
    };

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        midText.setText(R.string.compWork);
        initAutoScrollViewPager();

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_work;
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
            Log.i("lunbo","onPause");
            autoScrollViewPager.stopAutoScroll();
        }

    }

    @Override
    public void onDestroyView()
    {
        if (viewPagerIndicator != null)
            viewPagerIndicator.removeAllViews();
        if (mScrollImageViews != null)
            mScrollImageViews.clear();
        super.onDestroyView();
    }

    @OnClick({R.id.ll_signin,R.id.ll_dairy,R.id.ll_approve,R.id.ll_check,R.id.ll_roll})
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){

            case R.id.ll_signin:

                break;
            case R.id.ll_dairy:
                break;
            case R.id.ll_approve:
                intent = new Intent(mActivity, ApprovementActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_check:
                break;
            case R.id.ll_roll:
                break;
            default:
                break;


        }


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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)DimenUtil.dp2px(7), ViewGroup.LayoutParams.MATCH_PARENT);
            params.rightMargin = (int)DimenUtil.dp2px(10);
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
            Log.i("lunbo","instantiateItem");
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
