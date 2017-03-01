package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/2/25.
 */

public interface DoApproveInteractor<T> {
    Subscription doApprove(RequestCallBack<T> callback, String id, int reqType);
}
