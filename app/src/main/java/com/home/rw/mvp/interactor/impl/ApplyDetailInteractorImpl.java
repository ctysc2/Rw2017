package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.ApplyDetailEntity;
import com.home.rw.mvp.interactor.ApplyDetailInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/2/28.
 */

public class ApplyDetailInteractorImpl implements ApplyDetailInteractor<ApplyDetailEntity> {

    @Inject
    public ApplyDetailInteractorImpl(){

    }
    @Override
    public Subscription getApplyDetail(final RequestCallBack<ApplyDetailEntity> callback, String id, int reqType) {

        switch (reqType){
            case HostType.DETAIL_APPLY_EXPENSE:
            case HostType.DETAIL_APPLY_OUTGO:
            case HostType.DETAIL_APPLY_LEAVE:
            case HostType.DETAIL_APPLY_OVERTIME:
                return RetrofitManager.getInstance(reqType).applyDetail(id)
                    .compose(TransformUtils.<ApplyDetailEntity>defaultSchedulers())
                    .subscribe(new Observer<ApplyDetailEntity>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            callback.onError(e.getMessage());
                        }

                        @Override
                        public void onNext(ApplyDetailEntity data) {
                            if(data!=null && data.getCode().equals("9999")){
                                callback.onError(App.getAppContext().getString(R.string.reRoad));
                                RxBus.getInstance().post(new ReLoginEvent());
                            }
                            else{
                                callback.success(data);
                            }

                        }

                    });

            default:
                    return null;
        }

    }
}
