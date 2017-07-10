package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/5/3.
 */

public interface MineNoticeInteractor<T> {
    Subscription getMineNotice(RequestCallBack<T> callback);
}
