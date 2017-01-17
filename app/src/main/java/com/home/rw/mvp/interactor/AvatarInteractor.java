package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/1/13.
 */

public interface AvatarInteractor<T> {
    Subscription updateAvatar(RequestCallBack<T> callback, String fileName);
}
