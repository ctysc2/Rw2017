package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.ApplyDetailEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/2/28.
 */

public interface ApplyDetailView extends BaseView{
    void getApplyDetailCompleted(ApplyDetailEntity data);
}
