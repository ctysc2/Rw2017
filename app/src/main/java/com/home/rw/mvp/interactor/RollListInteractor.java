package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import java.util.HashMap;

import rx.Subscription;

/**
 * Created by cty on 2017/3/1.
 */

public interface RollListInteractor<T> {
    Subscription getRollList(RequestCallBack<T> callback, int page,int size);
}
