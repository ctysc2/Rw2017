package com.home.rw.listener;

/**
 * Created by cty on 16/10/18.
 */
public interface RequestCallBack<T> {

    void beforeRequest();

    void success(T data);

    void onError(String errorMsg);
}
