package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.AddApplyInteractor;
import com.home.rw.mvp.interactor.EditApplyInteractor;
import com.home.rw.mvp.interactor.impl.AddApplyInteractorImpl;
import com.home.rw.mvp.interactor.impl.EditApplyInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.AddApplyView;
import com.home.rw.mvp.view.EditApplyView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/2/23.
 */

public class EditApplyPresenterImpl extends BasePresenterImpl<EditApplyView,BaseEntity> {

    private EditApplyInteractor mEditApplyInteractorImpl;

    @Inject
    public EditApplyPresenterImpl(EditApplyInteractorImpl editApplyInteractor){
        mEditApplyInteractorImpl = editApplyInteractor;


    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void editApply(String id, HashMap<String,Object> input){
        mSubscription = mEditApplyInteractorImpl.editApply(this,reqType,id,input);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.editApplyCompleted(data);
    }
}
