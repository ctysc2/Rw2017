package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/31.
 */

public interface DoorKeyInteractor<T> {
    Subscription getDoorKey(RequestCallBack<T> callback);
}
