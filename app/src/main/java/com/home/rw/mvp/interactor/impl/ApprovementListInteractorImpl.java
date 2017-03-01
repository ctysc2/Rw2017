package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.interactor.ApprovementListInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/2/24.
 */

public class ApprovementListInteractorImpl implements ApprovementListInteractor<ApprovementListEntity> {
    @Inject
    public ApprovementListInteractorImpl(){

    }

    @Override
    public Subscription getApprovementList(final RequestCallBack<ApprovementListEntity> callback, int page,int size,int reqType) {

        switch (reqType){
            case HostType.APPROVE_CHECKED:
                return RetrofitManager.getInstance(reqType).checked(page,size)
                        .compose(TransformUtils.<ApprovementListEntity>defaultSchedulers())
                        .subscribe(new Observer<ApprovementListEntity>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                callback.onError(e.getMessage());
                            }

                            @Override
                            public void onNext(ApprovementListEntity data) {
                                if(data!=null && data.getCode().equals("9999")){
                                    callback.onError(App.getAppContext().getString(R.string.reRoad));
                                    RxBus.getInstance().post(new ReLoginEvent());
                                }
                                else{
                                    callback.success(data);
                                }

                            }

                        });
            case HostType.APPROVE_WAITINNG_CHECKED:
                return RetrofitManager.getInstance(reqType).waitinghecked(page,size)
                        .compose(TransformUtils.<ApprovementListEntity>defaultSchedulers())
                        .subscribe(new Observer<ApprovementListEntity>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                callback.onError(e.getMessage());
                            }

                            @Override
                            public void onNext(ApprovementListEntity data) {
                                if(data!=null && data.getCode().equals("9999")){
                                    callback.onError(App.getAppContext().getString(R.string.reRoad));
                                    RxBus.getInstance().post(new ReLoginEvent());
                                }
                                else{
                                    callback.success(data);
                                }

                            }

                        });
            case HostType.APPROVE_CHECKING:
                return RetrofitManager.getInstance(reqType).checking(page,size)
                        .compose(TransformUtils.<ApprovementListEntity>defaultSchedulers())
                        .subscribe(new Observer<ApprovementListEntity>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                callback.onError(e.getMessage());
                            }

                            @Override
                            public void onNext(ApprovementListEntity data) {
                                if(data!=null && data.getCode().equals("9999")){
                                    callback.onError(App.getAppContext().getString(R.string.reRoad));
                                    RxBus.getInstance().post(new ReLoginEvent());
                                }
                                else{
                                    callback.success(data);
                                }

                            }

                        });
            case HostType.APPROVE_PASSED:
                return RetrofitManager.getInstance(reqType).passed(page,size)
                        .compose(TransformUtils.<ApprovementListEntity>defaultSchedulers())
                        .subscribe(new Observer<ApprovementListEntity>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                callback.onError(e.getMessage());
                            }

                            @Override
                            public void onNext(ApprovementListEntity data) {
                                if(data!=null && data.getCode().equals("9999")){
                                    callback.onError(App.getAppContext().getString(R.string.reRoad));
                                    RxBus.getInstance().post(new ReLoginEvent());
                                }
                                else{
                                    callback.success(data);
                                }

                            }

                        });
            case HostType.APPROVE_REJECT:
                return RetrofitManager.getInstance(reqType).reject(page,size)
                        .compose(TransformUtils.<ApprovementListEntity>defaultSchedulers())
                        .subscribe(new Observer<ApprovementListEntity>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                callback.onError(e.getMessage());
                            }

                            @Override
                            public void onNext(ApprovementListEntity data) {
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
