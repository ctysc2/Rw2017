package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.AcceptFriendInteractor;
import com.home.rw.mvp.interactor.AddApplyInteractor;
import com.home.rw.mvp.interactor.impl.AcceptFriendInteractorImpl;
import com.home.rw.mvp.interactor.impl.AddApplyInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.AcceptFriendView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/17.
 */

public class AcceptFriendPresenterImpl extends BasePresenterImpl<AcceptFriendView,BaseEntity> {
    private AcceptFriendInteractor mAcceptFriendInteractorImpl;

    @Inject
    public AcceptFriendPresenterImpl(AcceptFriendInteractorImpl acceptFriendInteractor){
        mAcceptFriendInteractorImpl = acceptFriendInteractor;


    }
    public void setReqType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void acceptFriend(String userId){
        mSubscription = mAcceptFriendInteractorImpl.acceptFriend(this,userId);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.acceptFriendCompleted(data);
    }
}
