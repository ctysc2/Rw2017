package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.MixFocusEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.MixFocusInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/10.
 */

public class MixFocusInteractorImpl implements MixFocusInteractor<MixFocusEntity> {

    @Inject
    public MixFocusInteractorImpl(){

    }
    @Override
    public Subscription getMixFocus(final RequestCallBack<MixFocusEntity> callback) {
        return RetrofitManager.getInstance(HostType.MIX_FOCUS).getMixFocusList()
                .compose(TransformUtils.<MixFocusEntity>defaultSchedulers())
                .subscribe(new Observer<MixFocusEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(MixFocusEntity data) {
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
