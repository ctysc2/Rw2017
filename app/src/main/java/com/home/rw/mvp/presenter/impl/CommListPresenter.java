package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.CardQueryEntity;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.interactor.CardQueryInteractor;
import com.home.rw.mvp.interactor.CommListInteractor;
import com.home.rw.mvp.interactor.impl.CardQueryInteractorImpl;
import com.home.rw.mvp.interactor.impl.CommListInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.CommListView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/10.
 */

public class CommListPresenter extends BasePresenterImpl<CommListView,CommunicationEntity>{
    private CommListInteractor mCommListInteractorImpl;

    @Inject
    public CommListPresenter(CommListInteractorImpl commListInteractor){
        mCommListInteractorImpl = commListInteractor;
    }
    public void setReqType(int reqType){
        this.reqType = reqType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getCommList(int page,int size){
        mSubscription = mCommListInteractorImpl.getCommList(this,page,size,reqType);

    }
    //他人获取
    public void getCommList(String userId,int page,int size){
        mSubscription = mCommListInteractorImpl.getCommList(this,userId,page,size,reqType);

    }

    @Override
    public void success(CommunicationEntity data) {
        super.success(data);
        mView.getCommListCompleted(data);
    }
}
