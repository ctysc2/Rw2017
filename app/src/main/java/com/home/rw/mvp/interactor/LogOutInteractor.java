package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/8.
 */

public interface LogOutInteractor<T> {
    Subscription logOut(RequestCallBack<T> callback);
}
