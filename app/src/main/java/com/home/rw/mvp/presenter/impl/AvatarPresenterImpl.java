package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.UploadEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.AvatarInteractor;
import com.home.rw.mvp.interactor.UploadInteractor;
import com.home.rw.mvp.interactor.impl.AvatarInteractorImpl;
import com.home.rw.mvp.interactor.impl.UploadInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.AvatarView;
import com.home.rw.mvp.view.LoginView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cty on 2017/1/13.
 */

public class AvatarPresenterImpl extends BasePresenterImpl<AvatarView,BaseEntity> {
    private AvatarInteractor mAvatarInteractorImpl;

    @Inject
    public AvatarPresenterImpl(AvatarInteractorImpl avatarInteractor){
        mAvatarInteractorImpl = avatarInteractor;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void updateAvatar(String fileName){
        mSubscription = mAvatarInteractorImpl.updateAvatar(this,fileName);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.updateAvatarCompleted(data);
    }
}
