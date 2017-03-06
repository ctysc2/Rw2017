package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.SignEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.SignInteractor;
import com.home.rw.mvp.interactor.SignListInteractor;
import com.home.rw.mvp.interactor.impl.SignInteractorImpl;
import com.home.rw.mvp.interactor.impl.SignListInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.SignListView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/2.
 */

public class SignListPresenterImpl extends BasePresenterImpl<SignListView,SignEntity>{
    private SignListInteractor mSignListInteractorImpl;

    @Inject
    public SignListPresenterImpl(SignListInteractorImpl signListDetailInteractor){
        mSignListInteractorImpl = signListDetailInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getSignList(int page,int size){
        mSubscription = mSignListInteractorImpl.getSignList(this,page,size);

    }
    @Override
    public void success(SignEntity data) {
        super.success(data);
        mView.getSignListComplete(data);
    }
}
