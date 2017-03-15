package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.LogEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.LogListInteractor;
import com.home.rw.mvp.interactor.LogOutInteractor;
import com.home.rw.mvp.interactor.impl.LogListInteractorImpl;
import com.home.rw.mvp.interactor.impl.LogOutInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.LogOutView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/8.
 */

public class LogOutPresenterImpl extends BasePresenterImpl<LogOutView,BaseEntity>{
    private LogOutInteractor mLogOutInteractorImpl;

    @Inject
    public LogOutPresenterImpl(LogOutInteractorImpl logoutInteractor){
        mLogOutInteractorImpl = logoutInteractor;


    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void logOut(){
        mSubscription = mLogOutInteractorImpl.logOut(this);

    }
    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.logOutCompleted(data);
    }
}
