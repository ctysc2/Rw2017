package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/20.
 */

public interface RwNoticeDetailInteractor<T> {
    Subscription getRwNoticeDetail(RequestCallBack<T> callback,String id);
}
