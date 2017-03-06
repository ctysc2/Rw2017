package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/5.
 */

public interface FeedBackInteractor<T> {
    Subscription sendFeedBack(RequestCallBack<T> callback, int reqType,String content);
}
