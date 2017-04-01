package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.UserInfoEntity;
import com.home.rw.mvp.entity.message.ContactListEntity;
import com.home.rw.mvp.interactor.UserByPhoneInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/19.
 */

public class UserByPhoneInteractorImpl implements UserByPhoneInteractor<ContactListEntity> {

    @Inject
    public UserByPhoneInteractorImpl(){

    }

    @Override
    public Subscription getUserByPhone(final RequestCallBack<ContactListEntity> callback, String phones) {
        return RetrofitManager.getInstance(HostType.REGISTERED_USER).getUsersByPhone(phones)

                .compose(TransformUtils.<ContactListEntity>defaultSchedulers())
                .subscribe(new Observer<ContactListEntity>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ContactListEntity data) {
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
