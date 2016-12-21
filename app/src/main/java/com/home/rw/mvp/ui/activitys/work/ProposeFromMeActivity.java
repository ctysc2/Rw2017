package com.home.rw.mvp.ui.activitys.work;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.FragmentAdapter;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMeApprovingFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMePassedFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMeUnPassedFragment;
import com.home.rw.utils.DrawableUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.home.rw.utils.DrawableUtils.RADIUS_LEFT;
import static com.home.rw.utils.DrawableUtils.RADIUS_NONE;
import static com.home.rw.utils.DrawableUtils.RADIUS_RIGHT;

public class ProposeFromMeActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private static final int LEFT = 0;
    private static final int MIDDLE = 1;
    private static final int RIGHT = 2;

    private FragmentAdapter mAdapter;
    private Fragment passedFragment = new ProposeFromMePassedFragment();
    private Fragment unPassedFragment = new ProposeFromMeUnPassedFragment();
    private Fragment approvingFragment = new ProposeFromMeApprovingFragment();

    private List<Fragment> mFragmentList = new ArrayList<>();
    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.tv_left)
    TextView mTvLeft;

    @BindView(R.id.tv_mid)
    TextView mTvMid;

    @BindView(R.id.tv_right)
    TextView mTvRight;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.sperate2)
    View mSperate;

    @OnClick({
            R.id.tv_left,
            R.id.tv_right,
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
            default:
                break;


        }

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_propose_from_me;
    }

    @Override
    public void initInjector() {

    }


    @Override
    public void initViews() {
        midText.setText(R.string.fromme);
        mback.setImageResource(R.drawable.btn_back);
        mTvMid.setVisibility(View.VISIBLE);
        mSperate.setVisibility(View.VISIBLE);
        setSegmentSelect(LEFT);
        mTvLeft.setText(R.string.passed);
        mTvMid.setText(R.string.approving);
        mTvRight.setText(R.string.unpassed);

        mFragmentList.add(passedFragment);
        mFragmentList.add(approvingFragment);
        mFragmentList.add(unPassedFragment);



        mAdapter = new FragmentAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
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
            mTvRight.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#FFFFFF"),RADIUS_LEFT,10));


        }


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
