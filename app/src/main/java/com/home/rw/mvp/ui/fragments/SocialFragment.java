package com.home.rw.mvp.ui.fragments;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cty on 2016/12/13.
 */

public class SocialFragment extends BaseFragment {

    @BindView(R.id.back)
    ImageButton mBack;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @Inject
    Activity mActivity;

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        midText.setText(R.string.compSocial);
        rightText.setText(R.string.publish);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_social;
    }
}
