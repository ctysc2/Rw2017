package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.message.MyFriendEntity;
import com.home.rw.mvp.entity.message.NewFriendEntity;
import com.home.rw.mvp.interactor.MyFriendInteractor;
import com.home.rw.mvp.interactor.NewFriendInteractor;
import com.home.rw.mvp.interactor.impl.MyFriendInteractorImpl;
import com.home.rw.mvp.interactor.impl.NewFriendInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.NewFriendView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/17.
 */

public class NewFriendPresenter extends BasePresenterImpl<NewFriendView,NewFriendEntity>{
    private NewFriendInteractor mNewFriendInteractorImpl;

    @Inject
    public NewFriendPresenter(NewFriendInteractorImpl newFriendInteractor){
        mNewFriendInteractorImpl = newFriendInteractor;
    }
    public void setReqType(int reaType){
        this.reqType = reaType;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getNewFriendList(int page,int size){
        mSubscription = mNewFriendInteractorImpl.getNewFriend(this,page,size);

    }

    @Override
    public void success(NewFriendEntity data) {
        super.success(data);
        mView.getNewFriendComplete(data);
    }
}
