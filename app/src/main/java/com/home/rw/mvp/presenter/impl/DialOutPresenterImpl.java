package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.DepartmentEntity;
import com.home.rw.mvp.interactor.DepartmentInteractor;
import com.home.rw.mvp.interactor.DialOutInteractor;
import com.home.rw.mvp.interactor.impl.DepartmentInteractorImpl;
import com.home.rw.mvp.interactor.impl.DialOutInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.DialOutView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/19.
 */

public class DialOutPresenterImpl extends BasePresenterImpl<DialOutView,BaseEntity>{
    private DialOutInteractor mDialOutInteractorImpl;

    @Inject
    public DialOutPresenterImpl(DialOutInteractorImpl dialOutInteractor){
        mDialOutInteractorImpl = dialOutInteractor;
    }
    public void setReqType(int reqType){
        this.reqType = reqType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void dialOut(String userId){

        mSubscription = mDialOutInteractorImpl.dialOut(this,userId);

    }
    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.dialOutCompleted(data);
    }
}
