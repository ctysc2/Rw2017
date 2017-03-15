package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import java.util.HashMap;

import rx.Subscription;

/**
 * Created by cty on 2017/3/10.
 */

public interface PublishInteractor<T> {
    Subscription publish(RequestCallBack<T> callback, HashMap<String,Object> input);
}
