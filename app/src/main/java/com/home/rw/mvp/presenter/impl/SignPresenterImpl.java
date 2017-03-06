package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.CardInteractor;
import com.home.rw.mvp.interactor.SignInteractor;
import com.home.rw.mvp.interactor.impl.CardInteractorImpl;
import com.home.rw.mvp.interactor.impl.SignInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.SignView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/2.
 */

public class SignPresenterImpl extends BasePresenterImpl<SignView,BaseEntity>{
    private SignInteractor mSignInteractorImpl;

    @Inject
    public SignPresenterImpl(SignInteractorImpl signDetailInteractor){
        mSignInteractorImpl = signDetailInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void sign(HashMap<String,Object> input){
        mSubscription = mSignInteractorImpl.sign(this,input);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.signCompleted(data);
    }
}
