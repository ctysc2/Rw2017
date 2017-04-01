package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/19.
 */

public interface DelNoticeInterator<T>{
    Subscription delNotice(RequestCallBack<T> callback,String id);
}
