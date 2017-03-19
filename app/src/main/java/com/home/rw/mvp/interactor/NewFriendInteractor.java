package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/17.
 */

public interface NewFriendInteractor<T> {
    Subscription getNewFriend(RequestCallBack<T> callback, int page, int size);
}
