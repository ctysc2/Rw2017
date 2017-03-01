package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import java.util.HashMap;

import rx.Subscription;

/**
 * Created by cty on 2017/2/23.
 */

public interface EditApplyInteractor<T> {
    Subscription editApply(RequestCallBack<T> callback, int reqType, String id, HashMap<String,Object> input);
}
