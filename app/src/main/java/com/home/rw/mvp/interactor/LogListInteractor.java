package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/1.
 */

public interface LogListInteractor<T> {
    Subscription getLogList(RequestCallBack<T> callback, int reqType,int page,int size);
}
