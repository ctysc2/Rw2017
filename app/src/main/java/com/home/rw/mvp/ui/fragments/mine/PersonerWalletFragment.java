package com.home.rw.mvp.ui.fragments.mine;

import android.app.Activity;
import android.view.View;

import com.home.rw.R;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by cty on 2017/1/4.
 */

public class PersonerWalletFragment extends BaseFragment {

    @Inject
    Activity mActivity;

    @Inject
    public PersonerWalletFragment() {
        // Required empty public constructor

    }


    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personer_wallet;
    }
}
