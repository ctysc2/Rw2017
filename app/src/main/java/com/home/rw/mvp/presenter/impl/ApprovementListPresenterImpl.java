package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.interactor.AddApplyInteractor;
import com.home.rw.mvp.interactor.ApprovementListInteractor;
import com.home.rw.mvp.interactor.impl.AddApplyInteractorImpl;
import com.home.rw.mvp.interactor.impl.ApprovementListInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.ApprovementListView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/2/24.
 */

public class ApprovementListPresenterImpl extends BasePresenterImpl<ApprovementListView,ApprovementListEntity> {
    private ApprovementListInteractor mApprovementListInteractorImpl;

    @Inject
    public ApprovementListPresenterImpl(ApprovementListInteractorImpl approvementListInteractor){
        mApprovementListInteractorImpl = approvementListInteractor;


    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getApprovementList(int page,int size){
        mSubscription = mApprovementListInteractorImpl.getApprovementList(this,page,size,reqType);

    }

    @Override
    public void success(ApprovementListEntity data) {
        super.success(data);
        mView.getApprovementListCompleted(data);
    }
}
