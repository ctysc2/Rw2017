package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.LinkedEntity;
import com.home.rw.mvp.entity.LogEntity;
import com.home.rw.mvp.interactor.LinkedInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/10.
 */

public class LinkedInteractorImpl implements LinkedInteractor<LinkedEntity> {

    @Inject
    public LinkedInteractorImpl(){

    }
    @Override
    public Subscription getLinked(final RequestCallBack<LinkedEntity> callback,int reqType) {

        Observable<LinkedEntity> mObservable;
        switch (reqType){
            case HostType.OUT_LINK1:
                mObservable = RetrofitManager.getInstance(reqType).link1();
                break;
            case HostType.OUT_LINK2:
                mObservable = RetrofitManager.getInstance(reqType).link2();
                break;
            case HostType.OUT_LINK3:
                mObservable = RetrofitManager.getInstance(reqType).link3();
                break;
            case HostType.OUT_LINK4:
                mObservable = RetrofitManager.getInstance(reqType).link4();
                break;
            default:
                return null;


        }
        return mObservable.compose(TransformUtils.<LinkedEntity>defaultSchedulers())
                .subscribe(new Observer<LinkedEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(LinkedEntity data) {
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
