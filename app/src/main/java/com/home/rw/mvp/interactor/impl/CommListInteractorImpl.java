package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.CardQueryEntity;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.interactor.CommListInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/10.
 */

public class CommListInteractorImpl implements CommListInteractor<CommunicationEntity> {

    @Inject
    public CommListInteractorImpl(){

    }

    @Override
    public Subscription getCommList(final RequestCallBack<CommunicationEntity> callback, int page, int size, int reqType) {
        Observable<CommunicationEntity> mObservable = null;

        switch (reqType){
            case HostType.MY_PUBLISH:
                mObservable = RetrofitManager.getInstance(reqType).myPublish(page,size);
                break;
            case HostType.PUBLISH_LIST1:
                mObservable = RetrofitManager.getInstance(reqType).publishList("0",page,size);
                break;
            case HostType.PUBLISH_LIST2:
                mObservable = RetrofitManager.getInstance(reqType).publishList("1",page,size);
                break;
            case HostType.PUBLISH_LIST3:
                mObservable = RetrofitManager.getInstance(reqType).publishList("2",page,size);
                break;
            case HostType.PUBLISH_LIST4:
                mObservable = RetrofitManager.getInstance(reqType).publishList("3",page,size);
                break;
            case HostType.DYN:
                mObservable = RetrofitManager.getInstance(reqType).getDynamics(page,size);

                break;
            default:
                return null;
        }

        return  mObservable
                .compose(TransformUtils.<CommunicationEntity>defaultSchedulers())
                .subscribe(new Observer<CommunicationEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(CommunicationEntity data) {
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

    @Override
    public Subscription getCommList(final RequestCallBack<CommunicationEntity> callback, String userId, int page, int size, int reqType) {
        return  RetrofitManager.getInstance(reqType).otherPublish(userId,page,size)
                .compose(TransformUtils.<CommunicationEntity>defaultSchedulers())
                .subscribe(new Observer<CommunicationEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(CommunicationEntity data) {
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
