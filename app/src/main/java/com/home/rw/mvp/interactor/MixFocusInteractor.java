package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/10.
 */

public interface MixFocusInteractor<T> {
    Subscription getMixFocus(RequestCallBack<T> callback);
}
