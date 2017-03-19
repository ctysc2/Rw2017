package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.MyFriendEntity;
import com.home.rw.mvp.interactor.MyFriendInteractor;
import com.home.rw.mvp.interactor.PublishInteractor;
import com.home.rw.mvp.interactor.impl.MyFriendInteractorImpl;
import com.home.rw.mvp.interactor.impl.PublishInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.MyFriendView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/17.
 */

public class MyFriendPresenterImpl extends BasePresenterImpl<MyFriendView,MyFriendEntity> {

    private MyFriendInteractor mMyFriendInteractorImpl;

    @Inject
    public MyFriendPresenterImpl(MyFriendInteractorImpl myFriendInteractor){
        mMyFriendInteractorImpl = myFriendInteractor;
    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getMyFriendList(){
        mSubscription = mMyFriendInteractorImpl.getMyFriend(this);

    }

    @Override
    public void success(MyFriendEntity data) {
        super.success(data);
        mView.getMyFriendCompleted(data);
    }
}
