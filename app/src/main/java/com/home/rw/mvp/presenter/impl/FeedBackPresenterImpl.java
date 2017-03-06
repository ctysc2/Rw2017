package com.home.rw.mvp.presenter.impl;

import com.home.rw.common.HostType;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.FeedBackInteractor;
import com.home.rw.mvp.interactor.ModifiUserInfoInteractor;
import com.home.rw.mvp.interactor.impl.FeedBackInteractorImpl;
import com.home.rw.mvp.interactor.impl.ModifiUserInfoInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.FeedBackView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/5.
 */

public class FeedBackPresenterImpl extends BasePresenterImpl<FeedBackView,BaseEntity>{
    private FeedBackInteractor mFeedBackInteractorImpl;

    @Inject
    public FeedBackPresenterImpl(FeedBackInteractorImpl feedBackInteractor){
        mFeedBackInteractorImpl = feedBackInteractor;

    }
    public void setReqType(int reqType){
        this.reqType = reqType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void sendFeedBack(String content){
        mSubscription = mFeedBackInteractorImpl.sendFeedBack(this,reqType,content);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.sendFeedBackCompleted(data);
    }

}
