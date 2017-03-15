package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.entity.LinkedEntity;
import com.home.rw.mvp.interactor.FocusListInteractor;
import com.home.rw.mvp.interactor.LinkedInteractor;
import com.home.rw.mvp.interactor.impl.FocusListInteractorImpl;
import com.home.rw.mvp.interactor.impl.LinkedInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.LinkedView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/10.
 */

public class LinkedPresenterImpl extends BasePresenterImpl<LinkedView,LinkedEntity>{

    private LinkedInteractor mLinkedInteractorImpl;

    @Inject
    public LinkedPresenterImpl(LinkedInteractorImpl linkedInteractor){
        mLinkedInteractorImpl = linkedInteractor;

    }
    public void setReqType(int reqType){
        this.reqType = reqType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getLinked(){
        mSubscription = mLinkedInteractorImpl.getLinked(this,reqType);
    }

    @Override
    public void success(LinkedEntity data) {
        super.success(data);
        mView.getLinkedCompleted(reqType,data);
    }

}
