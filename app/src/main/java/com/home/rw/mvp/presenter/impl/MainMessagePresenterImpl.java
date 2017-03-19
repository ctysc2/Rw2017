package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.MainBusinessEntity;
import com.home.rw.mvp.interactor.LogOutInteractor;
import com.home.rw.mvp.interactor.MainMessageInteractor;
import com.home.rw.mvp.interactor.impl.LogOutInteractorImpl;
import com.home.rw.mvp.interactor.impl.MainMessageInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.MainMessageView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/17.
 */

public class MainMessagePresenterImpl extends BasePresenterImpl<MainMessageView,MainBusinessEntity>{
    private MainMessageInteractor mMainMessageInteractorImpl;

    @Inject
    public MainMessagePresenterImpl(MainMessageInteractorImpl mainMessageInteractor){
        mMainMessageInteractorImpl = mainMessageInteractor;


    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getMainMessage(){
        mSubscription = mMainMessageInteractorImpl.mainMessage(this);

    }
    @Override
    public void success(MainBusinessEntity data) {
        super.success(data);
        mView.mainMessageCompleted(data);
    }
}
