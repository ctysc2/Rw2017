package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.message.MyFriendEntity;
import com.home.rw.mvp.entity.message.MyGroupEntity;
import com.home.rw.mvp.interactor.MyFriendInteractor;
import com.home.rw.mvp.interactor.MyGroupInteractor;
import com.home.rw.mvp.interactor.impl.MyFriendInteractorImpl;
import com.home.rw.mvp.interactor.impl.MyGroupInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.MyGroupView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/17.
 */

public class MyGroupPresenterImpl extends BasePresenterImpl<MyGroupView,MyGroupEntity>{
    private MyGroupInteractor mMyGroupInteractorImpl;

    @Inject
    public MyGroupPresenterImpl(MyGroupInteractorImpl myGroupInteractor){
        mMyGroupInteractorImpl = myGroupInteractor;
    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getMyGroupList(int page,int size){
        mSubscription = mMyGroupInteractorImpl.getMyGroupList(this,page,size);

    }

    @Override
    public void success(MyGroupEntity data) {
        super.success(data);
        mView.getMyGroupCompleted(data);
    }
}
