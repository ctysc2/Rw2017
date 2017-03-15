package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/10.
 */

public interface CommListInteractor<T> {
    Subscription getCommList(RequestCallBack<T> callback, int page, int size ,int reqType);
    Subscription getCommList(RequestCallBack<T> callback, String userId,int page, int size ,int reqType);
}
