package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/12.
 */

public interface MainPageInteractor<T> {
    Subscription getMainPage(RequestCallBack<T> callback);
}
