package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.message.MyTeamEntity;
import com.home.rw.mvp.entity.message.MyGroupEntity;
import com.home.rw.mvp.interactor.MyGroupInteractor;
import com.home.rw.mvp.interactor.MyTeamInteractor;
import com.home.rw.mvp.interactor.impl.MyGroupInteractorImpl;
import com.home.rw.mvp.interactor.impl.MyTeamInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.MyTeamView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/21.
 */

public class MyTeamPresenterImpl extends BasePresenterImpl<MyTeamView,MyTeamEntity> {
    private MyTeamInteractor mMyTeamInteractorImpl;

    @Inject
    public MyTeamPresenterImpl(MyTeamInteractorImpl myTeamInteractor){
        mMyTeamInteractorImpl = myTeamInteractor;
    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getMyTeam(){
        mSubscription = mMyTeamInteractorImpl.getMyTeam(this);

    }

    @Override
    public void success(MyTeamEntity data) {
        super.success(data);
        mView.getMyTeamCompleted(data);
    }
}
