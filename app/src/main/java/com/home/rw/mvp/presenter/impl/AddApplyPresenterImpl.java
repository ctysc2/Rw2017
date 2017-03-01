package com.home.rw.mvp.presenter.impl;

import com.home.rw.common.HostType;
import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.interactor.AddApplyInteractor;
import com.home.rw.mvp.interactor.LoginInteractor;
import com.home.rw.mvp.interactor.impl.AddApplyInteractorImpl;
import com.home.rw.mvp.interactor.impl.LoginInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.AddApplyView;
import com.home.rw.mvp.view.LoginView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/2/22.
 */

public class AddApplyPresenterImpl extends BasePresenterImpl<AddApplyView,AddApplyEntity> {

    private AddApplyInteractor mAddApplyInteractorImpl;

    @Inject
    public AddApplyPresenterImpl(AddApplyInteractorImpl addApplyInteractor){
        mAddApplyInteractorImpl = addApplyInteractor;


    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void addApply(){
        mSubscription = mAddApplyInteractorImpl.addApply(this,reqType);

    }

    @Override
    public void success(AddApplyEntity data) {
        super.success(data);
        mView.addApplyCompleted(data);
    }
}
