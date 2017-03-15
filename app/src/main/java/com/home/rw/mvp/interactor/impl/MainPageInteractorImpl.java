package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.MainPageEntity;
import com.home.rw.mvp.entity.MixFocusEntity;
import com.home.rw.mvp.interactor.MainPageInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/12.
 */

public class MainPageInteractorImpl implements MainPageInteractor<MainPageEntity> {

    @Inject
    public MainPageInteractorImpl(){

    }

    @Override
    public Subscription getMainPage(final RequestCallBack<MainPageEntity> callback) {
        return RetrofitManager.getInstance(HostType.MAIN_PAGE).getMainPage()
                .compose(TransformUtils.<MainPageEntity>defaultSchedulers())
                .subscribe(new Observer<MainPageEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(MainPageEntity data) {
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
