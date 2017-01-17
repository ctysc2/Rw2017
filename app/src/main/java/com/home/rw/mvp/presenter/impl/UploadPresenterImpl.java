package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.UploadEntity;
import com.home.rw.mvp.interactor.LoginInteractor;
import com.home.rw.mvp.interactor.UploadInteractor;
import com.home.rw.mvp.interactor.impl.LoginInteractorImpl;
import com.home.rw.mvp.interactor.impl.UploadInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.UploadView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cty on 2017/1/12.
 */

public class UploadPresenterImpl extends BasePresenterImpl<UploadView,UploadEntity> {
    private UploadInteractor mUploadInteractorImpl;

    @Inject
    public UploadPresenterImpl(UploadInteractorImpl uploadInteractor){
        mUploadInteractorImpl = uploadInteractor;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void processUpload(List<String> files){
        mSubscription = mUploadInteractorImpl.upload(this,files);

    }

    @Override
    public void success(UploadEntity data) {
        super.success(data);
        mView.uploadComplete(data);
    }
}
