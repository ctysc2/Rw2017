package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/9.
 */

public interface FocusListInteractor<T> {
    Subscription getFocusList(RequestCallBack<T> callback, int page, int size);
}
