package com.home.rw.mvp.interactor.impl;

import com.home.rw.R;
import com.home.rw.application.App;
import com.home.rw.common.HostType;
import com.home.rw.event.ReLoginEvent;
import com.home.rw.listener.RequestCallBack;
import com.home.rw.mvp.entity.TopicDetailEntity;
import com.home.rw.mvp.interactor.TopicDetailInteractor;
import com.home.rw.repository.network.RetrofitManager;
import com.home.rw.utils.RxBus;
import com.home.rw.utils.TransformUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

/**
 * Created by cty on 2017/3/11.
 */

public class TopicDetailInteractorImpl implements TopicDetailInteractor<TopicDetailEntity> {

    @Inject
    public TopicDetailInteractorImpl(){

    }
    @Override
    public Subscription getTopicDetail(final RequestCallBack<TopicDetailEntity> callback, String id) {
        return RetrofitManager.getInstance(HostType.TOPIC_DETAIL).topicDetail(id)
                .compose(TransformUtils.<TopicDetailEntity>defaultSchedulers())
                .subscribe(new Observer<TopicDetailEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(TopicDetailEntity data) {
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
