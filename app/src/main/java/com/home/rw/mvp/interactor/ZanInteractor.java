package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/11.
 */

public interface ZanInteractor<T> {
    Subscription zan(RequestCallBack<T> callback,String id);
}
