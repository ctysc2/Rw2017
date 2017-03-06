package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import java.util.HashMap;

import rx.Subscription;

/**
 * Created by cty on 2017/3/2.
 */

public interface SignInteractor<T> {
    Subscription sign(RequestCallBack<T> callback, HashMap<String,Object> input);
}
