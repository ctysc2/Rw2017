package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.ModifiPassWordInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/5.
 */

public class ModifiPassWordInteractorImpl implements ModifiPassWordInteractor<BaseEntity> {

    @Inject
    public ModifiPassWordInteractorImpl(){


    }
    @Override
    public Subscription modifiPassword(final RequestCallBack<BaseEntity> callback, HashMap<String, Object> input) {
        return RetrofitManager.getInstance(HostType.MODIFI_PASSWORD).modifiPassword(input)

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
                    public void onNext(BaseEntity data) {
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