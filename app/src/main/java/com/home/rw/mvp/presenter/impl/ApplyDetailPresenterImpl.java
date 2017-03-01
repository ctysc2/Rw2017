package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.ApplyDetailEntity;
import com.home.rw.mvp.interactor.AddApplyInteractor;
import com.home.rw.mvp.interactor.ApplyDetailInteractor;
import com.home.rw.mvp.interactor.impl.AddApplyInteractorImpl;
import com.home.rw.mvp.interactor.impl.ApplyDetailInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.AddApplyView;
import com.home.rw.mvp.view.ApplyDetailView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/2/28.
 */

public class ApplyDetailPresenterImpl extends BasePresenterImpl<ApplyDetailView,ApplyDetailEntity> {
    private ApplyDetailInteractor mApplyDetailInteractorImpl;

    @Inject
    public ApplyDetailPresenterImpl(ApplyDetailInteractorImpl applyDetailInteractor){
        mApplyDetailInteractorImpl = applyDetailInteractor;


    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getApplyDetail(String id){
        mSubscription = mApplyDetailInteractorImpl.getApplyDetail(this,id,reqType);

    }

    @Override
    public void success(ApplyDetailEntity data) {
        super.success(data);
        mView.getApplyDetailCompleted(data);
    }
}
