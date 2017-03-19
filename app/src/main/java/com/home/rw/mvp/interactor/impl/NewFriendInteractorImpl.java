package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.message.MyFriendEntity;
import com.home.rw.mvp.entity.message.NewFriendEntity;
import com.home.rw.mvp.interactor.NewFriendInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/17.
 */

public class NewFriendInteractorImpl implements NewFriendInteractor<NewFriendEntity>{

    @Inject
    public NewFriendInteractorImpl(){

    }

    @Override
    public Subscription getNewFriend(final RequestCallBack<NewFriendEntity> callback, int page, int size) {
        return RetrofitManager.getInstance(HostType.NEW_FRIEND).getNewFriend(page,size)
                .compose(TransformUtils.<NewFriendEntity>defaultSchedulers())
                .subscribe(new Observer<NewFriendEntity>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(NewFriendEntity data) {
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
