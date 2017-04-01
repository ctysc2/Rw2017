package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.DepartmentEntity;
import com.home.rw.mvp.interactor.DelNoticeInterator;
import com.home.rw.mvp.interactor.DepartmentInteractor;
import com.home.rw.mvp.interactor.impl.DelNoticeInteratorImpl;
import com.home.rw.mvp.interactor.impl.DepartmentInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.DelNoticeView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/19.
 */

public class DelNoticePresenterImpl extends BasePresenterImpl<DelNoticeView,BaseEntity>{
    private DelNoticeInterator mDelNoticeInteractorImpl;

    @Inject
    public DelNoticePresenterImpl(DelNoticeInteratorImpl delNoticeInteractor){
        mDelNoticeInteractorImpl = delNoticeInteractor;
    }
    public void setReqType(int reqType){
        this.reqType = reqType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void delNotice(String id){

        mSubscription = mDelNoticeInteractorImpl.delNotice(this,id);

    }
    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.delNoticeCompleted(data);
    }
}
