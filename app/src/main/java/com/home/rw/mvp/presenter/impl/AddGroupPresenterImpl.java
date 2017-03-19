package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.AddFriendInteractor;
import com.home.rw.mvp.interactor.AddGroupInteractor;
import com.home.rw.mvp.interactor.impl.AddFriendInteractorImpl;
import com.home.rw.mvp.interactor.impl.AddGroupInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.AddGroupView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/18.
 */

public class AddGroupPresenterImpl extends BasePresenterImpl<AddGroupView,BaseEntity>{
    private AddGroupInteractor mAddGroupInteractorImpl;

    @Inject
    public AddGroupPresenterImpl(AddGroupInteractorImpl addGroupInteractor){
        mAddGroupInteractorImpl = addGroupInteractor;

    }
    public void setReqType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void addFriend(String name,String receiveUsers){
        mSubscription = mAddGroupInteractorImpl.addGroup(this,name,receiveUsers);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.addGroupCompleted(data);
    }
}
