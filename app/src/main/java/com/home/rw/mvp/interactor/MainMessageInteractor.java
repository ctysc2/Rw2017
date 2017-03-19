package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/17.
 */

public interface MainMessageInteractor<T>{
    Subscription mainMessage(RequestCallBack<T> callback);
}
