package com.home.rw.mvp.presenter.impl;

import com.home.rw.common.HostType;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.VerifiCodeInteractor;
import com.home.rw.mvp.interactor.ZanInteractor;
import com.home.rw.mvp.interactor.impl.VerifiCodeInteractorImpl;
import com.home.rw.mvp.interactor.impl.ZanInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.ZanView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/11.
 */

public class ZanPresenterImpl extends BasePresenterImpl<ZanView,BaseEntity>{
    private ZanInteractor mZanInteractorImpl;

    @Inject
    public ZanPresenterImpl(ZanInteractorImpl zanInteractor){
        mZanInteractorImpl = zanInteractor;
        reqType = HostType.VERIFI_CODE;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void zan(String id){
        mSubscription = mZanInteractorImpl.zan(this,id);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.zanCompleted(data);
    }
}
