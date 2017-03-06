package com.home.rw.mvp.presenter.impl;

import com.home.rw.common.HostType;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.ModifiUserInfoInteractor;
import com.home.rw.mvp.interactor.VerifiCodeInteractor;
import com.home.rw.mvp.interactor.impl.ModifiUserInfoInteractorImpl;
import com.home.rw.mvp.interactor.impl.VerifiCodeInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.ModifiUserInfoView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/5.
 */

public class ModifiUserInfoPresenterImpl extends BasePresenterImpl<ModifiUserInfoView,BaseEntity>{
    private ModifiUserInfoInteractor mModifiUserInfoInteractorImpl;

    @Inject
    public ModifiUserInfoPresenterImpl(ModifiUserInfoInteractorImpl modifiUserInfoInteractor){
        mModifiUserInfoInteractorImpl = modifiUserInfoInteractor;
        reqType = HostType.MODIFI_USER_INFO;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void modifiUserInfo(HashMap<String,Object> input){
        mSubscription = mModifiUserInfoInteractorImpl.modifiUserInfo(this,input);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.modifiUserInfoCompleted(data);
    }
}
