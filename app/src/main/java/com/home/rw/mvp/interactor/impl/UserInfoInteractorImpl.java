package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.UserInfoEntity;
import com.home.rw.mvp.interactor.UserInfoInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/2/22.
 */

public class UserInfoInteractorImpl implements UserInfoInteractor{

    @Inject
    public UserInfoInteractorImpl(){

    }

    @Override
    public Subscription getUserInfo(final RequestCallBack callback) {
        return RetrofitManager.getInstance(HostType.USER_INFO).getUserInfo()

                .compose(TransformUtils.<UserInfoEntity>defaultSchedulers())
                .subscribe(new Observer<UserInfoEntity>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(UserInfoEntity data) {
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
