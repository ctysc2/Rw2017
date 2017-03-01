package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/2/24.
 */

public interface ApprovementListInteractor<T> {
    Subscription getApprovementList(RequestCallBack<T> callback, int page,int size,int reqType);
}
