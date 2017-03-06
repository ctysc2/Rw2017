package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.CardQueryEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.CardInteractor;
import com.home.rw.mvp.interactor.CardQueryInteractor;
import com.home.rw.mvp.interactor.impl.CardInteractorImpl;
import com.home.rw.mvp.interactor.impl.CardQueryInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.CardQueryView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/2.
 */

public class CardQueryPresenterImpl extends BasePresenterImpl<CardQueryView,CardQueryEntity>{
    private CardQueryInteractor mcardQueryInteractorImpl;

    @Inject
    public CardQueryPresenterImpl(CardQueryInteractorImpl cardQueryDetailInteractor){
        mcardQueryInteractorImpl = cardQueryDetailInteractor;
    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void cardQuery(){
        mSubscription = mcardQueryInteractorImpl.cardQuery(this);

    }

    @Override
    public void success(CardQueryEntity data) {
        super.success(data);
        mView.getCardQueryCompleted(data);
    }
}
