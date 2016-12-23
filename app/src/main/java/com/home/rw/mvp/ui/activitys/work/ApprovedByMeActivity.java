package com.home.rw.mvp.ui.activitys.work;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.event.BeforeReadEvent;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.FragmentAdapter;
import com.home.rw.mvp.ui.fragments.work.ApproveByMeAfterFragment;
import com.home.rw.mvp.ui.fragments.work.ApproveByMeBeforeFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMeApprovingFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMePassedFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMeUnPassedFragment;
import com.home.rw.utils.DrawableUtils;
import com.home.rw.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

import static com.home.rw.utils.DrawableUtils.RADIUS_LEFT;
import static com.home.rw.utils.DrawableUtils.RADIUS_NONE;
import static com.home.rw.utils.DrawableUtils.RADIUS_RIGHT;

public class ApprovedByMeActivity extends BaseActivity implements ViewPager.OnPageChangeListener{


    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    private FragmentAdapter mAdapter;
    @Inject
    ApproveByMeBeforeFragment approveBeforeFragment;
    @Inject
    ApproveByMeAfterFragment approveAfterFragment;

    private List<Fragment> mFragmentList = new ArrayList<>();
    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.tv_left)
    TextView mTvLeft;

    @BindView(R.id.tv_right)
    TextView mTvRight;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @OnClick({
            R.id.tv_left,
            R.id.tv_right,
            R.id.back
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
            case R.id.back:
                finish();
            default:
                break;


        }

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_approved_by_me;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.approvebyme);
        mback.setImageResource(R.drawable.btn_back);
        setSegmentSelect(LEFT);
        mTvLeft.setText(R.string.daiwoshenpi);
        mTvRight.setText(R.string.woyishenpi);

        mFragmentList.add(approveBeforeFragment);
        mFragmentList.add(approveAfterFragment);


        mAdapter = new FragmentAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        registerBeforeNum();
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

    private void registerBeforeNum() {
        mSubscription = RxBus.getInstance().toObservable(BeforeReadEvent.class)
                .subscribe(new Action1<BeforeReadEvent>() {
                    @Override
                    public void call(BeforeReadEvent befreRead) {
                        mTvLeft.setText(getResources().getString(R.string.daiwoshenpi)+"("+befreRead.getNumber()+")");
                    }
                });
    }
    private void setSegmentSelect(int index){
        if(LEFT == index){
            mTvLeft.setTextColor(Color.parseColor("#FFFFFF"));
            mTvLeft.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#3B41FF"),RADIUS_LEFT,10));

            mTvRight.setTextColor(Color.parseColor("#000000"));
            mTvRight.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#FFFFFF"),RADIUS_RIGHT,10));


        }else if((RIGHT == index)){
            mTvLeft.setTextColor(Color.parseColor("#000000"));
            mTvLeft.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#FFFFFF"),RADIUS_LEFT,10));

            mTvRight.setTextColor(Color.parseColor("#FFFFFF"));
            mTvRight.setBackground(DrawableUtils.getShapeDrawable(Color.parseColor("#3B41FF"),RADIUS_RIGHT,10));

        }


    }
}
