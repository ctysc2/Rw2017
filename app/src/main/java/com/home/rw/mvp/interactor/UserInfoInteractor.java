package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import java.util.List;

import rx.Subscription;

/**
 * Created by cty on 2017/2/22.
 */

public interface UserInfoInteractor<T> {
    Subscription getUserInfo(RequestCallBack<T> callback);
    Subscription getOtherUserInfo(RequestCallBack<T> callback,String userId);
}
