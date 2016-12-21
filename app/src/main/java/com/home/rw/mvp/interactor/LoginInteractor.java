package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2016/12/12.
 */

public interface LoginInteractor<T>{
    Subscription login(RequestCallBack<T> callback,String username,String password);
}
