package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.DoApproveInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/2/25.
 */

public class DoApproveInteractorImpl implements DoApproveInteractor<BaseEntity> {

    @Inject
    public DoApproveInteractorImpl(){

    }
    @Override
    public Subscription doApprove(final RequestCallBack<BaseEntity> callback, String id, int reqType) {
        if(reqType == HostType.APPLY_DO_PASS){
            return RetrofitManager.getInstance(reqType).doPass(id)
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
        }else{
            return RetrofitManager.getInstance(reqType).doReject(id)
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
}
