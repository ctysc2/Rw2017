package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.MainPageEntity;
import com.home.rw.mvp.entity.MineNoticeEntity;
import com.home.rw.mvp.interactor.MainPageInteractor;
import com.home.rw.mvp.interactor.MineNoticeInteractor;
import com.home.rw.mvp.interactor.impl.MainPageInteractorImpl;
import com.home.rw.mvp.interactor.impl.MineNoticeInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.MineNoticeView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/5/3.
 */

public class MineNoticePresenterImpl extends BasePresenterImpl<MineNoticeView,MineNoticeEntity> {
    private MineNoticeInteractor mMineNoticeInteractorImpl;

    @Inject
    public MineNoticePresenterImpl(MineNoticeInteractorImpl mineNoticeInteractorImpl){
        mMineNoticeInteractorImpl = mineNoticeInteractorImpl;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getMineNotice(){
        mSubscription = mMineNoticeInteractorImpl.getMineNotice(this);

    }
    @Override
    public void success(MineNoticeEntity data) {
        super.success(data);
        mView.getMineNoticeCompleted(data);
    }
}
