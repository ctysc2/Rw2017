package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.AddApplyInteractor;
import com.home.rw.mvp.interactor.AddFriendInteractor;
import com.home.rw.mvp.interactor.impl.AddApplyInteractorImpl;
import com.home.rw.mvp.interactor.impl.AddFriendInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.AddFriendView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/17.
 */

public class AddFriendPresenterImpl extends BasePresenterImpl<AddFriendView,BaseEntity>{

    private AddFriendInteractor mAddFriendInteractorImpl;

    @Inject
    public AddFriendPresenterImpl(AddFriendInteractorImpl addFriendInteractor){
        mAddFriendInteractorImpl = addFriendInteractor;

    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void addFriend(String userId){
        mSubscription = mAddFriendInteractorImpl.addFriend(this,userId);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.addFriendCompleted(data);
    }
}
