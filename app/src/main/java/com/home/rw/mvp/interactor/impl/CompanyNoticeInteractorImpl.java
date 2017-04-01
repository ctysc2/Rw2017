package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.CompanyNoticeEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.CompNoticeEntity;
import com.home.rw.mvp.interactor.CompanyNoticeInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/17.
 */

public class CompanyNoticeInteractorImpl implements CompanyNoticeInteractor<CompNoticeEntity> {
    @Inject
    public CompanyNoticeInteractorImpl(){

    }
    @Override
    public Subscription getCompanyNotice(final RequestCallBack<CompNoticeEntity> callback, int page, int size,int type) {
        return RetrofitManager.getInstance(HostType.COMPANY_NOTICE).getCompanyNotice(page,size,type)
                .compose(TransformUtils.<CompNoticeEntity>defaultSchedulers())
                .subscribe(new Observer<CompNoticeEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(CompNoticeEntity data) {
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
