package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.message.MyTeamEntity;
import com.home.rw.mvp.entity.message.MyGroupEntity;
import com.home.rw.mvp.interactor.MyTeamInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/21.
 */

public class MyTeamInteractorImpl implements MyTeamInteractor<MyTeamEntity> {

    @Inject
    public MyTeamInteractorImpl(){

    }

    @Override
    public Subscription getMyTeam(final RequestCallBack<MyTeamEntity> callback) {
        return RetrofitManager.getInstance(HostType.MY_TEAM).myTeam()
                .compose(TransformUtils.<MyTeamEntity>defaultSchedulers())
                .subscribe(new Observer<MyTeamEntity>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(MyTeamEntity data) {
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
