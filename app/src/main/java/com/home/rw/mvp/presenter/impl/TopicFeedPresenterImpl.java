package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.TopicDetailEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.interactor.TopicDetailInteractor;
import com.home.rw.mvp.interactor.TopicFeedInteractor;
import com.home.rw.mvp.interactor.impl.TopicDetailInteractorImpl;
import com.home.rw.mvp.interactor.impl.TopicFeedInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.TopicFeedbackView;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by cty on 2017/4/16.
 */

public class TopicFeedPresenterImpl extends BasePresenterImpl<TopicFeedbackView,BaseEntity>{

    private TopicFeedInteractor mTopicFeedInteractorImpl;

    @Inject
    public TopicFeedPresenterImpl(TopicFeedInteractorImpl topicFeedDetailInteractor){
        mTopicFeedInteractorImpl = topicFeedDetailInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void sendTopicFeed(HashMap<String,String> data){
        mSubscription = mTopicFeedInteractorImpl.sendTopicFeedr(this,data);

    }

    @Override
    public void success(BaseEntity data) {
        super.success(data);
        mView.sendTopicFeedbackCompleted(data);
    }

}
