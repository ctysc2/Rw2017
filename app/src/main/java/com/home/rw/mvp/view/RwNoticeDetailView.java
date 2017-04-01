package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.message.RwNoticeDetailEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/20.
 */

public interface RwNoticeDetailView extends BaseView{
    void getRwNoticeDetailCompleted(RwNoticeDetailEntity data);
}
