package com.home.rw.mvp.presenter.impl;

import com.home.rw.common.HostType;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.UploadEntity;
import com.home.rw.mvp.entity.UserInfoEntity;
import com.home.rw.mvp.interactor.LoginInteractor;
import com.home.rw.mvp.interactor.UserInfoInteractor;

import com.home.rw.mvp.interactor.impl.UserInfoInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;

import com.home.rw.mvp.view.UserInfoView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/2/22.
 */

public class UserInfoPresenterImpl extends BasePresenterImpl<UserInfoView,UserInfoEntity> {
    private UserInfoInteractor mUserInfoInteractorImpl;

    @Inject
    public UserInfoPresenterImpl(UserInfoInteractorImpl userInfoInteractor){
        mUserInfoInteractorImpl = userInfoInteractor;
        reqType = HostType.USER_INFO;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getUserInfo(){
        mSubscription = mUserInfoInteractorImpl.getUserInfo(this);

    }
    public void getOtherUserInfo(String userId){
        mSubscription = mUserInfoInteractorImpl.getOtherUserInfo(this,userId);
    }
    @Override
    public void success(UserInfoEntity data) {
        super.success(data);
        mView.getUserInfoCompleted(data);
    }
}
