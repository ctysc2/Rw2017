package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.message.RwNoticeDetailEntity;
import com.home.rw.mvp.entity.message.RwNoticeEntity;
import com.home.rw.mvp.interactor.RwNoticeDetailInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/20.
 */

public class RwNoticeDetailInteractorImpl implements RwNoticeDetailInteractor<RwNoticeDetailEntity> {
    @Inject
    public RwNoticeDetailInteractorImpl(){

    }
    @Override
    public Subscription getRwNoticeDetail(final RequestCallBack<RwNoticeDetailEntity> callback, String id) {
        return RetrofitManager.getInstance(HostType.RW_NOTICE_DETAIL).getRwNoticeDetailById(id)
                .compose(TransformUtils.<RwNoticeDetailEntity>defaultSchedulers())
                .subscribe(new Observer<RwNoticeDetailEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(RwNoticeDetailEntity data) {
                        if(data!=null && data.getCode().equals("9999")){
                            callback.onError(App.getAppContext().getString(R.string.reRoad));
                            RxBus.getInstance().post(new ReLoginEvent());
                        }
                        else{
                            callback.success(data);
                        }

                    }

                });
    }
}
