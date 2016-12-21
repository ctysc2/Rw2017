package com.home.rw.mvp.interactor.impl;

import android.util.Log;

import com.home.rw.common.HostType;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.interactor.LoginInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.TransformUtils;
import com.socks.library.KLog;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2016/12/12.
 */

public class LoginInteractorImpl implements LoginInteractor<LoginEntity> {

    @Inject
    public LoginInteractorImpl(){

    }
    @Override
    public Subscription login(final RequestCallBack<LoginEntity> callback,String username,String password) {
        return RetrofitManager.getInstance(HostType.LOGIN).login(username, password)

                .compose(TransformUtils.<LoginEntity>defaultSchedulers())
                .subscribe(new Observer<LoginEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(LoginEntity login) {
                        callback.success(login);

                    }

                });
    }
}
