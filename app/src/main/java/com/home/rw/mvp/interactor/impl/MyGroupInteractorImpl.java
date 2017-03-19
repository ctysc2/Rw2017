package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.message.MyFriendEntity;
import com.home.rw.mvp.entity.message.MyGroupEntity;
import com.home.rw.mvp.interactor.MyGroupInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/17.
 */

public class MyGroupInteractorImpl implements MyGroupInteractor<MyGroupEntity> {

    @Inject
    public MyGroupInteractorImpl(){

    }
    @Override
    public Subscription getMyGroupList(final RequestCallBack<MyGroupEntity> callback, int page, int size) {
        return RetrofitManager.getInstance(HostType.MY_GROUP).getGroupList(page,size)
                .compose(TransformUtils.<MyGroupEntity>defaultSchedulers())
                .subscribe(new Observer<MyGroupEntity>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(MyGroupEntity data) {
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
