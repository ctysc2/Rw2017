package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.TopicDetailEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.SignInteractor;
import com.home.rw.mvp.interactor.TopicDetailInteractor;
import com.home.rw.mvp.interactor.impl.SignInteractorImpl;
import com.home.rw.mvp.interactor.impl.TopicDetailInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.TopicDetailView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/11.
 */

public class TopicDetailPresenterImpl extends BasePresenterImpl<TopicDetailView,TopicDetailEntity>{
    private TopicDetailInteractor mTopicDetailInteractorImpl;

    @Inject
    public TopicDetailPresenterImpl(TopicDetailInteractorImpl topicDetailDetailInteractor){
        mTopicDetailInteractorImpl = topicDetailDetailInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getTpoicDetial(String id){
        mSubscription = mTopicDetailInteractorImpl.getTopicDetail(this,id);

    }

    @Override
    public void success(TopicDetailEntity data) {
        super.success(data);
        mView.getTopicDetailCompleted(data);
    }
}
