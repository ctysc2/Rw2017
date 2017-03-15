package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.FocusInteractor;
import com.home.rw.mvp.interactor.FocusListInteractor;
import com.home.rw.mvp.interactor.impl.FocusInteractorImpl;
import com.home.rw.mvp.interactor.impl.FocusListInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.FocusView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/10.
 */

public class FocusPresenterImpl extends BasePresenterImpl<FocusView,BaseEntity>{
    private FocusInteractor mFoucsInteractorImpl;

    @Inject
    public FocusPresenterImpl(FocusInteractorImpl foucsInteractor){
        mFoucsInteractorImpl = foucsInteractor;

    }
    public void setReqType(int reqType){
        this.reqType = reqType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void doFocus(String userId){
        mSubscription = mFoucsInteractorImpl.doFocus(this,userId,reqType);
    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.doFocusCompleted(data);
    }
}
