package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.message.RwNoticeDetailEntity;
import com.home.rw.mvp.entity.message.RwNoticeEntity;
import com.home.rw.mvp.interactor.RwNoticeDetailInteractor;
import com.home.rw.mvp.interactor.RwNoticeInteractor;
import com.home.rw.mvp.interactor.impl.RwNoticeDetailInteractorImpl;
import com.home.rw.mvp.interactor.impl.RwNoticeInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.RwNoticeDetailView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/20.
 */

public class RwNoticeDetailPresenterImpl extends BasePresenterImpl<RwNoticeDetailView,RwNoticeDetailEntity>{
    private RwNoticeDetailInteractor mRwNoticeDetailInteractorImpl;

    @Inject
    public RwNoticeDetailPresenterImpl(RwNoticeDetailInteractorImpl rwNoticeDetailInteractor){
        mRwNoticeDetailInteractorImpl = rwNoticeDetailInteractor;


    }
    public void setReqType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getRwNoticeDetail(String id){
        mSubscription = mRwNoticeDetailInteractorImpl.getRwNoticeDetail(this,id);

    }
    @Override
    public void success(RwNoticeDetailEntity data) {
        super.success(data);
        mView.getRwNoticeDetailCompleted(data);
    }
}
