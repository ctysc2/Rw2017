package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.interactor.FocusListInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/9.
 */

public class FocusListInteractorImpl implements FocusListInteractor<FacusListEntity>{
    @Inject
    public FocusListInteractorImpl(){

    }
    @Override
    public Subscription getFocusList(final RequestCallBack<FacusListEntity> callback, int page, int size) {
        return RetrofitManager.getInstance(HostType.FOCUS_LIST).focusList(page, size)

                .compose(TransformUtils.<FacusListEntity>defaultSchedulers())
                .subscribe(new Observer<FacusListEntity>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(FacusListEntity data) {
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
