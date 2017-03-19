package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/18.
 */

public interface RemarkInteractor<T> {
    Subscription setRemark(RequestCallBack<T> callback, String userId, String nickname);
}
