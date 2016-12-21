package com.home.rw.mvp.presenter.base;

import android.support.annotation.NonNull;


import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.view.base.BaseView;
import com.home.rw.utils.RxBus;

import rx.Subscription;

/**
 * Created by cty on 16/10/19.
 */
public class BasePresenterImpl<T extends BaseView, E> implements BasePresenter, RequestCallBack<E> {
    protected T mView;
    protected Subscription mSubscription;

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        RxBus.cancelSubscription(mSubscription);
    }

    @Override
    public void attachView(@NonNull BaseView view) {
        // TODO?
        mView = (T) view;
    }

    @Override
    public void beforeRequest() {
        mView.showProgress();
    }

    @Override
    public void success(E data) {
        mView.hideProgress();

    }

    @Override
    public void onError(String errorMsg) {
        mView.hideProgress();
        mView.showErrorMsg(errorMsg);
    }


}