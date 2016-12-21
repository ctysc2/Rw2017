package com.home.rw.mvp.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.mvp.ui.activitys.SignInActivity;
import com.home.rw.mvp.ui.fragments.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cty on 2016/12/13.
 */

public class MessageFragment extends BaseFragment {
    @BindView(R.id.back)
    ImageButton mScan;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @Inject
    Activity mActivity;

    @OnClick({R.id.back})
    public void OnClick(View v){

        switch (v.getId()){
            case R.id.back:
                startActivity(new Intent(mActivity,SignInActivity.class));
                break;
            default:
                break;
        }

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void initViews(View view) {
        midText.setText(R.string.messageTitle);
        rightText.setText(R.string.more);
        mScan.setImageResource(R.drawable.btn_scan);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }
}
