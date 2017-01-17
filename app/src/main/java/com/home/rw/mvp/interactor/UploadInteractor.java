package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import java.util.List;

import rx.Subscription;

/**
 * Created by cty on 2017/1/12.
 */

public interface UploadInteractor<T>{
    Subscription upload(RequestCallBack<T> callback, List<String> fileList);
}
