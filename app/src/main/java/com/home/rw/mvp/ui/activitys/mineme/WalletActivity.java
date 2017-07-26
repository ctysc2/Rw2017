package com.home.rw.mvp.ui.activitys.mineme;

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
import com.home.rw.mvp.ui.fragments.mine.AllOrderFragment;
import com.home.rw.mvp.ui.fragments.mine.CompanyWalletFragment;
import com.home.rw.mvp.ui.fragments.mine.LastMonthOrderFragment;
import com.home.rw.mvp.ui.fragments.mine.LastWeekOrderFragment;
import com.home.rw.mvp.ui.fragments.mine.PersonerWalletFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletActivity extends BaseActivity {

    private FragmentAdapter mAdapter;
    @Inject
    PersonerWalletFragment personerWalletFragment;
    @Inject
    CompanyWalletFragment companyWalletFragment;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;


    @BindView(R.id.ib_right)
    ImageButton mIbRight;

    @BindView(R.id.tb_Top)
    TabLayout mTabLayout;

    @BindView(R.id.vp_view)
    ViewPager mViewPager;

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
        return R.layout.activity_wallet;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.wallet);
        rightText.setVisibility(View.GONE);
        mback.setImageResource(R.drawable.btn_back);
        mIbRight.setVisibility(View.GONE);

        mFragmentList.add(personerWalletFragment);
        mFragmentList.add(companyWalletFragment);


        mTitleList.add(getString(R.string.personerWallet));
        mTitleList.add(getString(R.string.companyWallet));


        mAdapter = new FragmentAdapter(getSupportFragmentManager(),mFragmentList,mTitleList);
        mViewPager.setAdapter(mAdapter);

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
}
