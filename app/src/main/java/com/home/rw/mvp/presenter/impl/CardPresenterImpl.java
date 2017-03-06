package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.ApplyDetailEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.ApplyDetailInteractor;
import com.home.rw.mvp.interactor.CardInteractor;
import com.home.rw.mvp.interactor.impl.ApplyDetailInteractorImpl;
import com.home.rw.mvp.interactor.impl.CardInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.CardView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/2.
 */

public class CardPresenterImpl extends BasePresenterImpl<CardView,BaseEntity>{
    private CardInteractor mcardInteractorImpl;

    @Inject
    public CardPresenterImpl(CardInteractorImpl cardDetailInteractor){
        mcardInteractorImpl = cardDetailInteractor;
    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void card(HashMap<String,Object> input){
        mSubscription = mcardInteractorImpl.card(this,input);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.cardCompleted(data);
    }
}
