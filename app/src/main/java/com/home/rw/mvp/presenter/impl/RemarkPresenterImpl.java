package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.ReadNoticeInteractor;
import com.home.rw.mvp.interactor.RemarkInteractor;
import com.home.rw.mvp.interactor.impl.ReadNoticeInteractorImpl;
import com.home.rw.mvp.interactor.impl.RemarkInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.RemarkView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/18.
 */

public class RemarkPresenterImpl extends BasePresenterImpl<RemarkView,BaseEntity>{
    private RemarkInteractor mRemarkInteractorImpl;

    @Inject
    public RemarkPresenterImpl(RemarkInteractorImpl remarkInteractor){
        mRemarkInteractorImpl = remarkInteractor;
    }

    public void setReqType(int reaType){
        this.reqType = reaType;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setRemark(String userId,String nickname){
        mSubscription = mRemarkInteractorImpl.setRemark(this,userId,nickname);

    }
    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.setRemarkCompleted(data);
    }
}
