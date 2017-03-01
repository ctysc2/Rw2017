package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.AddApplyInteractor;
import com.home.rw.mvp.interactor.AddLogInteractor;
import com.home.rw.mvp.interactor.impl.AddApplyInteractorImpl;
import com.home.rw.mvp.interactor.impl.AddLogInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.AddApplyView;
import com.home.rw.mvp.view.AddLogView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/1.
 */

public class AddLogPresenterImpl extends BasePresenterImpl<AddLogView,BaseEntity> {
    private AddLogInteractor mAddLogInteractorImpl;

    @Inject
    public AddLogPresenterImpl(AddLogInteractorImpl addLogInteractor){
        mAddLogInteractorImpl = addLogInteractor;


    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void addLog(String title,String content){
        mSubscription = mAddLogInteractorImpl.addLog(this,title,content);

    }
    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.addLogCompleted(data);
    }
}
