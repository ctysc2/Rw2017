package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.CompanyNoticeEntity;
import com.home.rw.mvp.entity.message.DepartmentEntity;
import com.home.rw.mvp.interactor.DepartmentInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/18.
 */

public class DepartmentInteractorImpl implements DepartmentInteractor<DepartmentEntity> {

    @Inject
    public DepartmentInteractorImpl(){

    }

    @Override
    public Subscription getCompanyNotice(final RequestCallBack<DepartmentEntity> callback) {
        return RetrofitManager.getInstance(HostType.DEPARTMENT).getDepartmentList()
                .compose(TransformUtils.<DepartmentEntity>defaultSchedulers())
                .subscribe(new Observer<DepartmentEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(DepartmentEntity data) {
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
