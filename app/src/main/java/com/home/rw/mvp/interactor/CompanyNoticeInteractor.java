package com.home.rw.mvp.interactor;

import com.home.rw.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by cty on 2017/3/17.
 */

public interface CompanyNoticeInteractor<T> {
    Subscription getCompanyNotice(RequestCallBack<T> callback, int page, int size);
}
