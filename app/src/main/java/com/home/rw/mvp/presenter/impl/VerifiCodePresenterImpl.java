package com.home.rw.mvp.presenter.impl;

import com.home.rw.common.HostType;
import com.home.rw.mvp.entity.UserInfoEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.UserInfoInteractor;
import com.home.rw.mvp.interactor.VerifiCodeInteractor;
import com.home.rw.mvp.interactor.impl.UserInfoInteractorImpl;
import com.home.rw.mvp.interactor.impl.VerifiCodeInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.VerifiCodeView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/5.
 */

public class VerifiCodePresenterImpl extends BasePresenterImpl<VerifiCodeView,BaseEntity>{
    private VerifiCodeInteractor mVerifiCodeInteractorImpl;

    @Inject
    public VerifiCodePresenterImpl(VerifiCodeInteractorImpl VerifiCodeInteractor){
        mVerifiCodeInteractorImpl = VerifiCodeInteractor;
        reqType = HostType.VERIFI_CODE;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void sendVerifiCode(){
        mSubscription = mVerifiCodeInteractorImpl.sendVerifiCode(this);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.sendVerifiCodeCompleted(data);
    }
}
