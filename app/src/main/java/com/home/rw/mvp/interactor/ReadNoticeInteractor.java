package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import java.util.HashMap;

import rx.Subscription;

/**
 * Created by cty on 2017/3/17.
 */

public interface ReadNoticeInteractor<T> {
    Subscription readNotice(RequestCallBack<T> callback, String id);
}
