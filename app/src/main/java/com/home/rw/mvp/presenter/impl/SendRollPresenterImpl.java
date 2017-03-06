package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.LogEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.LogListInteractor;
import com.home.rw.mvp.interactor.SendRollInteractor;
import com.home.rw.mvp.interactor.impl.LogListInteractorImpl;
import com.home.rw.mvp.interactor.impl.SendRollInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.LogListView;
import com.home.rw.mvp.view.SendRollView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/1.
 */

public class SendRollPresenterImpl extends BasePresenterImpl<SendRollView,BaseEntity> {
    private SendRollInteractor mSendRollInteractorImpl;

    @Inject
    public SendRollPresenterImpl(SendRollInteractorImpl sendRollInteractor){
        mSendRollInteractorImpl = sendRollInteractor;


    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void sendRoll(HashMap<String,Object> input){
        mSubscription = mSendRollInteractorImpl.sendRoll(this,input);

    }
    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.sendRollComplete(data);
    }
}
