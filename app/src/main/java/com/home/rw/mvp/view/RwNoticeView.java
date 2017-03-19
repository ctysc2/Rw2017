package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.message.RwNoticeEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/17.
 */

public interface RwNoticeView extends BaseView{
    void getRwNoticeCompleted(RwNoticeEntity data);
}
