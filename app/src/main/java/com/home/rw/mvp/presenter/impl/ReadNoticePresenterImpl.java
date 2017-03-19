package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.PublishInteractor;
import com.home.rw.mvp.interactor.ReadNoticeInteractor;
import com.home.rw.mvp.interactor.impl.PublishInteractorImpl;
import com.home.rw.mvp.interactor.impl.ReadNoticeInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.ReadNoticeView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/17.
 */

public class ReadNoticePresenterImpl extends BasePresenterImpl<ReadNoticeView,BaseEntity>{

    private ReadNoticeInteractor mReadNoticeInteractorImpl;

    @Inject
    public ReadNoticePresenterImpl(ReadNoticeInteractorImpl readNoticeInteractor){
        mReadNoticeInteractorImpl = readNoticeInteractor;
    }

    public void setReqType(int reaType){
        this.reqType = reaType;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void readNotice(String id){
        mSubscription = mReadNoticeInteractorImpl.readNotice(this,id);

    }
    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.setReadNoticeCompleted(data);
    }
}
