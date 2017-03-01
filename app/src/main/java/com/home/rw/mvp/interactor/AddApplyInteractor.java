package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/2/22.
 */

public interface AddApplyInteractor<T> {
    Subscription addApply(RequestCallBack<T> callback,int reqType);
}
