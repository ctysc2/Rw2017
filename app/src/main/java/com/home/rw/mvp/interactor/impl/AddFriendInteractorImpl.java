package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.AddFriendInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/17.
 */

public class AddFriendInteractorImpl implements AddFriendInteractor<BaseEntity> {

    @Inject
    public AddFriendInteractorImpl(){

    }
    @Override
    public Subscription addFriend(final RequestCallBack<BaseEntity> callback, String userId,String remark) {
        return RetrofitManager.getInstance(HostType.ADD_FRIEND).addFriend(userId,remark)
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