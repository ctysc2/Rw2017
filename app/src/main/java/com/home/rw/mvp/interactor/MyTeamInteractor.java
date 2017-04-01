package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/21.
 */

public interface MyTeamInteractor<T> {
    Subscription getMyTeam(RequestCallBack<T> callback);
}
