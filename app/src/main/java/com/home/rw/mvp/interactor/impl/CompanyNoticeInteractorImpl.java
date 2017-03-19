package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.CompanyNoticeEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
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

public class CompanyNoticeInteractorImpl implements CompanyNoticeInteractor<CompanyNoticeEntity> {
    @Inject
    public CompanyNoticeInteractorImpl(){

    }
    @Override
    public Subscription getCompanyNotice(final RequestCallBack<CompanyNoticeEntity> callback, int page, int size) {
        return RetrofitManager.getInstance(HostType.COMPANY_NOTICE).getCompanyNotice(page,size)
                .compose(TransformUtils.<CompanyNoticeEntity>defaultSchedulers())
                .subscribe(new Observer<CompanyNoticeEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(CompanyNoticeEntity data) {
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
