package com.home.rw.mvp.interactor.impl;

import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.AvatarInteractor;
import com.home.rw.mvp.interactor.LoginInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.TransformUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/1/13.
 */


public class AvatarInteractorImpl implements AvatarInteractor<BaseEntity> {

    @Inject
    public AvatarInteractorImpl(){

    }
    @Override
    public Subscription updateAvatar(final RequestCallBack<BaseEntity> callback, String fileName) {
        Map<String,Object> map = new HashMap<>();
        map.put("fileName",fileName);
        map.put("userId", App.ID);
        return RetrofitManager.getInstance(HostType.AVATAR).updateAvatar(map)

                .compose(TransformUtils.<BaseEntity>defaultSchedulers())
                .subscribe(new Observer<BaseEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseEntity login) {
                        callback.success(login);

                    }

                });
    }
}