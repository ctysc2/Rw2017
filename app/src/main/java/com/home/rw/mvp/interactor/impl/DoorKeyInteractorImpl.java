package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.DoorKeyEntity;
import com.home.rw.mvp.interactor.DoorKeyInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/31.
 */

public class DoorKeyInteractorImpl implements DoorKeyInteractor<DoorKeyEntity> {
    @Inject
    public DoorKeyInteractorImpl(){

    }

    @Override
    public Subscription getDoorKey(final RequestCallBack<DoorKeyEntity> callback) {
        return RetrofitManager.getInstance(HostType.DOOR_KEY).getDoorKey()
                .compose(TransformUtils.<DoorKeyEntity>defaultSchedulers())
                .subscribe(new Observer<DoorKeyEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(DoorKeyEntity data) {

                            callback.success(data);


                    }

                });
    }
}
