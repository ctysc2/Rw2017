package com.home.rw.mvp.ui.fragments.social;

import android.view.View;

import com.home.rw.R;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by cty on 2017/1/5.
 */

public class HomePageFragment extends BaseFragment {

    @Inject
    public HomePageFragment(){

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
        return R.layout.fragment_homepage;
    }
}
