package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.PublishInteractor;
import com.home.rw.mvp.interactor.SendRollInteractor;
import com.home.rw.mvp.interactor.impl.PublishInteractorImpl;
import com.home.rw.mvp.interactor.impl.SendRollInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.PublishView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/10.
 */

public class PublishPresenterImpl extends BasePresenterImpl<PublishView,BaseEntity>{

    private PublishInteractor mPublishInteractorImpl;

    @Inject
    public PublishPresenterImpl(PublishInteractorImpl publishInteractor){
        mPublishInteractorImpl = publishInteractor;


    }
    public void setAddApplyType(int reaType){
        this.reqType = reaType;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void publish(HashMap<String,Object> input){
        mSubscription = mPublishInteractorImpl.publish(this,input);

    }
    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.publishCompleted(data);
    }

}
