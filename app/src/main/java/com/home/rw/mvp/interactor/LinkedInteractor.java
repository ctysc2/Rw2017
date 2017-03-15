package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/10.
 */

public interface LinkedInteractor<T> {
    Subscription getLinked(RequestCallBack<T> callback,int reqType);
}
