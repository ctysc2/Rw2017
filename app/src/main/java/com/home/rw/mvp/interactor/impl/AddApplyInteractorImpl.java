package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.interactor.AddApplyInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/2/22.
 */

public class AddApplyInteractorImpl implements AddApplyInteractor<AddApplyEntity> {

    @Inject
    public AddApplyInteractorImpl(){

    }

    @Override
    public Subscription addApply(final RequestCallBack<AddApplyEntity> callback,int reqType) {

        return RetrofitManager.getInstance(reqType).addApply()
                .compose(TransformUtils.<AddApplyEntity>defaultSchedulers())
                .subscribe(new Observer<AddApplyEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(AddApplyEntity data) {
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
