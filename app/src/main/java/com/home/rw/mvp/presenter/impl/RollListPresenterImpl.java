package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.RollMeEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.RollListInteractor;
import com.home.rw.mvp.interactor.SendRollInteractor;
import com.home.rw.mvp.interactor.impl.RollListInteractorImpl;
import com.home.rw.mvp.interactor.impl.SendRollInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.RollListView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/1.
 */

public class RollListPresenterImpl extends BasePresenterImpl<RollListView,RollMeEntity>{
    private RollListInteractor mRollListInteractorImpl;

    @Inject
    public RollListPresenterImpl(RollListInteractorImpl rollListInteractor){
        mRollListInteractorImpl = rollListInteractor;


    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getRollList(int page ,int size){
        mSubscription = mRollListInteractorImpl.getRollList(this,page,size);

    }
    @Override
    public void success(RollMeEntity data) {
        super.success(data);
        mView.getRollListComplete(data);
    }
}
