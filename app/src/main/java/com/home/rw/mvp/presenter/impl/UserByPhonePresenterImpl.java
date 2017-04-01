package com.home.rw.mvp.presenter.impl;

import com.home.rw.common.HostType;
import com.home.rw.mvp.entity.UserInfoEntity;
import com.home.rw.mvp.entity.message.ContactListEntity;
import com.home.rw.mvp.interactor.UserByPhoneInteractor;
import com.home.rw.mvp.interactor.UserInfoInteractor;
import com.home.rw.mvp.interactor.impl.UserByPhoneInteractorImpl;
import com.home.rw.mvp.interactor.impl.UserInfoInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.UserByPhoneView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/21.
 */

public class UserByPhonePresenterImpl extends BasePresenterImpl<UserByPhoneView,ContactListEntity>{
    private UserByPhoneInteractor mUserByPhoneInteractorImpl;

    @Inject
    public UserByPhonePresenterImpl(UserByPhoneInteractorImpl userByPhoneInteractor){
        mUserByPhoneInteractorImpl = userByPhoneInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getUserByPhone(String phones){
        mSubscription = mUserByPhoneInteractorImpl.getUserByPhone(this,phones);

    }

    @Override
    public void success(ContactListEntity data) {
        super.success(data);
        mView.getUserByPhoneCompleted(data);
    }
}
