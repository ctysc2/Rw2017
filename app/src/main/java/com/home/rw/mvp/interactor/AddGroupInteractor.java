package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/18.
 */

public interface AddGroupInteractor<T>{
    Subscription addGroup(RequestCallBack<T> callback, String name,String receiveUsers);
}
