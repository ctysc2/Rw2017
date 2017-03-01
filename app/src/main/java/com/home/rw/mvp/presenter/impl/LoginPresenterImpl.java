package com.home.rw.mvp.presenter.impl;

import com.home.rw.common.HostType;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.interactor.LoginInteractor;
import com.home.rw.mvp.interactor.impl.LoginInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.LoginView;

import javax.inject.Inject;

/**
 * Created by cty on 2016/12/12.
 */

public class LoginPresenterImpl extends BasePresenterImpl<LoginView,LoginEntity>{
    private LoginInteractor mLoginInteractorImpl;
    @Inject
    public LoginPresenterImpl(LoginInteractorImpl loginInteractor){
        mLoginInteractorImpl = loginInteractor;
        reqType = HostType.LOGIN;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void processLogin(String username, String password){
        mSubscription = mLoginInteractorImpl.login(this,username,password);

    }

    @Override
    public void success(LoginEntity data) {
        super.success(data);
        mView.loginCompleted(data);
    }
}
