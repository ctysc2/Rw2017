package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/2/28.
 */

public interface ApplyDetailInteractor<T> {
    Subscription getApplyDetail(RequestCallBack<T> callback, String id,int reqType);
}

