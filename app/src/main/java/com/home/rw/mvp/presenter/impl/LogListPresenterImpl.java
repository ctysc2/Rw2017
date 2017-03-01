package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.LogEntity;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.AddLogInteractor;
import com.home.rw.mvp.interactor.LogListInteractor;
import com.home.rw.mvp.interactor.impl.AddLogInteractorImpl;
import com.home.rw.mvp.interactor.impl.LogListInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.LogListView;
import com.home.rw.mvp.view.LoginView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/1.
 */

public class LogListPresenterImpl extends BasePresenterImpl<LogListView,LogEntity> {
    private LogListInteractor mLogListInteractorImpl;

    @Inject
    public LogListPresenterImpl(LogListInteractorImpl logListInteractor){
        mLogListInteractorImpl = logListInteractor;


    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getLogList(int page,int size){
        mSubscription = mLogListInteractorImpl.getLogList(this,reqType,page,size);

    }
    @Override
    public void success(LogEntity data) {
        super.success(data);
        mView.getLogListCompleted(data);
    }
}
