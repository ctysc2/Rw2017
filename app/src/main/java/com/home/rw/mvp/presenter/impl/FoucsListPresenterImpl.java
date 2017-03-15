package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.FeedBackInteractor;
import com.home.rw.mvp.interactor.FocusListInteractor;
import com.home.rw.mvp.interactor.impl.FeedBackInteractorImpl;
import com.home.rw.mvp.interactor.impl.FocusListInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.FocusListView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/9.
 */

public class FoucsListPresenterImpl extends BasePresenterImpl<FocusListView,FacusListEntity>{
    private FocusListInteractor mFoucsListInteractorImpl;

    @Inject
    public FoucsListPresenterImpl(FocusListInteractorImpl foucsListInteractor){
        mFoucsListInteractorImpl = foucsListInteractor;

    }
    public void setReqType(int reqType){
        this.reqType = reqType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getFocusList(int page,int size){
        mSubscription = mFoucsListInteractorImpl.getFocusList(this,page,size);
    }

    @Override
    public void success(FacusListEntity data) {
        super.success(data);
        mView.getFocusList(data);
    }
}
