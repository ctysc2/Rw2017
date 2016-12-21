package com.home.rw.mvp.ui.fragments.work;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.rw.R;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by cty on 2016/12/20.
 */

public class ProposeFromMePassedFragment extends BaseFragment {
    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {

    }

    @Inject
    Activity mActivity;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_proposepassed;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
