package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/17.
 */

public interface AddFriendInteractor<T> {
    Subscription addFriend(RequestCallBack<T> callback, String userId,String remark);
}
