package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.CompanyNoticeEntity;
import com.home.rw.mvp.entity.message.CompNoticeEntity;
import com.home.rw.mvp.interactor.CommListInteractor;
import com.home.rw.mvp.interactor.CompanyNoticeInteractor;
import com.home.rw.mvp.interactor.impl.CommListInteractorImpl;
import com.home.rw.mvp.interactor.impl.CompanyNoticeInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.CompanyNoticeView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/17.
 */

public class CompanyNoticePresenterImpl extends BasePresenterImpl<CompanyNoticeView,CompNoticeEntity> {
    private CompanyNoticeInteractor mCompanyNoticeInteractorImpl;

    @Inject
    public CompanyNoticePresenterImpl(CompanyNoticeInteractorImpl companyNoticeInteractor){
        mCompanyNoticeInteractorImpl = companyNoticeInteractor;
    }
    public void setReqType(int reqType){
        this.reqType = reqType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getCompanyNotice(int page,int size,int type){

        mSubscription = mCompanyNoticeInteractorImpl.getCompanyNotice(this,page,size,type);

    }
    @Override
    public void success(CompNoticeEntity data) {
        super.success(data);
        mView.getCompanyNoticeCompleted(data);
    }
}
