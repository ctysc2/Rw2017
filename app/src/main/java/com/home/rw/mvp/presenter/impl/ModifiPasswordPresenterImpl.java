package com.home.rw.mvp.presenter.impl;

import com.home.rw.common.HostType;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.ModifiPassWordInteractor;
import com.home.rw.mvp.interactor.ModifiUserInfoInteractor;
import com.home.rw.mvp.interactor.impl.ModifiPassWordInteractorImpl;
import com.home.rw.mvp.interactor.impl.ModifiUserInfoInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.ModifiPasswordView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/5.
 */

public class ModifiPasswordPresenterImpl extends BasePresenterImpl<ModifiPasswordView,BaseEntity>{
    private ModifiPassWordInteractor mModifiPasswordInteractorImpl;

    @Inject
    public ModifiPasswordPresenterImpl(ModifiPassWordInteractorImpl modifiPasswordInteractor){
        mModifiPasswordInteractorImpl = modifiPasswordInteractor;
        reqType = HostType.MODIFI_PASSWORD;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void modifiPassword(HashMap<String,Object> input){
        mSubscription = mModifiPasswordInteractorImpl.modifiPassword(this,input);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.modifiPasswordCompleted(data);
    }
}
