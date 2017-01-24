package com.home.rw.mvp.ui.activitys.message;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.ui.adapters.FragmentAdapter;
import com.home.rw.mvp.ui.fragments.message.CompanyReadedNotice;
import com.home.rw.mvp.ui.fragments.message.CompanyUnReadedNotice;
import com.home.rw.mvp.ui.fragments.mine.LastMonthOrderFragment;
import com.home.rw.mvp.ui.fragments.mine.LastWeekOrderFragment;
import com.home.rw.widget.ScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CompanyNoticeActivity extends BaseActivity implements ViewPager.OnPageChangeListener{


    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @Inject
    CompanyReadedNotice readFragment;
    @Inject
    CompanyUnReadedNotice unReadFragment;

    @BindView(R.id.tb_Top)
    TabLayout mTabLayout;

    @BindView(R.id.vp_view)
    ScrollViewPager mViewPager;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private FragmentAdapter mAdapter;

    @OnClick({R.id.back,
    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_notice;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.gongSi);
        mback.setImageResource(R.drawable.btn_back);


        mFragmentList.add(unReadFragment);
        mFragmentList.add(readFragment);

        mTitleList.add(getString(R.string.unReaded));
        mTitleList.add(getString(R.string.Readed));

        mAdapter = new FragmentAdapter(getSupportFragmentManager(),mFragmentList,mTitleList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
