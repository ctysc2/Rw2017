package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.RollMeEntity;
import com.home.rw.mvp.entity.message.RwNoticeEntity;
import com.home.rw.mvp.interactor.RollListInteractor;
import com.home.rw.mvp.interactor.RwNoticeInteractor;
import com.home.rw.mvp.interactor.impl.RollListInteractorImpl;
import com.home.rw.mvp.interactor.impl.RwNoticeInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.RwNoticeView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/17.
 */

public class RwNoticePresenterImpl extends BasePresenterImpl<RwNoticeView,RwNoticeEntity> {
    private RwNoticeInteractor mRwNoticeInteractorImpl;

    @Inject
    public RwNoticePresenterImpl(RwNoticeInteractorImpl rwNoticeInteractor){
        mRwNoticeInteractorImpl = rwNoticeInteractor;


    }
    public void setReqType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getRwNotice(int page ,int size){
        mSubscription = mRwNoticeInteractorImpl.getRwNotice(this,page,size);

    }
    @Override
    public void success(RwNoticeEntity data) {
        super.success(data);
        mView.getRwNoticeCompleted(data);
    }
}
