package com.home.rw.mvp.ui.fragments.mine;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.rw.R;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class LastMonthOrderFragment extends BaseFragment {


    @Inject
    Activity mActivity;

    @Inject
    public LastMonthOrderFragment() {
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
        return R.layout.fragment_last_month_order;
    }

}
