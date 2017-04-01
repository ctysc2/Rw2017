package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.DoorKeyEntity;
import com.home.rw.mvp.interactor.DoApproveInteractor;
import com.home.rw.mvp.interactor.DoorKeyInteractor;
import com.home.rw.mvp.interactor.impl.DoApproveInteractorImpl;
import com.home.rw.mvp.interactor.impl.DoorKeyInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.DoorKeyView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/31.
 */

public class DoorKeyPresenterImpl extends BasePresenterImpl<DoorKeyView,DoorKeyEntity> {
    private DoorKeyInteractor mDoorKeyInteractor;

    @Inject
    public DoorKeyPresenterImpl(DoorKeyInteractorImpl DoorKeyInteractor){
        mDoorKeyInteractor = DoorKeyInteractor;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getDoorKey(){
        mSubscription = mDoorKeyInteractor.getDoorKey(this);

    }

    @Override
    public void success(DoorKeyEntity data) {
        super.success(data);
        mView.getDoorKeyCompleted(data);
    }
}
