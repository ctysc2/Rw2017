package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.MixFocusEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.LogOutInteractor;
import com.home.rw.mvp.interactor.MixFocusInteractor;
import com.home.rw.mvp.interactor.impl.LogOutInteractorImpl;
import com.home.rw.mvp.interactor.impl.MixFocusInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.MixFocusView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/11.
 */

public class MixFocusPresenterImpl extends BasePresenterImpl<MixFocusView,MixFocusEntity>{
    private MixFocusInteractor mMixFocusInteractorImpl;

    @Inject
    public MixFocusPresenterImpl(MixFocusInteractorImpl mixFocusInteractor){
        mMixFocusInteractorImpl = mixFocusInteractor;


    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getMixFocus(){
        mSubscription = mMixFocusInteractorImpl.getMixFocus(this);

    }
    @Override
    public void success(MixFocusEntity data) {
        super.success(data);
        mView.getMixFocusCompleted(data);
    }
}
