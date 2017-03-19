package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.BusineseCallEntity;
import com.home.rw.mvp.interactor.BusinessCallInteractor;
import com.home.rw.mvp.interactor.CardInteractor;
import com.home.rw.mvp.interactor.impl.BusinessCallInteractorImpl;
import com.home.rw.mvp.interactor.impl.CardInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.BusinessCallView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/17.
 */

public class BusinessCallPrensenterImpl extends BasePresenterImpl<BusinessCallView,BusineseCallEntity>{
    private BusinessCallInteractor mBusinessCallInteractorImpl;

    @Inject
    public BusinessCallPrensenterImpl(BusinessCallInteractorImpl BusinessCallInteractor){
        mBusinessCallInteractorImpl = BusinessCallInteractor;
    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getBusinessCall(){
        mSubscription = mBusinessCallInteractorImpl.getBusinessCall(this);

    }

    @Override
    public void success(BusineseCallEntity data) {
        super.success(data);
        mView.getBusinessCallCompleted(data);
    }
}
