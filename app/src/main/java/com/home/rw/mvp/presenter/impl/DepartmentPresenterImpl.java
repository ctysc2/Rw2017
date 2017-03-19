package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.CompanyNoticeEntity;
import com.home.rw.mvp.entity.message.DepartmentEntity;
import com.home.rw.mvp.interactor.CompanyNoticeInteractor;
import com.home.rw.mvp.interactor.DepartmentInteractor;
import com.home.rw.mvp.interactor.impl.CompanyNoticeInteractorImpl;
import com.home.rw.mvp.interactor.impl.DepartmentInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.DepartmentView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/18.
 */

public class DepartmentPresenterImpl extends BasePresenterImpl<DepartmentView,DepartmentEntity>{
    private DepartmentInteractor mDepartmentInteractorImpl;

    @Inject
    public DepartmentPresenterImpl(DepartmentInteractorImpl departmentInteractor){
        mDepartmentInteractorImpl = departmentInteractor;
    }
    public void setReqType(int reqType){
        this.reqType = reqType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getDepartmentList(){

        mSubscription = mDepartmentInteractorImpl.getCompanyNotice(this);

    }
    @Override
    public void success(DepartmentEntity data) {
        super.success(data);
        mView.getDepartmentListCompleted(data);
    }
}

