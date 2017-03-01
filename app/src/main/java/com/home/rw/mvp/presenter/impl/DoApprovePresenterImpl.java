package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.DoApproveInteractor;
import com.home.rw.mvp.interactor.EditApplyInteractor;
import com.home.rw.mvp.interactor.impl.DoApproveInteractorImpl;
import com.home.rw.mvp.interactor.impl.EditApplyInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.DoApproveView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/2/25.
 */

public class DoApprovePresenterImpl extends BasePresenterImpl<DoApproveView,BaseEntity>{

    private DoApproveInteractor mDoApproveInteractor;

    @Inject
    public DoApprovePresenterImpl(DoApproveInteractorImpl doApproveInteractor){
        mDoApproveInteractor = doApproveInteractor;
    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void doApprove(String id){
        mSubscription = mDoApproveInteractor.doApprove(this,id,reqType);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.doApproveCompleted(data);
    }

}
