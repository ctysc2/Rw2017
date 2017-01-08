package com.home.rw.mvp.ui.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.mvp.ui.adapters.FragmentAdapter;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;
import com.home.rw.mvp.ui.fragments.social.FindFragment;
import com.home.rw.mvp.ui.fragments.social.FocusFragment;
import com.home.rw.mvp.ui.fragments.social.HomePageFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMeApprovingFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMePassedFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMeUnPassedFragment;
import com.home.rw.utils.DrawableUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.home.rw.utils.DrawableUtils.RADIUS_LEFT;
import static com.home.rw.utils.DrawableUtils.RADIUS_NONE;
import static com.home.rw.utils.DrawableUtils.RADIUS_RIGHT;

/**
 * Created by cty on 2016/12/13.
 */

public class SocialFragment extends BaseFragment implements ViewPager.OnPageChangeListener{

    private static final int LEFT = 0;
    private static final int MIDDLE = 1;
    private static final int RIGHT = 2;

    private FragmentAdapter mAdapter;

    @Inject
    FindFragment mFindFragment;
    @Inject
    FocusFragment mFocusFragment;
    @Inject
    HomePageFragment mHomePageFragment;

    private List<Fragment> mFragmentList = new ArrayList<>();
    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.tv_left)
    TextView mTvLeft;

    @BindView(R.id.tv_mid)
    TextView mTvMid;

    @BindView(R.id.tv_right)
    TextView mTvRight;

    @Inject
    Activity mActivity;

    @Inject
    FragmentActivity mFragmentActivity;


    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.sperate2)
    View mSperate;

    @OnClick({
            R.id.tv_left,
            R.id.tv_right,
            R.id.rightText,
            R.id.tv_mid
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_left:
                setSegmentSelect(LEFT);
                mViewPager.setCurrentItem(LEFT);
                break;
            case R.id.tv_right:
                setSegmentSelect(RIGHT);
                mViewPager.setCurrentItem(RIGHT);
                break;
            case R.id.tv_mid:
                setSegmentSelect(MIDDLE);
                mViewPager.setCurrentItem(MIDDLE);
                break;
            case R.id.rightText:
                //发布
                break;
            default:
                break;


        }

    }
    private void setSegmentSelect(int index){
        if(LEFT == index){
            mTvLeft.setTextColor(Color.parseColor("#FFFFFF"));
            mTvLeft.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#3B41FF"),RADIUS_LEFT,10));

            mTvMid.setTextColor(Color.parseColor("#000000"));
            mTvMid.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#FFFFFF"),RADIUS_NONE,10));

            mTvRight.setTextColor(Color.parseColor("#000000"));
            mTvRight.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#FFFFFF"),RADIUS_RIGHT,10));


        }else if((RIGHT == index)){
            mTvLeft.setTextColor(Color.parseColor("#000000"));
            mTvLeft.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#FFFFFF"),RADIUS_LEFT,10));

            mTvMid.setTextColor(Color.parseColor("#000000"));
            mTvMid.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#FFFFFF"),RADIUS_NONE,10));

            mTvRight.setTextColor(Color.parseColor("#FFFFFF"));
            mTvRight.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#3B41FF"),RADIUS_RIGHT,10));

        }else{
            mTvLeft.setTextColor(Color.parseColor("#000000"));
            mTvLeft.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#FFFFFF"),RADIUS_LEFT,10));

            mTvMid.setTextColor(Color.parseColor("#FFFFFF"));
            mTvMid.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#3B41FF"),RADIUS_NONE,10));


            mTvRight.setTextColor(Color.parseColor("#000000"));
            mTvRight.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#FFFFFF"),RADIUS_RIGHT,10));


        }


    }
    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        midText.setText(R.string.compSocial);
        rightText.setText(R.string.publish);

        mTvMid.setVisibility(View.VISIBLE);
        mSperate.setVisibility(View.VISIBLE);
        setSegmentSelect(LEFT);
        mTvLeft.setText(R.string.find);
        mTvMid.setText(R.string.facus);
        mTvRight.setText(R.string.homepage);

        mFragmentList.add(mFindFragment);
        mFragmentList.add(mFocusFragment);
        mFragmentList.add(mHomePageFragment);



        mAdapter = new FragmentAdapter(mFragmentActivity.getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_social;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setSegmentSelect(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
