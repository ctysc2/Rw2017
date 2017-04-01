package com.home.rw.mvp.ui.fragments.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.di.component.DaggerFragmentComponent;
import com.home.rw.di.component.FragmentComponent;
import com.home.rw.di.module.FragmentModule;
import com.home.rw.mvp.presenter.base.BasePresenter;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.DimenUtil;
import com.home.rw.utils.FrescoUtils;
import com.home.rw.utils.RxBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by cty on 2016/12/13.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }

    protected FragmentComponent mFragmentComponent;
    protected T mPresenter;
    protected DialogUtils mAlertDialog;
    protected DialogUtils mLoadDialog;
    private View mFragmentView;

    public abstract void initInjector();

    public abstract void initViews(View view);

    public abstract int getLayoutId();

    protected Subscription mSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((App) getActivity().getApplication()).getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
        initInjector();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mFragmentView);
            initViews(mFragmentView);
            adaptToolBarHeight();
        }
        return mFragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        //FrescoUtils.clearCache();
        RxBus.cancelSubscription(mSubscription);
    }

    private  void adaptToolBarHeight() {
       if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){

           Toolbar toolbar = (Toolbar) mFragmentView.findViewById(R.id.toolbar);
           Log.i("cty", "toolbar:" + toolbar);
           if (toolbar != null) {
               toolbar.setPadding(0, 0, 0, 0);
               RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();

               params.height = (int)DimenUtil.dp2px(44);
               toolbar.setLayoutParams(params);

           }
       }

    }
}
