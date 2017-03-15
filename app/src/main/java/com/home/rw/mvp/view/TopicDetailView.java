package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.TopicDetailEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/11.
 */

public interface TopicDetailView extends BaseView{
    void getTopicDetailCompleted(TopicDetailEntity data);
}
